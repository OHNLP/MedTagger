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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import org.ohnlp.medtagger.type.DictContext;

public class DictCollectionReader extends CollectionReader_ImplBase {

	private BufferedReader br = null;

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return br.ready();
	}

	@Override
	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		br.close();
	}

	@Override
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		try {
			//example line: OTHER Laboratory tests|other laboratory tests|PROC|C0497099_T059|2
			String line = br.readLine();
			aCAS.getJCas().setDocumentText(line.substring(0, line.indexOf("|")));			
			DictContext source = new DictContext(aCAS.getJCas());
			source.setEntry(line.substring(line.indexOf("|")+1));
			source.addToIndexes();
		} catch (CASRuntimeException e) {
			e.printStackTrace();
		} catch (CASException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() throws ResourceInitializationException {
		try {
			br = new BufferedReader(new FileReader((String) getConfigParameterValue("InputFile")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
