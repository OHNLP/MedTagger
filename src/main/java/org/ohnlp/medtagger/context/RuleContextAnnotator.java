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
package org.ohnlp.medtagger.context;


import java.util.ArrayList;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.resource.ResourceInitializationException;

import org.ohnlp.medtagger.type.ConceptMention;

import org.ohnlp.typesystem.type.textspan.Sentence;
import org.ohnlp.medtagger.context.impl.ConText;
import org.ohnlp.medtagger.context.impl.ConText.NegationContext;
import org.ohnlp.medtagger.context.impl.ConText.TemporalityContext;


public class RuleContextAnnotator extends JCasAnnotator_ImplBase {

	ConText conText=null;
	String contextFile=null;
	private String PARAM_CONTEXT_FILE = "context_file";
	
	//probable~|~pre~|~poss
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		try {
			contextFile= aContext.getResourceFilePath(PARAM_CONTEXT_FILE);
			conText = new ConText(contextFile);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		//iterate over sentence
		JFSIndexRepository indexes = jCas.getJFSIndexRepository();
		Iterator<?> sentItr = indexes.getAnnotationIndex(Sentence.type).iterator();
		while (sentItr.hasNext()) {
			Sentence sent = (Sentence) sentItr.next();
			Iterator<?> cmItr = indexes.getAnnotationIndex(ConceptMention.type).subiterator(sent);
			ArrayList<ConceptMention> nes = new ArrayList<ConceptMention>();
			while (cmItr.hasNext())
			{
				ConceptMention cm = (ConceptMention) cmItr.next();
				nes.add(cm);  
			}
			if(nes.size()==0) continue;

			for (ConceptMention ne : nes) {
								
				String tagged = conText.preProcessSentence(sent.getCoveredText().toLowerCase(), ne.getCoveredText().toLowerCase());
				if(tagged==null)
					continue;
				
				//tokenizing the sentence in words
				String[] words =  tagged.split("[,;\\s]+");
				
				String neg = conText.applyNegEx(words);
				String tmp = conText.applyTemporality(words);
				String subj = conText.applyExperiencer(words);
				
				if(neg.equals(NegationContext.Negated.name())) ne.setCertainty("Negated");
				else if(neg.equals(NegationContext.Possible.name())) ne.setCertainty("Possible");
				else if (neg.equals(NegationContext.Affirmed.name())) ne.setCertainty("Positive");
				else ne.setCertainty("UNK");
				if(tmp.equals(TemporalityContext.Hypothetical.name())) ne.setCertainty("Possible");
				else if(tmp.equals(TemporalityContext.Historical.name()) && !subj.equals("Patient")) ne.setStatus("FamilyHistoryOf");
				else if(tmp.equals(TemporalityContext.Historical.name())) ne.setStatus("HistoryOf");
				else ne.setStatus("Present");
				ne.setExperiencer(subj);	
			}
		}

	}

}
