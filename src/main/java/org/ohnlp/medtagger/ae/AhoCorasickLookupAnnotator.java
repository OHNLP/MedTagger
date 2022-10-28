/*******************************************************************************
 * Copyright: (c)  2013  Mayo Foundation for Medical Education and 
 *  Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 *  triple-shield Mayo logo are trademarks and service marks of MFMER.
 *  
 *  Except as contained in the copyright notice above, or as used to identify 
 *  MFMER as the author of this software, the trade names, trademarks, service
 *  marks, or product names of the copyright holder shall not be used in
 *  advertising, promotion or otherwise in connection with this software without
 *  prior written authorization of the copyright holder.
 *   
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *   
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *   
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 *******************************************************************************/

package org.ohnlp.medtagger.ae;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.ohnlp.medtagger.dict.AhoCorasickDict;
import org.ohnlp.medtagger.lvg.LvgLookup;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.syntax.BaseToken;
import org.ohnlp.typesystem.type.syntax.NumToken;
import org.ohnlp.typesystem.type.syntax.PunctuationToken;
import org.ohnlp.typesystem.type.syntax.WordToken;
import org.ohnlp.typesystem.type.textspan.Segment;
import org.ohnlp.typesystem.type.textspan.Sentence;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Hongfang Liu
 */
public class AhoCorasickLookupAnnotator extends JCasAnnotator_ImplBase {

	private boolean LONGEST = true;

