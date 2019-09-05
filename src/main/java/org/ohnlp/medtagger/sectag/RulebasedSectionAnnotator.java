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

package org.ohnlp.medtagger.sectag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

//import org.ohnlp.medtagger.type.ConceptMention;

import org.ohnlp.typesystem.type.textspan.Segment;
import org.ohnlp.typesystem.type.textspan.Sentence;
import org.ohnlp.medtagger.lvg.LvgLookup;

/**
 * @author Hongfang Liu
 *
 */
public class RulebasedSectionAnnotator extends JCasAnnotator_ImplBase {

	private int sentCounter=0;

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	
		public void process(JCas jCas) throws AnalysisEngineProcessException {
		JFSIndexRepository indexes = jCas.getJFSIndexRepository();
		
		// clean the existing segment
		Iterator<?> segItr = indexes.getAnnotationIndex(Segment.type)
		.iterator();
		List<Segment> segSets = new ArrayList<Segment>();
		
		while (segItr.hasNext()) {
			Segment sen = (Segment) segItr.next();
			segSets.add(sen);
		}
		for (int i=0; i< segSets.size(); i++) {
			Segment seg = (Segment) segSets.get(i);
			seg.removeFromIndexes();
		}
		
		// start the assignment of new segment
		Iterator<?> senItr = indexes.getAnnotationIndex(Sentence.type)
				.iterator();	
		List<Sentence> senSets = new ArrayList<Sentence>();
		
		while (senItr.hasNext()) {
			Sentence sen = (Sentence) senItr.next();
			senSets.add(sen);
		}
		String cSegment="DocBegin\tDocBegin";
		Segment cSeg=new Segment(jCas);
		cSeg.setBegin(0);
		String[] splits = cSegment.split("\t");		
		cSeg.setId(splits[0]);
		cSeg.setValue(splits[1]);
		int segEnd=0;
		sentCounter=0;
		for (int i=0; i< senSets.size(); i++) {
			Sentence sen = (Sentence) senSets.get(i);
			Segment sec=SecIndicator(sen, jCas);
			if(sec!=null) { 
				if(segEnd > 0) {
					cSeg.removeFromIndexes();
					cSeg.setEnd(segEnd); 
				    cSeg.addToIndexes();
				}
				cSeg=sec;
				segEnd=cSeg.getEnd();
				}
			else{
			sen.removeFromIndexes(jCas);
			sen.setId(cSeg.getId()+":"+sentCounter);
			sentCounter++;
			sen.setSegment(cSeg);
			sen.addToIndexes();
			segEnd=sen.getEnd();
			}
		}
		if(segEnd > 0) {
			cSeg.removeFromIndexes();
			cSeg.setEnd(segEnd);
			cSeg.addToIndexes();
		}
	}


	private Segment SecIndicator(Sentence sen, JCas jcas) {
		String str=sen.getCoveredText();
		Segment cSeg=null;
		int pos=str.indexOf(":");
		if(pos < 0 || pos >=100) return null;
		String secStr=str.substring(0,pos);
		if(sectionMap.containsKey(lvg.getNorm(secStr))){
			sen.removeFromIndexes(jcas);
		    String cSegment=sectionMap.get(lvg.getNorm(secStr));
		    cSeg=new Segment(jcas);
			cSeg.setBegin(sen.getBegin());
			cSeg.setEnd(sen.getEnd());
			cSeg.addToIndexes();
			String[] splits = cSegment.split("\t");		
			if(splits.length >=0) cSeg.setId(splits[0]);
			if(splits.length >=1) cSeg.setValue(splits[1]);
			Sentence segsent=new Sentence(jcas,sen.getBegin(),sen.getBegin()+pos+1);
		    sentCounter=0;
		    segsent.setId(cSeg.getId()+":"+sentCounter);
		    segsent.setSegment(cSeg);
		    segsent.addToIndexes(jcas);
		    sentCounter++;
		    if(sen.getEnd() > sen.getBegin()+pos+1){
		    Sentence sent=new Sentence(jcas,sen.getBegin()+pos+1,sen.getEnd());
		    sent.setId(cSeg.getId()+":"+sentCounter);
		    sentCounter++;
		    sent.setSegment(cSeg);
		    sent.addToIndexes(jcas);
		}
		}
		return cSeg;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	HashMap<String, String> sectionMap;
	LvgLookup lvg;
	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		sectionMap = new HashMap<String, String>();
		try {
			lvg  = new LvgLookup(aContext);
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(aContext.getResourceAsStream("section_map"),
							"UTF-8"));

			while(br.ready()){
				String str = br.readLine();
				if(str.startsWith("#"))
					continue;
				String[] splits = str.split("\t");
				//in case of ambiguity, use the first one
				if(!sectionMap.containsKey(lvg.getNorm(splits[0]))){
					sectionMap.put(lvg.getNorm(splits[0]), splits[1]+"\t"+splits[2]);
				}
			}

			br.close();						
		} catch (ResourceAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
