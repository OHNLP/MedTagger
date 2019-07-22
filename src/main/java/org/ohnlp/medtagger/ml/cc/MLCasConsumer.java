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

package org.ohnlp.medtagger.ml.cc;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
//import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.XMLSerializer;
import org.ohnlp.medtagger.ml.util.FeatureGenerator;
import org.ohnlp.typesystem.type.structured.Document;
import org.xml.sax.SAXException;



/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files will be written</li>
 * </ul>
 */
public class MLCasConsumer extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
  public static final String PARAM_OUTPUTDIR = "OutputDirectory";
  public static final String PARAM_RW = "RightWindow";
  public static final String PARAM_LW = "LeftWindow";
  public static final String PARAM_TYPE = "TaskType";
 // public static final String PARAM_MIMICVECTOR = "MIMICVector";
 // public static final String PARAM_EDTVECTOR = "EDTVector";
  

  private File mOutputDir;

  private int mDocNum;
  
  private int leftWindow=2;
  private int rightWindow=2;
  //private String mimicVector="";
  //private String EDTVector="";
  private String taskType="share";
  private FeatureGenerator fg=null;
  
   
  public void initialize() throws ResourceInitializationException {
    mDocNum = 0;
     mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
     rightWindow = ((Integer) getConfigParameterValue(PARAM_RW)).intValue();
     leftWindow=  ((Integer) getConfigParameterValue(PARAM_LW)).intValue();
     taskType = (String) getConfigParameterValue(PARAM_TYPE);
     taskType = (String) getConfigParameterValue(PARAM_TYPE);
      try {
		fg=new FeatureGenerator();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if (!mOutputDir.exists()) {
      mOutputDir.mkdirs();
    }
  }
  
  
  /**
   * Processes the CAS which was populated by the TextAnalysisEngines. <br>
   * In this case, the CAS is converted to XMI and written into the output file .
   * 
   * @param aCAS
   *          a CAS which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(CAS)
   */
public void processCas(CAS aCAS) throws ResourceProcessException {
    String modelFileName = null;

    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    // retreive the filename of the input file from the CAS
    JFSIndexRepository indexes = jcas.getJFSIndexRepository();
 	FSIterator<TOP> docIterator = indexes.getAllIndexedFS(Document.type);
 		  if (docIterator.hasNext()) {
    	Document docAnn = (Document) docIterator.next();
 	    String fileLoc =docAnn.getFileLoc(); 
    File outFile = null;
    File outFeatureFile = null;
          File inFile;
      try {
        inFile = new File(new URL(fileLoc).getPath());
        String outFileName = inFile.getName();
        outFileName += ".xmi";
        outFile = new File(mOutputDir, outFileName);
        outFeatureFile = new File(mOutputDir, "features.crfsuite");
        modelFileName = mOutputDir.getAbsolutePath() + "/" + inFile.getName() + ".ecore";
      } catch (MalformedURLException e1) {
        // invalid URL, use default processing below
      }
    
    if (outFile == null) {
      outFile = new File(mOutputDir, "doc" + mDocNum++ + ".xmi");     
    }
    // serialize XCAS and write to output file
    try {
   //   writeXmi(jcas.getCas(), outFile, modelFileName);
        fg.generateTrainFeatureLabel(jcas, taskType,outFile.getName(), outFeatureFile, leftWindow, rightWindow); 
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    } catch (SAXException e) {
      throw new ResourceProcessException(e);
    } catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
 		  }
  }

  
		
	
private void writeXmi(CAS aCas, File name, String modelFileName) throws IOException, SAXException {
    FileOutputStream out = null;

    try {
      // write XMI
      out = new FileOutputStream(name);
      XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
      XMLSerializer xmlSer = new XMLSerializer(out, false);
      
      ser.serialize(aCas, xmlSer.getContentHandler());
      
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}

