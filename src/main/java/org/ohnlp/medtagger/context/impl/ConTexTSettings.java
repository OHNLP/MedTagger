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

package org.ohnlp.medtagger.context.impl;

import org.ahocorasick.trie.Trie;
import org.ohnlp.medtagger.context.impl.ConTexTTrigger.TriggerType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Class used to analyze concept context (based on the 'ConText' algorithm by Chapman et al.)
 * Adapted from Julien Thibault, Stephane Meystre, Oscar Ferrandez-Escamez
 * Department of Biomedical Informatics, University of Utah, 2011
 */
public class ConTexTSettings {

    private Pattern regexPseudo;

    private Pattern regexNegPre;
    private Pattern regexNegPost;
    private Pattern regexPossPre;
    private Pattern regexPossPost;
    private Pattern regexNegEnd;

    private Pattern regexExpPre;
    private Pattern regexExpPost;
    private Pattern regexExpEnd;

    private Pattern regexHypoPre;
    private Pattern regexHypoEnd;
    private Pattern regexHypoExpEnd;

    private Pattern regexHistPre;
    private Pattern regexHistPost;
    private Pattern regexHistEnd;
    private Pattern regexHistExpEnd;

    private Pattern regexTime;
    private Pattern regexTimeFor;
    private Pattern regexTimeSince;

    //    private KeywordTrie generalTrie = new KeywordTrie();
    private Map<String, Map<TriggerType, Set<ConText>>> generalTriggerDict = new HashMap<>();

    private Trie general;


    /**
     * Initialization regex (load parameters)
     *
     * @throws FileNotFoundException
     */
    public ConTexTSettings(/*String*/Object contextFile, int typeInput, int priority) throws FileNotFoundException {
        /**
         * Used to handle priorities of overlapping triggers. Higher order triggers get higher priorities.
         */
        //SPM Signature change to allow for alternate input streams
        Scanner sc;
        String regex_PSEUDO = "";
        String regex_NEG_PRE = "";
        String regex_NEG_POST = "";
        String regex_POSS_PRE = "";
        String regex_POSS_POST = "";
        String regex_NEG_END = "";

        String regex_EXP_PRE = "";
        String regex_EXP_POST = "";
        String regex_EXP_END = "";

        String regex_HYPO_PRE = "";
        String regex_HIST_PRE = "";
        String regex_HIST_POST = "";
        String regex_HIST_1W = "";

        String regex_HYPO_END = "";
        String regex_HIST_END = "";
        String regex_HIST_EXP_END = "";
        String regex_HYPO_EXP_END = "";
        //SPM Signature change to allow for alternate input streams: input stream > 0 or file == 0 to be handled
        if (typeInput == 0) {
            sc = new Scanner(new File((String) contextFile));
        } else {
            sc = new Scanner((InputStream) contextFile);
        }

        Trie.TrieBuilder generalTrie = Trie.builder()
                .onlyWholeWordsWhiteSpaceSeparated();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("#")) continue;

