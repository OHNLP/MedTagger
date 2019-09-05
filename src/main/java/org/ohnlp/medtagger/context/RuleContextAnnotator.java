/*
 * Copyright: (c)  2019  Mayo Foundation for Medical Education and
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ohnlp.medtagger.context;

import org.ahocorasick.trie.Emit;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.ohnlp.medtagger.context.impl.ConTexTSettings;
import org.ohnlp.medtagger.context.impl.ConTexTStatus;
import org.ohnlp.medtagger.context.impl.ConTexTTrigger;
import org.ohnlp.medtagger.context.impl.ConText;
import org.ohnlp.medtagger.perf.AnnotationIndex;
import org.ohnlp.medtagger.perf.AnnotationRoot;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.textsem.ContextAnnotation;
import org.ohnlp.typesystem.type.textspan.Sentence;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * Performs context annotation based on the ConText algorithm as developed by Chapman et. al.
 */
public class RuleContextAnnotator extends JCasAnnotator_ImplBase {

    private static final int MAX_WIN_SIZE = -1;

    private static final int[] RULE_PRIORITIES = {1, 2}; // TODO more robust solution that scans files

    /**
     * {@link ConTexTSettings} that denote trigger terms and terminals, in priority order with lowest priority first
     */
    public Deque<ConTexTSettings> contextSettings;

