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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.ohnlp.medtagger.ml.type.i2b2Event;
import org.ohnlp.typesystem.type.structured.Document;

public class I2B2CollectionReader extends CollectionReader_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a
	 * directory containing input files.
	 */
	public static final String PARAM_INPUTDIR = "InputDirectory";

	/**
	 * Name of configuration parameter that contains the character encoding used
	 * by the input files. If not specified, the default system encoding will be
	 * used.
	 */
	public static final String PARAM_ENCODING = "Encoding";

	/**
	 * Name of optional configuration parameter that contains the language of
	 * the documents in the input directory. If specified this information will
	 * be added to the CAS.
	 */
	public static final String PARAM_LANGUAGE = "Language";

	/**
	 * Name of optional configuration parameter that indicates including the
	 * subdirectories (recursively) of the current input directory.
	 */
	public static final String PARAM_SUBDIR = "BrowseSubdirectories";
	public static final String PARAM_YEAR = "Year";

	private ArrayList<File> mFiles;
	private ArrayList<File> eFiles;// Event files

	private String mEncoding;

	private String mLanguage;

	private Boolean mRecursive;
    private String year;
	private int mCurrentIndex;

	/**
	 * @see CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		File directory = new File(
				((String) getConfigParameterValue(PARAM_INPUTDIR)).trim());
		mEncoding = (String) getConfigParameterValue(PARAM_ENCODING);
		mLanguage = (String) getConfigParameterValue(PARAM_LANGUAGE);
		mRecursive = (Boolean) getConfigParameterValue(PARAM_SUBDIR);
		year= (String) getConfigParameterValue(PARAM_YEAR);
		if (null == mRecursive) { // could be null if not set, it is optional
			mRecursive = Boolean.FALSE;
		}
		mCurrentIndex = 0;

		// if input directory does not exist or is not a directory, throw
		// exception
		if (!directory.exists() || !directory.isDirectory()) {
			throw new ResourceInitializationException(
					ResourceConfigurationException.DIRECTORY_NOT_FOUND,
					new Object[] { PARAM_INPUTDIR,
							this.getMetaData().getName(), directory.getPath() });
		}

		// get list of files in the specified directory, and subdirectories if
		// the
		// parameter PARAM_SUBDIR is set to True
		mFiles = new ArrayList<File>();
		eFiles = new ArrayList<File>();
		try {
			addFilesFromDir(directory,year);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method adds files in the directory passed in as a parameter to
	 * mFiles. If mRecursive is true, it will include all files in all
	 * subdirectories (recursively), as well.
	 * 
	 * @param dir
	 * @throws Exception
	 */
	private void addFilesFromDir(File dir, String year) throws Exception {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      if (!files[i].isDirectory() ) {
	  if (files[i].getAbsoluteFile().toString().endsWith(".txt")){
	      mFiles.add(files[i]);     
	     if(year.equals("2012")){
	    	 String i2b2EventFileName=files[i].getAbsoluteFile().toString().replace(".xml.txt",".xml");
	    	 i2b2EventFileName=i2b2EventFileName.replace(".txt",".xml");//for adjusting for i2b22010data
	    	 eFiles.add(new File(i2b2EventFileName));//Corresponding xml file
	     }
	     if(year.equals("2010")){
	    	 String i2b2EventFileName=files[i].getAbsoluteFile().toString().replace(".txt",".con");
	    	 eFiles.add(new File(i2b2EventFileName));
	     }
      } 
      }
      else if (mRecursive) {
        addFilesFromDir(files[i], year);
      }
    }
      }

	private Hashtable<String, String> get_event_attributes(String str) {

		Hashtable<String, String> ht = new Hashtable<String, String>();
		Matcher matcher = Pattern.compile("(\\w+)=\"((\\w|\\s)+)\"").matcher(
				str);
		while (matcher.find()) {
			ht.put(matcher.group(1), matcher.group(2));
		}
		return ht;
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
		File efile = (File) eFiles.get(mCurrentIndex);// event file
		mCurrentIndex = mCurrentIndex + 1;

		String text = FileUtils.file2String(file, mEncoding);
		// put document in CAS
		jcas.setDocumentText(text);

		// set language if it was explicitly specified as a configuration
		// parameter
		if (mLanguage != null) {
			((DocumentAnnotation) jcas.getDocumentAnnotationFs())
					.setLanguage(mLanguage);
		}
		if (efile.exists()) {
			ArrayList<i2b2Event> eventList = parse_event_file(
					FileUtils.file2String(efile, mEncoding), year, jcas);

			Iterator<i2b2Event> itr = eventList.iterator();
			while (itr.hasNext()) {
				i2b2Event f = (i2b2Event) itr.next();
				f.addToIndexes();
			}
		}

		// Also store location of source document in CAS. This information is
		// critical
		// if CAS Consumers will need to know where the original document
		// contents are located.
		// For example, the Semantic Search CAS Indexer writes this information
		// into the
		// search index that it creates, which allows applications that use the
		// search index to
		// locate the documents that satisfy their semantic queries.
		 Document document = new Document(jcas);
		   document.setFileLoc(file.getAbsoluteFile().toURL().toString());
		   document.addToIndexes();
	}

	private ArrayList<i2b2Event> parse_event_file(String str, String year, JCas jcas) {
		ArrayList<i2b2Event> eArr = new ArrayList<i2b2Event>();
		
		if(year.equals("2010")){
			String lineArr[] = str.split("\n");
			// c="afebrile" 55:12 55:12||t="problem"
			Pattern pattern = Pattern
				.compile("c=\"[^\"]+\"\\s([0-9]+)\\:([0-9]+)\\s([0-9]+)\\:([0-9]+)\\|\\|t=\"(.*)\"");
			for (int i = 0; i < lineArr.length; i++) {
			String line = lineArr[i];
			Matcher matcher = pattern.matcher(line.trim());
			//Event e = new Event(jcas);
		
			if(matcher.matches()){
			//	if(matcher.group(5).equals("problem")){
				i2b2Event e = new i2b2Event(jcas);
				//	e.setEventType("Disease_Disorder");
				e.setEventType(matcher.group(5));
				e.setEndLine(Integer.parseInt(matcher.group(3)));
			e.setBeginLine(Integer.parseInt(matcher.group(1)));
			e.setBeginLineToken(Integer.parseInt(matcher.group(2)));
			e.setEndLineToken(Integer.parseInt(matcher.group(4)));
			e.setBegin(-1);
			e.setEnd(-1);
			eArr.add(e);
		//}
			}
			}
		}
         if(year.equals("2012")){		
        	 String lineArr[] = str.split("\n");
        	 for (int i = 0; i < lineArr.length; i++) {
			String line = lineArr[i];
			if (line.startsWith("<EVENT")) {
				// System.out.println(line);
				Hashtable<String, String> eventAttrs = get_event_attributes(line);
				i2b2Event e = new i2b2Event(jcas);
				e.setEventType(eventAttrs.get("type"));
				e.setEndLine(-1);
				e.setBeginLine(-1);
				e.setEndLineToken(-1);
				e.setBeginLineToken(-1);
				// e.setBegin(0);
				// e.setBegin(10);
				try {
					e.setBegin(Integer.parseInt(eventAttrs.get("start")
							.replace("\"", "")) - 1);
					e.setEnd(Integer.parseInt(eventAttrs.get("end").replace(
							"\"", "")) - 1);
				} catch (Exception exp) {
					System.out.println(exp.getMessage());
				}
				eArr.add(e);
			}

		}
         }

		return eArr;
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
		return new Progress[] { new ProgressImpl(mCurrentIndex, mFiles.size(),
				Progress.ENTITIES) };
	}

	/**
	 * Gets the total number of documents that will be returned by this
	 * collection reader. This is not part of the general collection reader
	 * interface.
	 * 
	 * @return the number of documents in the collection
	 */
	public int getNumberOfDocuments() {
		return mFiles.size();
	}

}