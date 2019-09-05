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
package org.ohnlp.medtagger.lvg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import org.ohnlp.typesystem.type.syntax.WordToken;

public class LvgLookup extends JCasAnnotator_ImplBase {

	// LOG4J logger based on class name
	private Logger logger = Logger.getLogger("LvgLookup");
     HashMap<String, String> lvgMap;
    HashSet<String> openclass; 
    //private static OpenClassWords pds = new OpenClassWords();
    
    
	//for the stand alone version
	public String getNorm(String phrase) {
		String lower = phrase.toLowerCase();
		String ret="";
		for (String word : lower.split("\\s")){
			if(openclass.contains(word)) 
				ret+=" "+ ("");
	        else if(lvgMap.containsKey(word)) 
	        	ret+=" "+ (lvgMap.get(word));
	        else ret+=" "+ (word);
		}
        return ret.trim();
  	}
	
    
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		JFSIndexRepository indexes = aJCas.getJFSIndexRepository();
		Iterator<?> tokenItr = indexes.getAnnotationIndex(WordToken.type).iterator();
		while (tokenItr.hasNext())
        {
        	WordToken token = (WordToken) tokenItr.next();
        	String lower = token.getCoveredText().toLowerCase();
        	if(openclass.contains(lower)){
        		token.setCanonicalForm(null);
        	}
        	else if(lvgMap.containsKey(lower)){
        		token.setCanonicalForm(lvgMap.get(lower));
        	}
        	else {
        		token.setCanonicalForm(lower);
        	}
        }

	}
	
	public LvgLookup() {

	}
	
	
//	//for the stand alone version
	public LvgLookup(UimaContext aContext) throws ResourceAccessException {
		localInitialize(aContext.getResourceAsStream("lvg_dict"), aContext.getResourceAsStream("openclass"));
	}

//	public void localInitialize(String dict, String openclassFile) {
//		try {
//			Scanner sc = new Scanner(new File(dict));
//			logger.info("loading LVG condensed dictionary from:" +dict);
//			int count=0;
//			while(sc.hasNextLine()){
//				String line=sc.nextLine();
//				   if(line.startsWith("#")) continue;
//
//				count++;
//			}
//			sc.close();
//
//			sc = new Scanner(new File(dict));
//			lvgMap = new HashMap<>(count);
//			while(sc.hasNextLine()){
//				 String line=sc.nextLine();
//			     if(line.startsWith("#")) continue;
//
//				String[] splits=line.split("\\|");
//				if(splits.length<2) continue;
//				lvgMap.put(splits[0], splits[1]);
//			}
//			sc.close();
//			logger.info("loaded resource, lines=" +count);
//
//			sc = new Scanner(new File(openclassFile));
//			while(sc.hasNextLine()){
//				     String line=sc.nextLine();
//				     if(line.startsWith("#")) continue;
//				String[] splits=line.split("\\s*,\\s*");
//				openclass = new HashSet<String>(Arrays.asList(splits));
//				break;
//			}
//			sc.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	public void localInitialize(InputStream dictStream, InputStream openclassStream) {

		logger.info("loading LVG condensed dictionary from dictStream");
		int count=0;

		Scanner sc = new Scanner(dictStream);
		lvgMap = new HashMap<>(count);
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.startsWith("#"))
				continue;
			count++;

			String[] splits=line.split("\\|");
			if(splits.length < 2)
				continue;
			lvgMap.put(splits[0], splits[1]);
		}
		sc.close();

		logger.info("loaded resource, lines=" + count);

		sc = new Scanner(openclassStream);
		while(sc.hasNextLine()){
			String line=sc.nextLine();
			if(line.startsWith("#")) continue;
			String[] splits=line.split("\\s*,\\s*");
			openclass = new HashSet<String>(Arrays.asList(splits));
			break;
		}
		sc.close();
	}


	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		try {
//			String dict = aContext.getResourceFilePath("lvg_dict");
//			String openclassFile = aContext.getResourceFilePath("openclass");
//			localInitialize(dict, openclassFile);

			InputStream dictInStream = aContext.getResourceAsStream("lvg_dict");
			InputStream openclassFileStream = aContext.getResourceAsStream("openclass");

			localInitialize(dictInStream, openclassFileStream);

		} catch (ResourceAccessException e) {
			e.printStackTrace();
		} 
	}

}