    // data structure that stores the TRIE
    AhoCorasickDict btac;
    HashSet<String> stop;
    HashMap<String, String> abbr;
    // add the path in resources
    LvgLookup lvg;

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);

        try {
            lvg = new LvgLookup(aContext);
            String dictfile = (String) aContext
                    .getConfigParameterValue("dict_file");
            InputStream is;
            if (dictfile == null) {
                is = AhoCorasickLookupAnnotator.class.getResourceAsStream("/medtaggerresources/lookup/PASC.lookup.dict");
            } else {
                is = Files.newInputStream(Paths.get(URI.create(dictfile)));
            }
            btac = new AhoCorasickDict(is);
			String stpfile = (String) aContext
					.getConfigParameterValue("stop_file");
			if (stpfile == null) {
				is = AhoCorasickLookupAnnotator.class.getResourceAsStream("/medtaggerresources/lookup/stop.615");
			} else {
                is = Files.newInputStream(Paths.get(URI.create(stpfile)));
            }
            stop = new HashSet<String>();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (br.ready()) {
                String line = br.readLine();
                if (line.startsWith("#")) continue;
                stop.add(lvg.getNorm(line.substring(line.indexOf('\t') + 1,
                        line.indexOf('|'))));
            }
            br.close();

            abbr = new HashMap<String, String>();
            String abrfile = (String) aContext
                    .getConfigParameterValue("abbr_file");
			if (abrfile == null) {
				is = AhoCorasickLookupAnnotator.class.getResourceAsStream("/medtaggerresources/lookup/OHNLP_ohdsi.abbr");
			} else {
                is =  Files.newInputStream(Paths.get(URI.create(abrfile)));
            }
            br = new BufferedReader(new InputStreamReader(is));
            while (br.ready()) {
                String line = br.readLine();
                if (line.startsWith("#")) continue;

                // add even those not in stop word list
                // abbr.add(line.substring(0, line.indexOf('|')));
                abbr.put(line.split("\\|")[0], line.split("\\|")[1]);
            }
            br.close();

        } catch (ResourceAccessException | IOException e) {
            throw new ResourceInitializationException(e);
        }

    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        JFSIndexRepository indexes = jCas.getJFSIndexRepository();
        Iterator<?> segItr = indexes.getAnnotationIndex(Segment.type)
                .iterator();
        while (segItr.hasNext()) {
            Segment seg = (Segment) segItr.next();

            Iterator<?> sentItr = indexes.getAnnotationIndex(Sentence.type)
                    .subiterator(seg);
            while (sentItr.hasNext()) {
                ArrayList<String> normTokens = new ArrayList<String>();
                HashMap<Integer, Integer> begins = new HashMap<Integer, Integer>();
                HashMap<Integer, Integer> ends = new HashMap<Integer, Integer>();

                Sentence sent = (Sentence) sentItr.next();
                int b = sent.getBegin();
                int e = sent.getEnd();

                // TODO: do we care only about word tokens, or do we worry about
                // contractions, symbols, punctuations and numbers too?
                Iterator<?> tokenItr = indexes.getAnnotationIndex(
                        BaseToken.type).subiterator(sent);

                while (tokenItr.hasNext()) {
                    BaseToken token = (BaseToken) tokenItr.next();

                    if (token instanceof WordToken) {
                        if (((WordToken) token).getCanonicalForm() == null)
                            continue;
                        normTokens.add(((WordToken) token).getCanonicalForm());
                        // storing the begins and ends for future use
                        begins.put(normTokens.size() - 1, token.getBegin());
                        ends.put(normTokens.size() - 1, token.getEnd());
                    }
                    if (token instanceof NumToken) {
                        normTokens.add(token.getCoveredText());
                        // storing the begins and ends for future use
                        begins.put(normTokens.size() - 1, token.getBegin());
                        ends.put(normTokens.size() - 1, token.getEnd());
                    }
                    if (token instanceof PunctuationToken) {
                        String tktext = token.getCoveredText();
                        if (tktext.equals(">") || tktext.equals("<") || tktext.equals("=")) {
                            normTokens.add(token.getCoveredText());
                            // storing the begins and ends for future use
                            begins.put(normTokens.size() - 1, token.getBegin());
                            ends.put(normTokens.size() - 1, token.getEnd());

                        }
                    }
                }

                String[] tokens = normTokens.toArray(new String[0]);
                ArrayList<Vector<String>> tags = new ArrayList<Vector<String>>(
                        tokens.length);
                for (int i = 0; i < tokens.length; i++) {
                    tags.add(new Vector<String>());
                }

                // TODO: debug this later
                if (tokens.length > 200)
                    continue;

                // debugging
                // logger.debug(btac.root.phrase);
                btac.find(tokens, 0, btac.root, tags);

                // loading stuff into CAS
                for (int count = 0; count < tags.size(); count++) {
                    for (int lcount = 0; lcount < tags.get(count).size(); lcount++) {
                        if (LONGEST && lcount != tags.get(count).size() - 1)
                            continue;

                        String con = tags.get(count).get(lcount);
                        int size = Integer.parseInt(con.substring(0,
                                con.indexOf(":")));
                        int begin = begins.get(count);
                        int end = ends.get(count + size - 1);
                        String code = con.substring(con.indexOf(":") + 2);
                        String[] multiples = code.split("\\|\\|");
                        String concept_code = null;
                        String sem_group = null;
                        for (int multiple = 0; multiple < multiples.length; multiple++) {
                            String[] splits = multiples[multiple].split("\\|");

                            String text = sent.getCoveredText().substring(
                                    begin - b, end - b);
                            if (stop.contains(lvg.getNorm(text))
                                    && !abbr.containsKey(splits[0]))
                                continue;

                            if (abbr.containsKey(lvg.getNorm(text))
                                    && (!abbr.get(lvg.getNorm(text)).equals(
                                    text) || (begin - b > 0 && !sent
                                    .getCoveredText()
                                    .substring(begin - b - 1, begin - b)
                                    .equals(" "))))
                                continue;

                            concept_code = splits.length > 1 ? splits[1] : concept_code;
                            sem_group = splits.length > 2 ? splits[2] : "";

                            if (concept_code == null) {
                                System.out.println("Null concept code: " + con);
                                continue;
                            }

                            ConceptMention neAnnot = new ConceptMention(
                                    jCas, begin, end);
                            neAnnot.setNormTarget(concept_code);
                            neAnnot.setSemGroup(sem_group);
                            neAnnot.setDetectionMethod("DictionaryLookup");
                            neAnnot.setSentence(sent);
                            neAnnot.addToIndexes();
                        }

                        if (LONGEST && lcount == tags.get(count).size() - 1) {
                            count = count + size - 1;
                            break;
                        }
                    }

                }
            }
        }
    }

}