    @Override
    public void initialize(UimaContext ctxt) throws ResourceInitializationException {
        super.initialize(ctxt);
        try {
            contextSettings = new LinkedList<>();
            for (int priority : RULE_PRIORITIES) {
                contextSettings.add(new ConTexTSettings(ConTexTSettings.class.getResourceAsStream("/medtaggerresources/context/contextRule.txt"), 1, priority));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(JCas cas) {
        Map<Sentence, Collection<ConceptMention>> sentenceToConceptMentionIndex = JCasUtil.indexCovered(cas, Sentence.class, ConceptMention.class);
        // Iterate through sentences and tag them
        for (Sentence sentence : JCasUtil.select(cas, Sentence.class)) {
            Collection<ConceptMention> concepts = sentenceToConceptMentionIndex.get(sentence);
            // No concepts to tag, therefore no need to process contexts
            if (concepts == null || concepts.size() == 0) {
                continue;
            }
            // Process the sentence to determine contexts
            String content = sentence.getCoveredText();
            // - Get Contexts
            Deque<Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>>> triggersByPriority = getConTexTTriggers(content.toLowerCase());
            // - Flatten the view while factoring in priority overwrites
            Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>> triggers = flattenByPriority(cas, triggersByPriority);
            // Create a tagged view of concept statuses for that sentence
            ConTexTStatus[] taggedSent = annotateConTextStatuses(triggers, content);
            // Encode concept mentions accordingly TODO AABB use to determine collisions? (faster/more complete)
            // As of right now, only the context status of first character is checked, which could lead to odd
            // behaviour in very select (and probably extremely rare/never occurring) cases when a status begins and/or
            // ends halfway through and/or is completely enclosed by the concept mention
            for (ConceptMention concept : concepts) {
                // - Set defaults
                concept.setCertainty(ConText.AFFIRMED.toString());
                concept.setStatus(ConText.RECENT.toString());
                concept.setExperiencer(ConText.EXPERIENCER_PATIENT.toString());
                // - Encode based on first character of ConText
                ConTexTStatus status = taggedSent[concept.getBegin() - sentence.getBegin()];
                if (!status.isPositive) {
                    concept.setCertainty(ConText.NEGATED.toString());
                }
                if (!status.isAsserted) { // Assertion checks come after positive check, since possible != no, but possible no == possible yes
                    concept.setCertainty(ConText.POSSIBLE.toString());
                }
                if (status.isHypothetical) {
                    concept.setCertainty(ConText.HYPOTHETICAL.toString());
                }
                if (!status.experiencerIsPatient) {
                    concept.setExperiencer(ConText.EXPERIENCER_OTHER.toString());
                }
                if (!status.isPresent) {
                    if (status.experiencerIsPatient) {
                        concept.setStatus(ConText.PERSONALHISTORICAL.toString());
                    } else {
                        concept.setStatus(ConText.FAMILIALHISTORICAL.toString());
                    }
                }
            }

        }
    }

    /**
     * Iterates through triggersByPriority in reverse order in descending priorities while disallowing overwrites
     *
     * @param triggersByPriority priorities to flatten
     * @return A flattened view with higher priority triggers overwriting lower priority triggers
     */
    private Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>> flattenByPriority(JCas cas, Deque<Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>>> triggersByPriority) {
        if (triggersByPriority.size() == 1) {
            return triggersByPriority.poll();
        }
        AnnotationIndex priorityIndex = new AnnotationRoot();
        Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>> ret = new HashMap<>();
        Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>> next;
        while ((next = triggersByPriority.pollLast()) != null) {
            next.forEach((type, triggers) -> {
                List<ConTexTTrigger> retained = new LinkedList<>();
                for (ConTexTTrigger trigger : triggers) {
                    if (priorityIndex.getCollisions(trigger.start, trigger.end, ContextAnnotation.class).isEmpty()) {
                        retained.add(trigger);
                    }
                }
                ret.computeIfAbsent(type, k -> new LinkedList<>()).addAll(retained);
            });
            // Now, add all of these to index to prevent lower priority from adding if they collide. We do this here to
            // ensure that all annotations with the same priority have the chance to appear even if they overlap.
            next.values().forEach(l -> l.forEach(t -> priorityIndex.insert(new ContextAnnotation(cas, t.start, t.end))));
        }
        return ret;
    }

    /**
     * Annotates context statuses by sentence on a character level
     *
     * @param triggers The triggers to use
     * @param text     The text to annotate
     * @return An array of context statuses with indexes corresponding to their respective sentence character positions
     */
    public final ConTexTStatus[] annotateConTextStatuses(Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>> triggers, String text) {
        ConTexTStatus[] sentence = new ConTexTStatus[text.length()];
        for (int i = 0; i < sentence.length; i++) {
            sentence[i] = new ConTexTStatus();
        }
        // - Transpose pseudos onto the sentence
        for (ConTexTTrigger pseudo : triggers.getOrDefault(ConTexTTrigger.TriggerType.PSEUDO, Collections.emptyList())) {
            for (int i = pseudo.start; i < pseudo.end; i++) {
                sentence[i].isPseudo = true;
            }
        }
        // - Transpose terminals onto the sentence
        for (ConTexTTrigger trigger : triggers.getOrDefault(ConTexTTrigger.TriggerType.TERMINAL, Collections.emptyList())) {
            for (int i = trigger.start; i < trigger.end; i++) {
                switch (trigger.contextType) {
                    case NEGATED:
                        sentence[i].isNegationTerminal = true;
                        break;
                    case POSSIBLE:
                        sentence[i].isPossibleTerminal = true;
                        break;
                    case HISTORICAL:
                        sentence[i].isHistoricalTerminal = true;
                        break;
                    case HYPOTHETICAL:
                        sentence[i].isHypotheticalTerminal = true;
                        break;
                    case EXPERIENCER:
                        sentence[i].isExperiencerTerminal = true;
                        break;
                    default:
                        break;
                }
            }
        }
        // - Traverse left to right TODO better algorithm for traversal
        List<ConTexTTrigger> preTriggers = triggers.getOrDefault(ConTexTTrigger.TriggerType.START_RIGHT, Collections.emptyList());
        for (ConTexTTrigger trigger : preTriggers) {
            // Skip pseudos
            if (sentence[trigger.start].isPseudo || sentence[trigger.end - 1].isPseudo) {
                continue;
            }
            // Tag trigger
            for (int i = trigger.start; i < trigger.end; i++) {
                ConTexTStatus status = sentence[i];
                tagStatusAsTrigger(status, trigger.contextType);
            }
            // Continue to end of sentence, break on terminal
            AtomicInteger wordWindow = new AtomicInteger(0);
            for (int i = trigger.end; i < sentence.length; i++) {
                ConTexTStatus status = sentence[i];
                char sentenceChar = text.toCharArray()[i];
                if (sentenceChar == ' ') {
                    wordWindow.incrementAndGet();
                }
                if (tagContexTStatus(status, trigger, wordWindow)) {
                    break;
                }
            }
        }
        // - Traverse right to left
        List<ConTexTTrigger> postTriggers = triggers.getOrDefault(ConTexTTrigger.TriggerType.START_LEFT, Collections.emptyList());
        for (ConTexTTrigger trigger : postTriggers) {
            // Skip pseudos
            if (sentence[trigger.start].isPseudo || sentence[trigger.end - 1].isPseudo) {
                continue;
            }
            // Tag trigger
            for (int i = trigger.start; i < trigger.end; i++) {
                ConTexTStatus status = sentence[i];
                tagStatusAsTrigger(status, trigger.contextType);
            }
            // Continue to beginning of sentence, break on terminal
            AtomicInteger wordWindow = new AtomicInteger(0);
            for (int i = trigger.start - 1; i >= 0; i--) {
                ConTexTStatus status = sentence[i];
                char sentenceChar = text.toCharArray()[i];
                if (sentenceChar == ' ') {
                    wordWindow.incrementAndGet();
                }
                if (tagContexTStatus(status, trigger, wordWindow)) {
                    break;
                }
            }
        }
        return sentence;
    }

    private void tagStatusAsTrigger(ConTexTStatus status, ConText type) {
        switch (type) {
            case NEGATED:
                status.isNegationTrigger = true;
                break;
            case HISTORICAL:
                status.isHistoricalTrigger = true;
                break;
            case HYPOTHETICAL:
                status.isHypotheticalTrigger = true;
                break;
            case EXPERIENCER:
                status.isExperiencerTrigger = true;
                break;
            case POSSIBLE:
                status.isPossibleTrigger = true;
                break;
            default:
                throw new UnsupportedOperationException("A trigger of type " + type + " was found for sentence tagging");
        }
    }

    /**
     * Tags the given status; and returns whether tagging should continue or if a terminal condition was reached
     *
     * @param status  The status to tag
     * @param trigger The trigger definition being tagged
     * @param spaces
     * @return True if a terminal condition was hit and tagging is finished for this trigger, false otherwise
     */
    private boolean tagContexTStatus(ConTexTStatus status, ConTexTTrigger trigger, AtomicInteger spaces) {
        // First check if this is a terminal
        boolean isExit = spaces.get() > MAX_WIN_SIZE && MAX_WIN_SIZE != -1;
        switch (trigger.contextType) {
            case NEGATED:
                if (status.isNegationTerminal || status.isNegationTrigger) {
                    isExit = true;
                }
                break;
            case POSSIBLE:
                if (status.isPossibleTrigger || status.isPossibleTerminal || status.isHypotheticalTrigger || status.isHypotheticalTerminal) {
                    isExit = true;
                }
                break;
            case HYPOTHETICAL:
                if (status.isHypotheticalTrigger || status.isHypotheticalTerminal) {
                    isExit = true;
                }
                break;
            case HISTORICAL:
                if (status.isHistoricalTerminal || status.isHistoricalTrigger) {
                    isExit = true;
                }
                break;
            case EXPERIENCER:
                if (status.isExperiencerTerminal || status.isExperiencerTrigger) {
                    isExit = true;
                }
                break;
            default:
                throw new UnsupportedOperationException("A trigger of type " + trigger.contextType + " was found for sentence tagging");
        }
        if (isExit) {
            // Terminal found, stop encoding
            return true;
        }
        // Not a Terminal, thus encode the status
        switch (trigger.contextType) {
            case NEGATED:
                status.isPositive = false;
                break;
            case POSSIBLE:
                status.isAsserted = false;
                break;
            case HISTORICAL:
                status.isPresent = false;
                break;
            case HYPOTHETICAL:
                status.isHypothetical = true;
                break;
            case EXPERIENCER:
                status.experiencerIsPatient = false;
                break;
        }
        return false;
    }


    /**
     * ConTexT triggers denote where to start/end a ConTexT, and (in the case of start triggers) whether a context
     * extends to the right or left.
     * <br/>
     * This method detects all the triggers within the sentence
     *
     * @param sentence The sentence to tag
     * @return a mapping of context trigger types to triggers, in priority order with lowest priority first
     */
    public final Deque<Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>>> getConTexTTriggers(String sentence) {
        Deque<Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>>> ret = new LinkedList<>();
        for (ConTexTSettings prioritySettings : contextSettings) {
            Map<ConTexTTrigger.TriggerType, List<ConTexTTrigger>> triggersForThisPriority = new HashMap<>();
            // Run the general trie
            Collection<Emit> values = prioritySettings.getGeneral().parseText(sentence.toLowerCase());
            Map<String, Map<ConTexTTrigger.TriggerType, Set<ConText>>> generalDict = prioritySettings.getGeneralTriggerDict();
            values.forEach(e -> {
                Map<ConTexTTrigger.TriggerType, Set<ConText>> triggers = generalDict.get(e.getKeyword());
                int start = e.getStart();
                int end = e.getEnd() + 1;
                triggers.forEach(((triggerType, conTexts) -> {
                    if (triggerType == ConTexTTrigger.TriggerType.PSEUDO) {
                        triggersForThisPriority.computeIfAbsent(triggerType, k -> new LinkedList<>()).add(new ConTexTTrigger(null, start, end));
                    } else {
                        conTexts.forEach(conText -> triggersForThisPriority.computeIfAbsent(triggerType, k -> new LinkedList<>()).add(new ConTexTTrigger(conText, start, end)));
                    }
                }));
            });
            // First find pseudos (trigger exclusions)
            Matcher matcher;
            if (prioritySettings.getRegexPseudo() != null) {
                matcher = prioritySettings.getRegexPseudo().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.PSEUDO, k -> new LinkedList<>()).add(new ConTexTTrigger(null, mr.start(), mr.end()));
                }
            }
            // Negation - Right lookup
            if (prioritySettings.getRegexNegPre() != null) {
                matcher = prioritySettings.getRegexNegPre().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.NEGATED, mr.start(), mr.end()));
                }
            }
            // Negation - Left lookup
            if (prioritySettings.getRegexNegPost() != null) {
                matcher = prioritySettings.getRegexNegPost().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_LEFT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.NEGATED, mr.start(), mr.end()));
                }
            }
            // Negation - Terminals
            if (prioritySettings.getRegexNegEnd() != null) {
                matcher = prioritySettings.getRegexNegEnd().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.NEGATED, mr.start(), mr.end()));
                }
            }
            // Possible - Right lookup
            if (prioritySettings.getRegexPossPre() != null) {
                matcher = prioritySettings.getRegexPossPre().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.POSSIBLE, mr.start(), mr.end()));
                }
            }
            // Possible - Left lookup
            if (prioritySettings.getRegexPossPost() != null) {
                matcher = prioritySettings.getRegexPossPost().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_LEFT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.POSSIBLE, mr.start(), mr.end()));
                }
            }
            // Experiencer - Right Lookup
            if (prioritySettings.getRegexExpPre() != null) {
                matcher = prioritySettings.getRegexExpPre().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.EXPERIENCER, mr.start(), mr.end()));
                }
            }
            // Experiencer - Left Lookup
            if (prioritySettings.getRegexExpPost() != null) {
                matcher = prioritySettings.getRegexExpPost().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_LEFT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.EXPERIENCER, mr.start(), mr.end()));
                }
            }
            // Experiencer - Terminal
            if (prioritySettings.getRegexExpEnd() != null) {
                matcher = prioritySettings.getRegexExpEnd().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.EXPERIENCER, mr.start(), mr.end()));
                }
            }
            // Hypothetical - Right Lookup
            if (prioritySettings.getRegexHypoPre() != null) {
                matcher = prioritySettings.getRegexHypoPre().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HYPOTHETICAL, mr.start(), mr.end()));
                }
            }
            // No Hypothetical - Left Lookup
            // Hypothetical - Terminal
            if (prioritySettings.getRegexHypoEnd() != null) {
                matcher = prioritySettings.getRegexHypoEnd().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HYPOTHETICAL, mr.start(), mr.end()));
                }
            }
            // Historical - Right Lookup
            if (prioritySettings.getRegexHistPre() != null) {
                matcher = prioritySettings.getRegexHistPre().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
                }
            }
            // Historical - Left Lookup
            if (prioritySettings.getRegexHistPost() != null) {
                matcher = prioritySettings.getRegexHistPost().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_LEFT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
                }
            }
            // Historical - Terminal
            if (prioritySettings.getRegexHistEnd() != null) {
                matcher = prioritySettings.getRegexHistEnd().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
                }
            }
            // Mixed
            if (prioritySettings.getRegexHypoExpEnd() != null) {
                matcher = prioritySettings.getRegexHypoExpEnd().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HYPOTHETICAL, mr.start(), mr.end()));
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.EXPERIENCER, mr.start(), mr.end()));
                }
            }
            if (prioritySettings.getRegexHistExpEnd() != null) {
                matcher = prioritySettings.getRegexHistExpEnd().matcher(sentence);
                while (matcher.find()) {
                    MatchResult mr = matcher.toMatchResult();
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
                    triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.TERMINAL, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.EXPERIENCER, mr.start(), mr.end()));
                }
            }
            // Time
            matcher = prioritySettings.getRegexTimeFor().matcher(sentence);
            while (matcher.find()) {
                MatchResult mr = matcher.toMatchResult();
                triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
            }
            matcher = prioritySettings.getRegexTime().matcher(sentence);
            while (matcher.find()) {
                MatchResult mr = matcher.toMatchResult();
                triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_RIGHT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
            }
            matcher = prioritySettings.getRegexTimeSince().matcher(sentence);
            while (matcher.find()) {
                MatchResult mr = matcher.toMatchResult();
                triggersForThisPriority.computeIfAbsent(ConTexTTrigger.TriggerType.START_LEFT, k -> new LinkedList<>()).add(new ConTexTTrigger(ConText.HISTORICAL, mr.start(), mr.end()));
            }
            ret.add(triggersForThisPriority);
        }
        return ret;
    }

}
