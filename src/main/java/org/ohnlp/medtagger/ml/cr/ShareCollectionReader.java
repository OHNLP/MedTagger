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

package org.ohnlp.medtagger.ml.cr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
//import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.ohnlp.typesystem.type.structured.Document;




public class ShareCollectionReader extends CollectionReader_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory containing input
   * files.
   */
  public static final String PARAM_INPUTDIR = "InputDirectory";

  /**
   * Name of configuration parameter that contains the character encoding used by the input files.
   * If not specified, the default system encoding will be used.
   */
  public static final String PARAM_ENCODING = "Encoding";

  /**
   * Name of optional configuration parameter that contains the language of the documents in the
   * input directory. If specified this information will be added to the CAS.
   */
  public static final String PARAM_LANGUAGE = "Language";

  /**
   * Name of optional configuration parameter that indicates including
   * the subdirectories (recursively) of the current input directory.
   */
  public static final String PARAM_SUBDIR = "BrowseSubdirectories";
  
  private ArrayList<File> mFiles;
  private HashMap<String, File> annotFiles;//Event files
  private HashMap<String, File> abbrFiles;//Abbreviation Files

  private String mEncoding;

  private String mLanguage;
  
  private Boolean mRecursive;

  private int mCurrentIndex;

  /**
   * @see CollectionReader_ImplBase#initialize()
   */
  public void initialize() throws ResourceInitializationException {
    File directory = new File(((String) getConfigParameterValue(PARAM_INPUTDIR)).trim());
    mEncoding  = (String) getConfigParameterValue(PARAM_ENCODING);
    mLanguage  = (String) getConfigParameterValue(PARAM_LANGUAGE);
    mRecursive = (Boolean) getConfigParameterValue(PARAM_SUBDIR);
    if (null == mRecursive) { // could be null if not set, it is optional
      mRecursive = Boolean.FALSE;
    }
    mCurrentIndex = 0;

    // if input directory does not exist or is not a directory, throw exception
    if (!directory.exists() || !directory.isDirectory()) {
      throw new ResourceInitializationException(ResourceConfigurationException.DIRECTORY_NOT_FOUND,
              new Object[] { PARAM_INPUTDIR, this.getMetaData().getName(), directory.getPath() });
    }

    // get list of files in the specified directory, and subdirectories if the
    // parameter PARAM_SUBDIR is set to True
    mFiles = new ArrayList<File>();
    abbrFiles = new HashMap<String, File>();
    annotFiles = new HashMap<String, File>();
    try {
	addFilesFromDir(directory);
    } catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
  }
  
  /**
   * This method adds files in the directory passed in as a parameter to mFiles.
   * If mRecursive is true, it will include all files in all
   * subdirectories (recursively), as well. 
   * 
   * @param dir
 * @throws Exception 
   */
  private void addFilesFromDir(File dir) throws Exception {
    File textdir=new File(dir.getAbsoluteFile().toString()+"/ALLREPORTS");
    File annotdir=new File(dir.getAbsoluteFile().toString()+"/ALLSAVED");
    File abbrdir=new File(dir.getAbsoluteFile().toString()+"/ALLABBR");
    File[] files = textdir.listFiles();
    for (int i = 0; i < files.length; i++) 
    	mFiles.add(files[i]);
    if(annotdir.exists()){
    files=annotdir.listFiles();
    for (int i = 0; i < files.length; i++)   	
    	annotFiles.put(files[i].getName().replace(".knowtator.xml",""), files[i]);
    }
    if(abbrdir.exists()){
    files=abbrdir.listFiles();
    for (int i = 0; i < files.length; i++) 
     	abbrFiles.put(files[i].getName().replace(".knowtator.xml",""), files[i]);
    }
  }

  
  /**
   * @see org.apache.uima.collection.CollectionReader#hasNext()
   */
  public boolean hasNext() {
    return mCurrentIndex < mFiles.size();
  }

  /**
   * @see org.apache.uima.collection.CollectionReader#getNext(CAS)
   */
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }

    // open input stream to file
    
   File file = (File) mFiles.get(mCurrentIndex);
   File annotfile=null;
   File abbrfile=null;
   if(annotFiles.size()>0) {
	   annotfile = (File) annotFiles.get(file.getName());//event file
   }
   if(abbrFiles.size()>0){
     abbrfile = (File) abbrFiles.get(file.getName());//predicted event file
   }
     mCurrentIndex=mCurrentIndex+1;

       String text = FileUtils.file2String(file, mEncoding);  
         // put document in CAS
       jcas.setDocumentText(text);

    // set language if it was explicitly specified as a configuration parameter
    if (mLanguage != null) {
      ((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage(mLanguage);
    }
    if(annotfile!=null && annotfile.exists()){
	new transShareAnnotation(FileUtils.file2String(annotfile, mEncoding),jcas);
    }
    if(abbrfile!=null && abbrfile.exists()){
    	new transShareAnnotation(FileUtils.file2String(abbrfile, mEncoding),jcas);
        }	
    
    
    // Also store location of source document in CAS. This information is critical
    // if CAS Consumers will need to know where the original document contents are located.
    // For example, the Semantic Search CAS Indexer writes this information into the
    // search index that it creates, which allows applications that use the search index to
    // locate the documents that satisfy their semantic queries.
	 Document document = new Document(jcas);
	   document.setFileLoc(file.getAbsoluteFile().toURL().toString());
	   document.addToIndexes();
    
  }
  
  
  /**
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
   */
	      
  
	      public void close() throws IOException {
  }

  /**
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
   */
  public Progress[] getProgress() {
    return new Progress[] { 
    		new ProgressImpl(mCurrentIndex, mFiles.size(), Progress.ENTITIES) };
  }

  /**
   * Gets the total number of documents that will be returned by this collection reader. This is not
   * part of the general collection reader interface.
   * 
   * @return the number of documents in the collection
   */
  public int getNumberOfDocuments() {
    return mFiles.size();
  }
  

}