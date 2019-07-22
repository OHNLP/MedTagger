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

package org.ohnlp.medtagger.ml.ae;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;

import org.ohnlp.typesystem.type.syntax.BaseToken;
import org.ohnlp.typesystem.type.syntax.NewlineToken;

import org.ohnlp.medtagger.ml.type.shareToken;

/*
 * in java if the substring is indexed as x,y
 * the corresponding UIMA span begin,end are x and y
 * and i2b2 span begin,end are x+1 and y+1 , as character positions begin with 1 
 * newlines are counted as one character in java,UIMA and i2b2
 */

public class ShareTokenizer extends JCasAnnotator_ImplBase {  
    
   public void process(JCas jCas) throws AnalysisEngineProcessException {
		JFSIndexRepository indexes = jCas.getJFSIndexRepository();
		Iterator<?> tokenItr = indexes.getAnnotationIndex(BaseToken.type)
				.iterator();
		int lineTokenCounter=0;
		int tokenCounter=0;
		List<BaseToken> shareTokenList = new ArrayList<BaseToken>();

		while (tokenItr.hasNext()) {
					BaseToken token = (BaseToken) tokenItr.next();
					if (! ( token instanceof NewlineToken)) {
						int x=token.getBegin();
						int y=token.getEnd();	
						shareTokenList.add(token);
						shareToken sstoken = new shareToken(jCas,x,y);
					  	sstoken.setLineNumber(lineTokenCounter);
	                	sstoken.setShareBegin(x-lineTokenCounter);
	                	sstoken.setShareEnd(y-lineTokenCounter);
	                	sstoken.setTokenNumber(tokenCounter++);
	                	sstoken.addToIndexes();
						}
					
					if (token instanceof NewlineToken) {
						lineTokenCounter++;
					}
				}
		
		for (int i = 0; i < shareTokenList.size() - 1; i++) {
			BaseToken t = (BaseToken) shareTokenList.get(i);
			t.removeFromIndexes(jCas);
		}
   }
}
    