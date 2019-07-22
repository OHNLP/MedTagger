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
package org.ohnlp.medtagger.dict;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.ohnlp.typesystem.type.syntax.BaseToken;
import org.ohnlp.typesystem.type.syntax.NumToken;
import org.ohnlp.typesystem.type.syntax.PunctuationToken;
import org.ohnlp.typesystem.type.syntax.WordToken;
import org.ohnlp.medtagger.type.DictContext;

public class DictConsumer extends CasConsumer_ImplBase {

	private PrintWriter pwr = null;
	private HashSet<String> processed = new HashSet<String>();
	
	@Override
	public void processCas(CAS aCAS) throws ResourceProcessException {
		try {
			JFSIndexRepository indexes = aCAS.getJCas().getJFSIndexRepository();
			Iterator<?> itr = indexes.getAnnotationIndex(BaseToken.type).iterator();
			String ret="";
			while (itr.hasNext()){
				BaseToken token = (BaseToken) itr.next();
				if(token instanceof WordToken){
					if(((WordToken)token).getCanonicalForm()!=null)
						ret+=((WordToken)token).getCanonicalForm()+"\t";
				}
				else if(token instanceof NumToken){
					ret+=((NumToken)token).getCoveredText()+"\t";
				} 
				else if(token instanceof PunctuationToken){
					String tktext=token.getCoveredText();
					if(tktext.equals(">") ||tktext.equals("<") || tktext.equals("="))
					ret+=tktext+"\t";
				} 
			}
			
			ret=ret.trim()+"|";
			if(ret.equals("|")) return;
			itr = indexes.getAnnotationIndex(DictContext.type).iterator();
			while (itr.hasNext()){
				DictContext source = ((DictContext)itr.next());
				ret+=source.getEntry();
			}
			
			//capturing multiple expansions where relevant
			if(processed.contains(ret)) return;
			else processed.add(ret);
			
			pwr.println(ret);
			pwr.flush();
		} catch (CASException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() throws ResourceInitializationException {
		try {
			pwr = new PrintWriter((String) getConfigParameterValue("OutputFile"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	Logger log = Logger.getLogger(getClass().getName());
	@Override
	public void destroy() {
		super.destroy();
		pwr.close();
		log.info("Finshed printed in the dictionary");
	}

}
