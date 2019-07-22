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

package org.ohnlp.medtagger.ml.crfsuite;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.uima.UIMAFramework;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.metadata.CpeDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

public class CRFTrainMain {

	public static void main(String[] args) throws Exception {
		// parse CPE descriptor in file specified on command line
		String usage = "Usage: To train a CRF model:\n"
				+ "java -cp <this jar file> [-csh crfsuitehome [-cpexml CPEFile] [-train trainData] [-model ModelFile]\n";
		if (args.length == 0 || args.length > 0
				&& ("-h".equals(args[0]) || "-help".equals(args[0]))) {
			System.out.println(usage);
			System.exit(0);
		}
		String csh = null;
		String cpexml = null;
		String trainFile = null;
		String modelFile = null;
		for (int i = 0; i < args.length; i++) {
			if("-csh".equals(args[i])){
				csh = args[i + 1];
				i++;
			}
				if ("-cpexml".equals(args[i])) {
			
				cpexml = args[i + 1];
				i++;
			} else if ("-train".equals(args[i])) {
				trainFile = args[i + 1];
				i++;
			} else if ("-model".equals(args[i])) {
				modelFile = args[i + 1];
				i++;
			}
		}

		CpeDescription cpeDesc;
		try {
			cpeDesc = UIMAFramework.getXMLParser().parseCpeDescription(
					new XMLInputSource(cpexml));
			CollectionProcessingEngine mCPE;
			mCPE = UIMAFramework.produceCollectionProcessingEngine(cpeDesc);
			mCPE.process();
			File tmpFile=new File(trainFile);
			tmpFile.delete();
			
			CRFSuiteWrapper csw=new CRFSuiteWrapper(csh);
			
			// code to make sure CPE has finished
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)+":"+"waiting for CPE to finish .... ");
			
			Thread.sleep(60000);
			tmpFile=new File(trainFile);
			long size=tmpFile.length();
			boolean change=true;
			while(change){
				Thread.sleep(300000);
				date = new Date();
				System.out.println(dateFormat.format(date)+":"+"still waiting for CPE to finish .... ");
				tmpFile=new File(trainFile);
				long size1=tmpFile.length();
				if(size1==size){
					change=false;
				}
				else{
					change=true;
				}
				size=size1;
			}	
			System.out.println("Start building models");
			csw.trainClassifier(modelFile, trainFile, new String[]{});				
		} catch (InvalidXMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
