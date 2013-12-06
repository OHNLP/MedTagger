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

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.ohnlp.typesystem.type.syntax.BaseToken;
import org.ohnlp.typesystem.type.syntax.NewlineToken;
import org.ohnlp.typesystem.type.syntax.ContractionToken;
import org.ohnlp.typesystem.type.syntax.PunctuationToken;
import org.ohnlp.typesystem.type.syntax.NumToken;
import org.ohnlp.typesystem.type.syntax.WordToken;
import org.ohnlp.typesystem.type.textspan.Sentence;

public class Open2OHTokenizer  extends JCasAnnotator_ImplBase {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	public static final byte CAPS_UNKNOWN = 0;
	public static final byte CAPS_NONE = 1;
	public static final byte CAPS_MIXED = 2;
	public static final byte CAPS_FIRST_ONLY = 3;
	public static final byte CAPS_ALL = 4;

	final char crChar = '\r';
	final char nlChar = '\n';
	final char aposChar = '\'';

	public void process(JCas jCas) throws AnalysisEngineProcessException {
		JFSIndexRepository indexes = jCas.getJFSIndexRepository();
		
		Iterator<?> btItr = indexes.getAnnotationIndex(BaseToken.type).iterator();
		Iterator<?> sentItr = indexes.getAnnotationIndex(Sentence.type).iterator();
		String docText=jCas.getDocumentText();
		char[] charArray=docText.toCharArray(); 
		ArrayList<BaseToken> btList=new ArrayList<BaseToken>();
		ArrayList<Sentence> sentList=new ArrayList<Sentence>();
		ArrayList<BaseToken> newbtList=new ArrayList<BaseToken>();
		while (btItr.hasNext()) {
		   BaseToken bt= (BaseToken) btItr.next();
		   btList.add(bt);				
		}
		while (sentItr.hasNext()) {
			   Sentence sent= (Sentence) sentItr.next();
			   sentList.add(sent);				
			}
		int btsize=btList.size();
		BaseToken pbt=null;
		if(sentList.size()>0){
		Sentence csent=(Sentence) sentList.get(0);
		int sentIndex=1;
		for(int i=0; i<btsize; i++){
			 BaseToken bt=(BaseToken) btList.get(i);
			 if(bt.getBegin()>csent.getEnd()) csent=(Sentence)sentList.get(sentIndex++);
			 if(pbt!=null && pbt.getEnd()+1==bt.getBegin()){
			   char ch=charArray[pbt.getEnd()];	 
			    if(ch==crChar||ch==nlChar){ 
				 NewlineToken nlToken=new NewlineToken(jCas, pbt.getEnd(), pbt.getEnd()+1);
				 newbtList.add(nlToken);
			 // correcting sentence detection issue (clinical text may not use the period as the end of sentences
				 if(csent.getEnd() > pbt.getEnd() && csent.getBegin() < pbt.getEnd()){
				   csent.removeFromIndexes();
				   Sentence nsent=new Sentence(jCas,csent.getBegin(),pbt.getEnd());
				   nsent.setSegment(csent.getSegment());
				   nsent.addToIndexes();
				   Sentence nsent1=new Sentence(jCas,pbt.getEnd(),csent.getEnd());
				   nsent1.setSegment(csent.getSegment());
				   csent=nsent1;
				   csent.addToIndexes();
			   }
			   }
			  }
			  if(bt.getEnd()-bt.getBegin()==1){
				 char ch=charArray[bt.getBegin()];
				 if(!Character.isLetterOrDigit(ch)){
					 PunctuationToken pToken=new PunctuationToken(jCas, bt.getBegin(), bt.getEnd());
					 pToken.setPartOfSpeech(bt.getPartOfSpeech());
					 newbtList.add(pToken);
				 }
				 else if (Character.isDigit(ch)){
					 NumToken nToken=new NumToken(jCas, bt.getBegin(), bt.getEnd());
					 nToken.setPartOfSpeech(bt.getPartOfSpeech());
					 newbtList.add(nToken);			 
				}
				 else{
					 WordToken wToken=new WordToken(jCas, bt.getBegin(), bt.getEnd());
					 wToken.setPartOfSpeech(bt.getPartOfSpeech());
					 wToken.setCapitalization(getCapitalization(bt.getCoveredText()));
					 newbtList.add(wToken);		 		 
				 }
			  }
			 
			 else if(charArray[bt.getBegin()]==aposChar && bt.getEnd()-bt.getBegin()>1){
				ContractionToken cToken=new ContractionToken(jCas,bt.getBegin(), bt.getEnd());
			    cToken.setPartOfSpeech(bt.getPartOfSpeech());
			    newbtList.add(cToken);
				}
			 else if(bt.getPartOfSpeech().equals("CD")){
				 NumToken nToken=new NumToken(jCas, bt.getBegin(), bt.getEnd());
				 nToken.setPartOfSpeech(bt.getPartOfSpeech());
				 newbtList.add(nToken);				
			 }
			 else{
				 String tokenText=bt.getCoveredText()+"E";
				 String[] splits=tokenText.split("[\\W]");
				 if(splits.length==1){
				 WordToken wToken=new WordToken(jCas, bt.getBegin(), bt.getEnd());
				 wToken.setPartOfSpeech(bt.getPartOfSpeech());
				 wToken.setCapitalization(getCapitalization(bt.getCoveredText()));
				 newbtList.add(wToken);
				 }
				 else{
					 int pos=bt.getBegin();
					 for(int j=0; j<splits.length; j++){
						 if(j>0){
							 PunctuationToken pToken=new PunctuationToken(jCas, pos, pos+1);
							 pToken.setPartOfSpeech(pToken.getCoveredText());
							 newbtList.add(pToken);
				             pos+=1;
						 }
						 if(j==splits.length-1){
							 if(splits[j].equals("E")) continue;
							 splits[j]=splits[j].substring(0,splits[j].length()-1);
						 }
						 if(splits[j].length()!=0) {
							 WordToken wToken=new WordToken(jCas,pos,pos+splits[j].length()); 
							 wToken.setPartOfSpeech(bt.getPartOfSpeech());
							 wToken.setCapitalization(getCapitalization(splits[j]));
							 newbtList.add(wToken);
							 pos+=splits[j].length();	
						 }
					 }
					 
				 }
			 } 
			 pbt=bt;
             bt.removeFromIndexes();
		}
		
		for(int i=0; i<newbtList.size(); i++){
			BaseToken bt=(BaseToken) newbtList.get(i);
			bt.setTokenNumber(i);
			bt.addToIndexes();
		}
		}
	}
			 

