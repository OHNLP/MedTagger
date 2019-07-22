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

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.ohnlp.typesystem.type.syntax.BaseToken;
import org.ohnlp.typesystem.type.syntax.NewlineToken;
import org.ohnlp.typesystem.type.syntax.WordToken;
import org.ohnlp.medtagger.ml.type.i2b2Token;

/*
 * in java if the substring is indexed as x,y
 * the corresponding UIMA span begin,end are x and y
 * and i2b2 span begin,end are x+1 and y+1 , as character positions begin with 1 
 * newlines are counted as one character in java,UIMA and i2b2
 */

public class I2B2Tokenizer extends JCasAnnotator_ImplBase {

    private static final int NOT_DEFINED = -1;
    JCas jcas;
    
    
    
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
            jcas = aJCas;
            JFSIndexRepository indexes = jcas.getJFSIndexRepository();
            Iterator<?> btItr = indexes.getAnnotationIndex(BaseToken.type)
    				.iterator();
    		String docText=jcas.getDocumentText();
    		ArrayList <BaseToken> btList=new ArrayList<BaseToken>();
    			while (btItr.hasNext()) {
    		   BaseToken bt= (BaseToken) btItr.next();
    		   btList.add(bt);				
    		}
          
	  int tokenCounter=0;
	 int lineTokenCounter=0;
 	  int newlineCounter=1;
	  int startToken=1;
	   docText+="\n";
		char[] charArray=docText.toCharArray(); 
		BaseToken cbt=(BaseToken) btList.get(0);
		cbt.removeFromIndexes();
		int cbtIndex=0;
    	
	  for(int charPos=1; charPos<charArray.length; charPos++){
		  char ch=charArray[charPos-1];
          while(btList.get(cbtIndex).getEnd()<charPos-1 && cbtIndex<btList.size()-1){
        	  cbt=btList.get(++cbtIndex);
        	  cbt.removeFromIndexes();
        	 }
		  if(charPos>1){
			  char pch=charArray[charPos-2];
			  if(Character.isWhitespace(ch) && !Character.isWhitespace(pch) ){
				  int x=startToken-1;
				  int y=charPos-1;
     
                	i2b2Token token = new i2b2Token(jcas,x , y);
                	token.setPartOfSpeech(cbt.getPartOfSpeech());
                	if(cbt instanceof WordToken){
                		WordToken lwt=(WordToken) cbt;
                		token.setCanonicalForm(lwt.getCanonicalForm());
                		token.setCapitalization(lwt.getCapitalization());
                	}
                	token.setLineNumber(newlineCounter);
                	token.setI2b2Begin(x+1);
                	token.setI2b2End(y+1);
                	token.setTokenNumber(tokenCounter++);
                	token.setLineTokenNumber(lineTokenCounter++);
                	token.addToIndexes();
                	
                	
                	 //System.out.print("making token"+" start="+startToken+" end="+charPos+ " lineNum="+(newlineCounter)+ " text=<"+input.substring(x,y)+">");
                	 startToken=NOT_DEFINED;
                	 
                        // System.out.print("->");
                    }else{
                	if(startToken==NOT_DEFINED){
                	    startToken=charPos;
                	}
                    }
                    if(ch=='\n'){
                	startToken=NOT_DEFINED;
                	NewlineToken nltoken = new NewlineToken(jcas,charPos-1 ,charPos );
                	nltoken.addToIndexes();
                	newlineCounter++;//new line positions
                	lineTokenCounter=0;
                    }
                   // System.out.println(ch+" "+charPos);
                }
             }
    }
      
}