            if (!(line.startsWith("//"))) {
                String[] tmp = line.split("~\\|~");
//                String phrase = tmp[0].replaceAll(" ", "[\\\\s\\\\-]+");
                String phrase = tmp[0].trim();
                boolean isRegex = phrase.startsWith("regex:");
                if (isRegex) {
                    phrase = phrase.substring(6);
                } else {
                    phrase = phrase.toLowerCase();
                }
                String position = tmp[1].toLowerCase();
                String contextType = tmp[2].toLowerCase();
                int rulePriority = Integer.valueOf(tmp[3]);
                if (rulePriority != priority) {
                    continue;
                }
                if (!isRegex) {
                    generalTrie.addKeyword(phrase);
                    switch (position) {
                        case "pseudo": {
                            generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.PSEUDO, k -> new HashSet<>()).add(ConText.NEGATED);
                            break;
                        }
                        case "termin": {
                            switch (contextType) {
                                case "neg": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.NEGATED);
                                    break;
                                }
                                case "hypo": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.HYPOTHETICAL);
                                    break;
                                }
                                case "hist": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.HISTORICAL);
                                    break;
                                }
                                case "histexp": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.EXPERIENCER);
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.HISTORICAL);
                                    break;
                                }
                                case "hypoexp": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.EXPERIENCER);
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.HYPOTHETICAL);
                                    break;
                                }
                                case "exp": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.TERMINAL, k -> new HashSet<>()).add(ConText.EXPERIENCER);
                                    break;
                                }
                            }
                            break;
                        }
                        case "pre": {
                            switch (contextType) {
                                case "neg": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_RIGHT, k -> new HashSet<>()).add(ConText.NEGATED);
                                    break;
                                }
                                case "poss": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_RIGHT, k -> new HashSet<>()).add(ConText.POSSIBLE);
                                    break;
                                }
                                case "hypo": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_RIGHT, k -> new HashSet<>()).add(ConText.HYPOTHETICAL);
                                    break;
                                }
                                case "hist": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_RIGHT, k -> new HashSet<>()).add(ConText.HISTORICAL);
                                    break;
                                }
                                case "exp": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_RIGHT, k -> new HashSet<>()).add(ConText.EXPERIENCER);
                                    break;
                                }
                            }
                            break;
                        }
                        case "post": {
                            switch (contextType) {
                                case "neg": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_LEFT, k -> new HashSet<>()).add(ConText.NEGATED);
                                    break;
                                }
                                case "poss": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_LEFT, k -> new HashSet<>()).add(ConText.POSSIBLE);
                                    break;
                                }
                                case "hypo": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_LEFT, k -> new HashSet<>()).add(ConText.HYPOTHETICAL);
                                    break;
                                }
                                case "hist": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_LEFT, k -> new HashSet<>()).add(ConText.HISTORICAL);
                                    break;
                                }
                                case "exp": {
                                    generalTriggerDict.computeIfAbsent(phrase, k -> new HashMap<>()).computeIfAbsent(TriggerType.START_LEFT, k -> new HashSet<>()).add(ConText.EXPERIENCER);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } else {
                    if (position.compareTo("pseudo") == 0) {
                        regex_PSEUDO = regex_PSEUDO + "|" + phrase;
                    } else if (position.compareTo("termin") == 0) {
                        if (contextType.compareTo("neg") == 0)
                            regex_NEG_END = regex_NEG_END + "|" + phrase;
                        else if (contextType.compareTo("hypo") == 0)
                            regex_HYPO_END = regex_HYPO_END + "|" + phrase;
                        else if (contextType.compareTo("hist") == 0)
                            regex_HIST_END = regex_HIST_END + "|" + phrase;
                        else if (contextType.compareTo("histexp") == 0)
                            regex_HIST_EXP_END = regex_HIST_EXP_END + "|" + phrase;
                        else if (contextType.compareTo("hypoexp") == 0)
                            regex_HYPO_EXP_END = regex_HYPO_EXP_END + "|" + phrase;
                        else if (contextType.compareTo("exp") == 0)
                            regex_EXP_END = regex_EXP_END + "|" + phrase;
                    } else if (position.compareTo("pre") == 0) {
                        if (contextType.compareTo("neg") == 0)
                            regex_NEG_PRE = regex_NEG_PRE + "|" + phrase;
                        else if (contextType.compareTo("poss") == 0)
                            regex_POSS_PRE = regex_POSS_PRE + "|" + phrase;
                        else if (contextType.compareTo("hypo") == 0)
                            regex_HYPO_PRE = regex_HYPO_PRE + "|" + phrase;
                        else if (contextType.compareTo("exp") == 0)
                            regex_EXP_PRE = regex_EXP_PRE + "|" + phrase;
                        else if (contextType.compareTo("hist") == 0)
                            regex_HIST_PRE = regex_HIST_PRE + "|" + phrase;
                    } else if (position.compareTo("post") == 0) {
                        if (contextType.compareTo("neg") == 0)
                            regex_NEG_POST = regex_NEG_POST + "|" + phrase;
                        else if (contextType.compareTo("poss") == 0)
                            regex_POSS_POST = regex_POSS_POST + "|" + phrase;
                        else if (contextType.compareTo("hist") == 0)
                            regex_HIST_POST = regex_HIST_POST + "|" + phrase;
                        else if (contextType.compareTo("exp") == 0)
                            regex_EXP_POST = regex_EXP_POST + "|" + phrase;
                    }
                }
            }
        }

        sc.close();

        general = generalTrie.build();

        if (regex_PSEUDO.length() > 0)
            regexPseudo = Pattern.compile(trimStartingAlternation(regex_PSEUDO), Pattern.CASE_INSENSITIVE); //bug fix

        //negation context
        if (regex_NEG_PRE.length() > 0)
            regexNegPre = Pattern.compile(trimStartingAlternation(regex_NEG_PRE), Pattern.CASE_INSENSITIVE);
        if (regex_NEG_POST.length() > 0)
            regexNegPost = Pattern.compile(trimStartingAlternation(regex_NEG_POST), Pattern.CASE_INSENSITIVE);
        if (regex_NEG_END.length() > 0)
            regexNegEnd = Pattern.compile(trimStartingAlternation(regex_NEG_END), Pattern.CASE_INSENSITIVE);
        if (regex_POSS_PRE.length() > 0)
            regexPossPre = Pattern.compile(trimStartingAlternation(regex_POSS_PRE), Pattern.CASE_INSENSITIVE);
        if (regex_POSS_POST.length() > 0)
            regexPossPost = Pattern.compile(trimStartingAlternation(regex_POSS_POST), Pattern.CASE_INSENSITIVE);

        //temporality context
        if (regex_HIST_PRE.length() > 0)
            regexHistPre = Pattern.compile(trimStartingAlternation(regex_HIST_PRE), Pattern.CASE_INSENSITIVE);
        if (regex_HIST_POST.length() > 0)
            regexHistPost = Pattern.compile(trimStartingAlternation(regex_HIST_POST), Pattern.CASE_INSENSITIVE);
        if (regex_HYPO_PRE.length() > 0)
            regexHypoPre = Pattern.compile(trimStartingAlternation(regex_HYPO_PRE), Pattern.CASE_INSENSITIVE);
        if (regex_HIST_END.length() > 0)
            regexHistEnd = Pattern.compile(trimStartingAlternation(regex_HIST_END), Pattern.CASE_INSENSITIVE);
        if (regex_HYPO_END.length() > 0)
            regexHypoEnd = Pattern.compile(trimStartingAlternation(regex_HYPO_END), Pattern.CASE_INSENSITIVE);

        //experiencer and mixed
        if (regex_EXP_PRE.length() > 0)
            regexExpPre = Pattern.compile(trimStartingAlternation(regex_EXP_PRE), Pattern.CASE_INSENSITIVE);
        if (regex_EXP_POST.length() > 0)
            regexExpPost = Pattern.compile(trimStartingAlternation(regex_EXP_POST), Pattern.CASE_INSENSITIVE);
        if (regex_EXP_END.length() > 0)
            regexExpEnd = Pattern.compile(trimStartingAlternation(regex_EXP_END), Pattern.CASE_INSENSITIVE);
        if (regex_HYPO_EXP_END.length() > 0)
            regexHypoExpEnd = Pattern.compile(trimStartingAlternation(regex_HYPO_EXP_END), Pattern.CASE_INSENSITIVE);
        if (regex_HIST_EXP_END.length() > 0)
            regexHistExpEnd = Pattern.compile(trimStartingAlternation(regex_HIST_EXP_END), Pattern.CASE_INSENSITIVE);

        // Add hardcoded temporal contexts
        regexTime = Pattern.compile("((1[4-9]|[1-9]?[2-9][0-9])[\\s-]days? of)|" +
                "(([2-9]|[1-9][0-9])[\\s-]weeks? of)|" +
                "(([1-9]?[0-9])[\\s-](?:months?|years?) of)");//pattern to recognize expressions of >14 days
        regexTimeFor = Pattern.compile("(?:for|over) the [lp]ast (((1[4-9]|[1-9]?[2-9][0-9])[ |-]days? of)|" +
                "(([2-9]|[1-9][0-9])[\\s-]weeks? of)|" +
                "(([1-9]?[0-9])[\\s-](?:months?|years?) of))");//other pattern to recognize expressions of >14 days
        regexTimeSince = Pattern.compile("since (?:(the )?last)? ((([2-9]|[1-9][0-9]) weeks ago)|" +
                "(([1-9]?[0-9])? (?:months?|years?) ago)|" +
                "((?:january|february|march|april|may|june|july|august|september|october|november|december|spring|summer|fall|winter)))");
    }

    private String trimStartingAlternation(String ret) {
        return ret.startsWith("|") ? ret.substring(1) : ret;
    }

    public Pattern getRegexPseudo() {
        return regexPseudo;
    }

    public Pattern getRegexNegPre() {
        return regexNegPre;
    }

    public Pattern getRegexNegPost() {
        return regexNegPost;
    }

    public Pattern getRegexPossPre() {
        return regexPossPre;
    }

    public Pattern getRegexPossPost() {
        return regexPossPost;
    }

    public Pattern getRegexNegEnd() {
        return regexNegEnd;
    }

    public Pattern getRegexExpPre() {
        return regexExpPre;
    }

    public Pattern getRegexExpEnd() {
        return regexExpEnd;
    }

    public Pattern getRegexHypoPre() {
        return regexHypoPre;
    }

    public Pattern getRegexHypoEnd() {
        return regexHypoEnd;
    }

    public Pattern getRegexHypoExpEnd() {
        return regexHypoExpEnd;
    }

    public Pattern getRegexHistPre() {
        return regexHistPre;
    }

    public Pattern getRegexHistEnd() {
        return regexHistEnd;
    }

    public Pattern getRegexHistExpEnd() {
        return regexHistExpEnd;
    }

    public Pattern getRegexTime() {
        return regexTime;
    }

    public Pattern getRegexTimeFor() {
        return regexTimeFor;
    }

    public Pattern getRegexTimeSince() {
        return regexTimeSince;
    }

    public Pattern getRegexExpPost() {
        return regexExpPost;
    }

    public Pattern getRegexHistPost() {
        return regexHistPost;
    }

    public Trie getGeneral() {
        return general;
    }

    public Map<String, Map<TriggerType, Set<ConText>>> getGeneralTriggerDict() {
        return generalTriggerDict;
    }

}