	/**
	 * Applies capitalization rules to the given token. This should normally
	 * only be used for tokens containing strictly text, but mixtures of
	 * letters, numbers, and symbols are allowed too.
	 * 
	 * @param token
	 * @param tokenText
	 */
 public static int getCapitalization(String tokenText) {
		// true = upper case, false = lower case
		boolean[] uppercaseMask = new boolean[tokenText.length()];
		boolean isAllUppercase = true;
		boolean isAllLowercase = true;
		for (int i = 0; i < tokenText.length(); i++) {
			char currentChar = tokenText.charAt(i);
			uppercaseMask[i] = Character.isUpperCase(currentChar);
			if (uppercaseMask[i] == false)
				isAllUppercase = false;
			else
				isAllLowercase = false;
		}

		if (isAllLowercase) {
			return CAPS_NONE;
		} else if (isAllUppercase) {
			return CAPS_ALL;
		} else if (uppercaseMask[0] == true) {
			if (uppercaseMask.length == 1) {
				return CAPS_FIRST_ONLY;
			}
			boolean isRestLowercase = true;
			for (int i = 1; i < uppercaseMask.length; i++) {
				if (uppercaseMask[i] == true)
					isRestLowercase = false;
			}
			if (isRestLowercase) {
				return CAPS_FIRST_ONLY;
			} else {
				return CAPS_MIXED;
			}
		}
		else {
			return CAPS_MIXED;
		}
 }
 }

