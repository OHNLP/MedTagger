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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class used to analyze concept context (based on the 'ConText' algorithm by Chapman et al.)
 * Adapted from Julien Thibault, Stephane Meystre, Oscar Ferrandez-Escamez
 * Department of Biomedical Informatics, University of Utah, 2011
 */
public class ConTextLegacy {

    public enum NegationContext{
        Affirmed, Negated, Possible;
    }
    public enum TemporalityContext{
        Recent, Historical, Hypothetical;
    }

    public static void main(String[] args){
        String neg = "Negated";
        System.out.print(neg.equals(NegationContext.Negated.name()));
    }

    protected static /*final*/ int MAX_WINDOW = 15;  // was 'private' 'final'  with value '15' .  Experimenting with this value for applying window size SPM


    private Pattern regexPseudo;

    private Pattern regexNegPre;
    private Pattern regexNegPost;
    private Pattern regexPossPre;
    private Pattern regexPossPost;
    private Pattern regexNegEnd;

    private Pattern regexExpPre;
    private Pattern regexExpEnd;

    private Pattern regexHypoPre;
    private Pattern regexHypoEnd;
    private Pattern regexHypoExpEnd;

    private Pattern regexHistPre;
    private Pattern regexHist1w;
    private Pattern regexHistEnd;
    private Pattern regexHistExpEnd;

    private Pattern regexTime;
    private Pattern regexTimeFor;
    private Pattern regexTimeSince;

    //originally this pattern recognized UMLS concepts, but for this application
    //it will recognize the input concepts
    private static final String regExUmlsTag = "\\[\\d+\\]";


