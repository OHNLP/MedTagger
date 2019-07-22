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

import java.io.File;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;


import org.ohnlp.medtagger.ml.util.FeatureGenerator;
import org.ohnlp.medtagger.type.ConceptMention;

/**
 * Focus on sentence-level sequential labeling assignment
 * @author Hongfang Liu
 *
 */
public class CRFAnnotator extends JCasAnnotator_ImplBase {

	public void process(JCas jCas) throws AnalysisEngineProcessException {
		try {
			List<ConceptMention> pCMs=fg.applyCRF(jCas, leftWindow, rightWindow);
			for(int i=0; i<pCMs.size(); i++) {
			   ConceptMention e=pCMs.get(i);
			   e.setDetectionMethod("CRF"+leftWindow+"_"+rightWindow);
			   e.addToIndexes();
		}
		}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}	
	}

	public static final String PARAM_CRFHome="CRFSuiteHome";
	public static final String PARAM_RW = "RightWindow";
    public static final String PARAM_LW = "LeftWindow";
    public static final String PARAM_MODEL = "CRFModel";
    private int leftWindow=2;
    private int rightWindow=2;
    private FeatureGenerator fg=null;
   
	
    @Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
			leftWindow = ((Integer) aContext.getConfigParameterValue(PARAM_LW)).intValue();
			rightWindow = ((Integer) aContext.getConfigParameterValue(PARAM_RW)).intValue();
			File modelFile= new File((String) aContext.getConfigParameterValue(PARAM_MODEL));
			String crfHome= (String) aContext.getConfigParameterValue(PARAM_CRFHome);
			
			try {
				fg=new FeatureGenerator(modelFile,crfHome);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