    /**
     * Initialization regex (load parameters)
     * @param sizeCxtWindow
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ConTextLegacy(/*String*/Object contextFile, int typeInput, int sizeCxtWindow) throws FileNotFoundException
    {
        MAX_WINDOW = sizeCxtWindow;
        //SPM Signature change to allow for alternate input streams
        Scanner sc;
        String regex_PSEUDO = "";
        String regex_NEG_PRE = "";
        String regex_NEG_POST = "";
        String regex_POSS_PRE = "";
        String regex_POSS_POST = "";
        String regex_NEG_END = "";

        String regex_EXP_PRE = "";
        String regex_EXP_END = "";

        String regex_HYPO_PRE = "";
        String regex_HIST_PRE = "";
        String regex_HIST_1W = "";

        String regex_HYPO_END = "";
        String regex_HIST_END = "";
        String regex_HIST_EXP_END = "";
        String regex_HYPO_EXP_END = "";
        //SPM Signature change to allow for alternate input streams: input stream > 0 or file == 0 to be handled
        if (typeInput == 0) {
            sc = new Scanner(new File((String)contextFile));
        } else {
            sc = new Scanner((InputStream)contextFile);
        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.startsWith("#")) continue;

            if (!(line.startsWith("//"))) {
                String[] tmp=line.split("~\\|~");
                String phrase = tmp[0].replaceAll(" ", "[\\\\s\\\\-]");
                String position = tmp[1];
                String contextType = tmp[2];

                if (position.compareTo("pseudo")==0)
                {
                    regex_PSEUDO = regex_PSEUDO + "|" + phrase;
                }
                else if (position.compareTo("termin")==0)
                {
                    if (contextType.compareTo("neg")==0)
                        regex_NEG_END = regex_NEG_END + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("hypo")==0)
                        regex_HYPO_END = regex_HYPO_END + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("hist")==0)
                        regex_HIST_END = regex_HIST_END + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("histexp")==0)
                        regex_HIST_EXP_END = regex_HIST_EXP_END + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("hypoexp")==0)
                        regex_HYPO_EXP_END = regex_HYPO_EXP_END + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("exp")==0)
                        regex_EXP_END = regex_EXP_END + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                }
                else if (position.compareTo("pre")==0)
                {
                    if (contextType.compareTo("neg")==0)
                        regex_NEG_PRE = regex_NEG_PRE + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("poss")==0)
                        regex_POSS_PRE = regex_POSS_PRE + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("hypo")==0)
                        regex_HYPO_PRE = regex_HYPO_PRE + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("exp")==0)
                        regex_EXP_PRE = regex_EXP_PRE + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("hist")==0)
                        regex_HIST_PRE = regex_HIST_PRE + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("hist")==0)
                        regex_HIST_1W = regex_HIST_1W + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                }
                else if (position.compareTo("post")==0)
                {
                    if (contextType.compareTo("neg")==0)
                        regex_NEG_POST = regex_NEG_POST + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                    else if (contextType.compareTo("poss")==0)
                        regex_POSS_POST = regex_POSS_POST + "|[\\s\\.]+" + phrase + "[\\s\\.\\:;\\,]+";
                }

            }
        }

        sc.close();

        if (regex_PSEUDO.length()>0)
            regexPseudo = Pattern.compile(regex_PSEUDO.substring(1)); //bug fix

        //negation context
        if (regex_NEG_PRE.length()>0)
            regexNegPre = Pattern.compile(regex_NEG_PRE.substring(1));
        if (regex_NEG_POST.length()>0)
            regexNegPost = Pattern.compile(regex_NEG_POST.substring(1));
        if (regex_NEG_END.length()>0)
            regexNegEnd = Pattern.compile(regex_NEG_END.substring(1));
        if (regex_POSS_PRE.length()>0)
            regexPossPre = Pattern.compile(regex_POSS_PRE.substring(1));
        if (regex_POSS_POST.length()>0)
            regexPossPost = Pattern.compile(regex_POSS_POST.substring(1));

        //temporality context
        if (regex_HIST_PRE.length()>0)
            regexHistPre = Pattern.compile(regex_HIST_PRE.substring(1));
        if (regex_HYPO_PRE.length()>0)
            regexHypoPre = Pattern.compile(regex_HYPO_PRE.substring(1));
        if (regex_HIST_1W.length()>0)
            regexHist1w = Pattern.compile(regex_HIST_1W.substring(1));
        if (regex_HIST_END.length()>0)
            regexHistEnd = Pattern.compile(regex_HIST_END.substring(1));
        if (regex_HYPO_END.length()>0)
            regexHypoEnd = Pattern.compile(regex_HYPO_END.substring(1));

        //experiencer and mixed
        if (regex_EXP_PRE.length()>0)
            regexExpPre = Pattern.compile(regex_EXP_PRE.substring(1));
        if (regex_EXP_END.length()>0)
            regexExpEnd = Pattern.compile(regex_EXP_END.substring(1));
        if (regex_HYPO_EXP_END.length()>0)
            regexHypoExpEnd = Pattern.compile(regex_HYPO_EXP_END.substring(1));
        if (regex_HIST_EXP_END.length()>0)
            regexHistExpEnd = Pattern.compile(regex_HIST_EXP_END.substring(1));

    }

    /**
     * Pre-processing on the sentence (replace concepts and negation terms by keywords)
     * @param sent
     * @return Tagged sentence (concepts and context base terms)
     * @throws Exception
     */
    public String preProcessSentence(String sent, String concept, int cBegin)
    {
        String tag= " [0] ";
        int sbegin=cBegin-2;
        if(sbegin<0) sbegin=0;
        int conceptIndex = sent.substring(sbegin).indexOf(concept);
        String sentenceTagged=" "+sent;
        String cSen=sentenceTagged;
        if(conceptIndex != -1)
            sentenceTagged = sentenceTagged.substring(0,sbegin+conceptIndex) + tag + sentenceTagged.substring(sbegin+conceptIndex+concept.length()+1);
        if(sentenceTagged.equals(cSen))
            return null;
        sentenceTagged =  sentenceTagged.replaceAll("-", " ");
        //sentenceTagged =  sentenceTagged.replaceAll("\\s+", " ");
        sentenceTagged =  sentenceTagged.replaceAll(" +", " ");
        Vector <String> taggedSentences=new Vector <String>();
        //replacing negation phrases with corresponding tags

        //negation phrases
        if (regexPseudo != null){
            Matcher m0 = regexPseudo.matcher(sentenceTagged);
            //	sentenceTagged = m0.replaceAll(" <NEG_PSEUDO> ");
            taggedSentences.add(m0.replaceAll(" <NEG_PSEUDO> "));
        }
        if (regexNegPre != null){
            Matcher m1 = regexNegPre.matcher(sentenceTagged);
            //	sentenceTagged = m1.replaceAll(" <NEG_PRE> ");
            taggedSentences.add(m1.replaceAll(" <NEG_PRE> "));
        }
        if (regexPossPre != null){
            Matcher m2 = regexPossPre.matcher(sentenceTagged);
            //	sentenceTagged = m2.replaceAll(" <POSS_PRE> ");
            taggedSentences.add(m2.replaceAll(" <POSS_PRE> "));

        }
        if (regexNegPost != null){
            Matcher m3 = regexNegPost.matcher(sentenceTagged);
            //	sentenceTagged = m3.replaceAll(" <NEG_POST> ");
            taggedSentences.add(m3.replaceAll(" <NEG_POST> "));
        }
        if (regexPossPost != null){
            Matcher m4 = regexPossPost.matcher(sentenceTagged);
            //	sentenceTagged = m4.replaceAll(" <POSS_POST> ");
            taggedSentences.add(m4.replaceAll(" <POSS_POST> "));
        }

        for(int i=0; i<taggedSentences.size(); i++){
            String sen=taggedSentences.get(i);
            String[] words1=sen.split(" +"); //bug fix
            String[] words=sentenceTagged.split(" +"); //bug fix
            if(words1.length < words.length) sentenceTagged=sen;
            if(words1.length==words.length && (sen.indexOf("PRE")>0 || sen.indexOf("POST") >=0)) sentenceTagged=sen;
        }
        if (regexNegEnd != null){
            Matcher m5 = regexNegEnd.matcher(sentenceTagged);
            sentenceTagged = m5.replaceAll(" <NEG_END> ");
            //	taggedSentences.add(m5.replaceAll(" <NEG_END> "));
        }


        //experiencer phrases
        if (regexExpPre != null){
            Matcher m6 = regexExpPre.matcher(sentenceTagged);
            sentenceTagged = m6.replaceAll(" <EXP_PRE> ");
        }
        if (regexExpEnd != null){
            Matcher m14 = regexExpEnd.matcher(sentenceTagged);
            sentenceTagged = m14.replaceAll(" <EXP_END> ");
        }

        //hypothesis
        if (regexHypoPre != null){
            Matcher m7 = regexHypoPre.matcher(sentenceTagged);
            sentenceTagged = m7.replaceAll(" <HYPO_PRE> ");
        }
        if (regexHypoEnd != null){
            Matcher m10 = regexHypoEnd.matcher(sentenceTagged);
            sentenceTagged = m10.replaceAll(" <HYPO_END> ");
        }

        //temporality
        if (regexHistPre != null){
            Matcher m8 = regexHistPre.matcher(sentenceTagged);
            sentenceTagged = m8.replaceAll(" <HIST_PRE> ");
        }
        if (regexHist1w != null){
            Matcher m9 = regexHist1w.matcher(sentenceTagged);
            sentenceTagged = m9.replaceAll(" <HIST_1W> ");
        }
        if (regexHistEnd != null){
            Matcher m12 = regexHistEnd.matcher(sentenceTagged);
            sentenceTagged = m12.replaceAll(" <HIST_END> ");
        }

        // mixed
        if (regexHypoExpEnd != null){
            Matcher m11 = regexHypoExpEnd.matcher(sentenceTagged);
            sentenceTagged = m11.replaceAll(" <HYPO_EXP_END> ");
        }
        if (regexHistExpEnd != null){
            Matcher m13 = regexHistExpEnd.matcher(sentenceTagged);
            sentenceTagged = m13.replaceAll(" <HIST_EXP_END> ");
        }

        //time
        regexTime = Pattern.compile("((1[4-9]|[1-9]?[2-9][0-9])[ |-][day|days] of)|" +
                "(([2-9]|[1-9][0-9])[ |-][week|weeks] of)|" +
                "(([1-9]?[0-9])[ |-][month|months|year|years] of)");//pattern to recognize expressions of >14 days
        regexTimeFor = Pattern.compile("[for|over] the [last|past] (((1[4-9]|[1-9]?[2-9][0-9])[ |-][day|days] of)|" +
                "(([2-9]|[1-9][0-9])[ |-][week|weeks] of)|" +
                "(([1-9]?[0-9])[ |-][month|months|year|years] of))");//other pattern to recognize expressions of >14 days
        regexTimeSince = Pattern.compile("since [last|the last]? ((([2-9]|[1-9][0-9]) weeks ago)|" +
                "(([1-9]?[0-9])? [month|months|year|years] ago)|" +
                "([january|february|march|april|may|june|july|august|september|october|november|december|spring|summer|fall|winter]))");
        Matcher mTime = regexTimeFor.matcher(sentenceTagged);
        sentenceTagged = mTime.replaceAll(" <TIME_PRE> ");
        mTime = regexTime.matcher(sentenceTagged);
        sentenceTagged = mTime.replaceAll(" <TIME_PRE> ");
        mTime = regexTimeSince.matcher(sentenceTagged);
        sentenceTagged = mTime.replaceAll(" <TIME_POST> ");
        return sentenceTagged;
    }


    /**
     * Pre-processing on the sentence (replace concepts and negation terms by keywords)
     * @param sent
     * @return Tagged sentence (concepts and context base terms)
     * @throws Exception
     */
    public String preProcessSentence(String sent, String concept)
    {
        //modified by Sunghwan to use "\n" in context (06-17-2014) (eg, PAD NO\n)
        //String sentenceTagged = " " + sent.replaceAll("\\s+", " ").toLowerCase();
        String sentenceTagged = " " + sent.replaceAll(" +", " ").toLowerCase();

        int lastOffset = 0;
        int charOffset=0;

        String tag="";
        //String umlsConcept = concept.replaceAll("\\s+", " ").toLowerCase();
        String umlsConcept = concept.replaceAll(" +", " ").toLowerCase();
        tag = " [0] ";

        int conceptIndex = sentenceTagged.indexOf(umlsConcept);
        if (conceptIndex != -1)
        {
            charOffset = conceptIndex;
            sentenceTagged = sentenceTagged.substring(0,charOffset) + tag + sentenceTagged.substring(charOffset+umlsConcept.length());
            lastOffset = charOffset + tag.length();
        }
        else
            return null;

        Vector <String> taggedSentences=new Vector <String>();
        //replacing negation phrases with corresponding tags

        //negation phrases
        if (regexPseudo != null){
            Matcher m0 = regexPseudo.matcher(sentenceTagged);
            //	sentenceTagged = m0.replaceAll(" <NEG_PSEUDO> ");
            taggedSentences.add(m0.replaceAll(" <NEG_PSEUDO> "));
        }
        if (regexNegPre != null){
            Matcher m1 = regexNegPre.matcher(sentenceTagged);
            //	sentenceTagged = m1.replaceAll(" <NEG_PRE> ");
            taggedSentences.add(m1.replaceAll(" <NEG_PRE> "));
        }
        if (regexPossPre != null){
            Matcher m2 = regexPossPre.matcher(sentenceTagged);
            //	sentenceTagged = m2.replaceAll(" <POSS_PRE> ");
            taggedSentences.add(m2.replaceAll(" <POSS_PRE> "));
        }
        if (regexNegPost != null){
            Matcher m3 = regexNegPost.matcher(sentenceTagged);
            //	sentenceTagged = m3.replaceAll(" <NEG_POST> ");
            taggedSentences.add(m3.replaceAll(" <NEG_POST> "));
        }
        if (regexPossPost != null){
            Matcher m4 = regexPossPost.matcher(sentenceTagged);
            //	sentenceTagged = m4.replaceAll(" <POSS_POST> ");
            taggedSentences.add(m4.replaceAll(" <POSS_POST> "));
        }

        for(int i=0; i<taggedSentences.size(); i++){
            String sen=taggedSentences.get(i);
            System.out.println(sen);
            String[] words1=sen.split(" +"); //bug fix
            String[] words=sentenceTagged.split(" +"); //bug fix
            if(words1.length < words.length) sentenceTagged=sen;
            if(words1.length==words.length && (sen.indexOf("PRE")>0 || sen.indexOf("POST") >=0)) sentenceTagged=sen;
        }
        if (regexNegEnd != null){
            Matcher m5 = regexNegEnd.matcher(sentenceTagged);
            sentenceTagged = m5.replaceAll(" <NEG_END> ");
            //	taggedSentences.add(m5.replaceAll(" <NEG_END> "));
        }


        //experiencer phrases
        if (regexExpPre != null){
            Matcher m6 = regexExpPre.matcher(sentenceTagged);
            sentenceTagged = m6.replaceAll(" <EXP_PRE> ");
        }
        if (regexExpEnd != null){
            Matcher m14 = regexExpEnd.matcher(sentenceTagged);
            sentenceTagged = m14.replaceAll(" <EXP_END> ");
        }

        //hypothesis
        if (regexHypoPre != null){
            Matcher m7 = regexHypoPre.matcher(sentenceTagged);
            sentenceTagged = m7.replaceAll(" <HYPO_PRE> ");
        }
        if (regexHypoEnd != null){
            Matcher m10 = regexHypoEnd.matcher(sentenceTagged);
            sentenceTagged = m10.replaceAll(" <HYPO_END> ");
        }

        //temporality
        if (regexHistPre != null){
            Matcher m8 = regexHistPre.matcher(sentenceTagged);
            sentenceTagged = m8.replaceAll(" <HIST_PRE> ");
        }
        if (regexHist1w != null){
            Matcher m9 = regexHist1w.matcher(sentenceTagged);
            sentenceTagged = m9.replaceAll(" <HIST_1W> ");
        }
        if (regexHistEnd != null){
            Matcher m12 = regexHistEnd.matcher(sentenceTagged);
            sentenceTagged = m12.replaceAll(" <HIST_END> ");
        }

        // mixed
        if (regexHypoExpEnd != null){
            Matcher m11 = regexHypoExpEnd.matcher(sentenceTagged);
            sentenceTagged = m11.replaceAll(" <HYPO_EXP_END> ");
        }
        if (regexHistExpEnd != null){
            Matcher m13 = regexHistExpEnd.matcher(sentenceTagged);
            sentenceTagged = m13.replaceAll(" <HIST_EXP_END> ");
        }

        //time
        regexTime = Pattern.compile("((1[4-9]|[1-9]?[2-9][0-9])[ |-][day|days] of)|" +
                "(([2-9]|[1-9][0-9])[ |-][week|weeks] of)|" +
                "(([1-9]?[0-9])[ |-][month|months|year|years] of)");//pattern to recognize expressions of >14 days
        regexTimeFor = Pattern.compile("[for|over] the [last|past] (((1[4-9]|[1-9]?[2-9][0-9])[ |-][day|days] of)|" +
                "(([2-9]|[1-9][0-9])[ |-][week|weeks] of)|" +
                "(([1-9]?[0-9])[ |-][month|months|year|years] of))");//other pattern to recognize expressions of >14 days
        regexTimeSince = Pattern.compile("since [last|the last]? ((([2-9]|[1-9][0-9]) weeks ago)|" +
                "(([1-9]?[0-9])? [month|months|year|years] ago)|" +
                "([january|february|march|april|may|june|july|august|september|october|november|december|spring|summer|fall|winter]))");
        Matcher mTime = regexTimeFor.matcher(sentenceTagged);
        sentenceTagged = mTime.replaceAll(" <TIME_PRE> ");
        mTime = regexTime.matcher(sentenceTagged);
        sentenceTagged = mTime.replaceAll(" <TIME_PRE> ");
        mTime = regexTimeSince.matcher(sentenceTagged);
        sentenceTagged = mTime.replaceAll(" <TIME_POST> ");
        System.out.println(" sentenceTagged : " + sentenceTagged);

        return sentenceTagged;
    }

    /**
     * Context analysis on the given sentence.
     */
    public ArrayList<String> applyContext(String concept, String sentence) throws Exception
    {
        if(concept.equals("") || sentence.equals(""))
            return null;

        String tagged = preProcessSentence(sentence, concept);
        if(tagged==null)
            return null;

        //tokenizing the sentence in words
        String[] words =  tagged.split("[,;\\s]+");

        String ne = applyNegEx(words);
        String tmp = applyTemporality(words);
        String subj = applyExperiencer(words);
        ArrayList<String> result = new ArrayList<String>();
        result.add(concept);
        result.add(sentence);
        result.add(ne);
        result.add(tmp);
        result.add(subj);

        return result;
    }

    /**
     * Apply NegEx algorithm to find negation context of the concepts found in the sentence
     * @return
     */
    public String applyNegEx(String[] words)
    {
        //Going from one negation to another, and creating the appropriate window
        int m = 0;
        List<String> window = new ArrayList<String>();
        //for each word in the sentence
        while (m < words.length)
        {
            //IF word is a pseudo-negation, skips to the next word
            if(words[m].equals("<NEG_PSEUDO>"))
            {
                m++;
            }
            //IF word is a pre- concept negation or possible...
            else if(words[m].matches("<NEG_PRE>|<POSS_PRE>"))
            {
                //find window (default is six words after the negation phrase)

                int maxWindow = MAX_WINDOW;
                if (words.length < m + maxWindow) maxWindow = words.length - m;
                for(int o=1; o < maxWindow; o++)
                {
                    if(words[m+o].matches("<NEG_PRE>|<POSS_PRE>|<NEG_POST>|<POSS_POST>|<NEG_END>"))
                        break;
                    else window.add(words[m+o]);
                }

                //get type of Negation
                NegationContext currentNegationContext = NegationContext.Affirmed;
                if (words[m].equals("<NEG_PRE>")) {
                    currentNegationContext = NegationContext.Negated;
                }
                else if(words[m].equals("<POSS_PRE>"))
                    currentNegationContext = NegationContext.Possible;

                //check if there are concepts in the window
                for(int w=0; w<window.size(); w++) {
                    if(window.get(w).matches(regExUmlsTag)){
                        String umlsWord = window.get(w);
                        //int index = Integer.parseInt(umlsWord.replaceAll("\\[|\\]",""));
                        //mappingResults.get(index).setNegationContext(currentNegationContext.name());
                        return currentNegationContext.name();
                    }
                }
                window.clear();
                m++;
            }
            //IF word a post- concept negation or possible
            else if(words[m].matches("<NEG_POST>|<POSS_POST>"))
            {
                //find window (default is six words before the negation phrase)
                int maxWindow = MAX_WINDOW;
                if (m < maxWindow) maxWindow = m;
                for(int o=1; o < maxWindow; o++) {
                    if(words[m-o].matches("<NEG_PRE>|<POSS_PRE>|<NEG_POST>|<POSS_POST>|<NEG_END>"))
                        break;
                    else
                        window.add(words[m-o]);
                }

                //get type of Negation
                NegationContext currentNegationContext = NegationContext.Affirmed;
                if (words[m].equals("<NEG_POST>")){
                    currentNegationContext = NegationContext.Negated;
                }
                else if(words[m].equals("<POSS_POST>"))
                    currentNegationContext = NegationContext.Possible;

                //check if there are concepts in the window
                for(int w=0; w<window.size(); w++) {
                    if(window.get(w).matches(regExUmlsTag)){
                        String umlsWord = window.get(w);
                        //int index = Integer.parseInt(umlsWord.replaceAll("\\[|\\]",""));
                        //mappingResults.get(index).setNegationContext(currentNegationContext.name());
                        return currentNegationContext.name();
                    }
                }
                window.clear();
                m++;
            }
            //IF word not a negation or conjunction skip
            else{
                m++;
            }
        }
        return NegationContext.Affirmed.name();
    }

    /**
     * Temporality analysis
     * @return
     */
    public String applyTemporality(String[] words)
    {

        List<String> window = new ArrayList<String>();

        int mm = 0;
        while(mm<words.length)
        {
            //IF word is a pseudo-negation, skips to the next word
            if(words[mm].equals("<NEG_PSEUDO>")) mm++;

                //IF word is a pre- hypothetical trigger term
            else if(words[mm].equals("<HYPO_PRE>")){

                //expands window until end of sentence, termination term, or other negation/possible trigger term
                for(int o=1; (mm+o)<words.length; o++) {
                    if(words[mm+o].equals("<HYPO_END>|<HYPO_EXP_END>|<HYPO_PRE>")) {
                        break;//window decreased to right before other negation or conjunction
                    }
                    else
                        window.add(words[mm+o]);
                }
                //check if there are concepts in the window
                for(int w=0; w<window.size(); w++) {
                    if(window.get(w).matches(regExUmlsTag)){
                        String umlsWord = window.get(w);
                        return TemporalityContext.Hypothetical.name();
                    }
                }
                window.clear();
                mm++;
            }
            //IF word a pre- historical trigger term
            else if(words[mm].matches("<HIST_PRE>|<TIME_PRE>")){

                //expands window until end of sentence, termination term, or other negation/possible trigger term
                for(int o=1; (mm+o)<words.length; o++) {
                    if(words[mm+o].matches("<HIST_END>|<HIST_EXP_END>|<HIST_PRE>|<HIST_1W>")) {
                        break;//window decreased to right after other negation or conjunction
                    }
                    else window.add(words[mm+o]);
                }
                //check if there are concepts in the window
                for(int w=0; w<window.size(); w++) {
                    if(window.get(w).matches(regExUmlsTag)){
                        String umlsWord = window.get(w);
                        return TemporalityContext.Historical.name();
                    }
                }
                window.clear();
                mm++;
            }
            //IF word a post- historical trigger term
            else if(words[mm].equals("<TIME_POST>")){

                //expands window until end of sentence, termination term, or other negation/possible trigger term
                for(int o=1; (mm-o)>=0; o++) {
                    if(words[mm-o].matches("<HIST_END>|<HIST_EXP_END>|<HIST_PRE>|<HIST_1W>")) {
                        break;//window decreased to right after other negation or conjunction
                    }
                    else window.add(words[mm-o]);
                }
                //check if there are concepts in the window
                for(int w=0; w<window.size(); w++) {
                    if(window.get(w).matches(regExUmlsTag)){
                        String umlsWord = window.get(w);
                        return TemporalityContext.Historical.name();
                    }
                }
                window.clear();
                mm++;
            }
            else mm++;
        }
        return TemporalityContext.Recent.name();
    }

    /**
     * Experiencer analysis
     * @return
     */
    public String applyExperiencer(String[] words)
    {
        List<String> window = new ArrayList<String>();

        //Going from one experiencer term to another, and creating the appropriate window
        int mm = 0;
        while(mm<words.length){
            //IF word is a pseudo-negation, skips to the next word
            if(words[mm].equals("<NEG_PSEUDO>")) mm++;

                //IF word is a pre- experiencer trigger term
            else if(words[mm].equals("<EXP_PRE>"))
            {
                //expands window until end of sentence, termination term, or other negation/possible trigger term
                for(int o=1; (mm+o)<words.length; o++) {
                    if(words[mm+o].equals("<EXP_END>|<HIST_EXP_END>|<HYPO_EXP_END>|<EXP_PRE>")) {
                        break; //window decreased to right before other negation or conjunction
                    }
                    else window.add(words[mm+o]);
                }
                for(int w=0; w<window.size(); w++) {
                    if(window.get(w).matches(regExUmlsTag)){
                        String umlsWord = window.get(w);
                        return "Other";
                    }
                }
                window.clear();
                mm++;
            }
            else mm++;
        }
        return "Patient";
    }

}
