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
package org.ohnlp.medtagger.cr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.ohnlp.typesystem.type.structured.Document;

/**
 * A simple collection reader that reads documents from a directory in the filesystem. It can be
 * configured with the following parameters:
 * <ul>
 * <li><code>InputDirectory</code> - path to directory containing files</li>
 * </ul>
 */
public class FileSystemReader extends CollectionReader_ImplBase {
    /**
     * Name of configuration parameter that must be set to the path of a directory containing input
     * files.
     */
    public static final String PARAM_INPUTDIR = "InputDirectory";

    /**
     * Name of configuration parameter that contains the character encoding used by the input files.
     * If not specified, the default system encoding will be used.
     */

    public static final String PARAM_SUBDIR = "BrowseSubdirectories";

    private ArrayList<File> mFiles;

    private String mEncoding;

    private String mLanguage;

    private Boolean mRecursive;

    private int mCurrentIndex;

    /**
     * @see CollectionReader_ImplBase#initialize()
     */
    public void initialize() throws ResourceInitializationException {
        File directory = new File(((String) getConfigParameterValue(PARAM_INPUTDIR)).trim());
        mRecursive = (Boolean) getConfigParameterValue(PARAM_SUBDIR);
        if (null == mRecursive) { // could be null if not set, it is optional
            mRecursive = Boolean.FALSE;
        }
        mCurrentIndex = 0;

        // if input directory does not exist or is not a directory, throw exception
        if (!directory.exists() || !directory.isDirectory()) {
            throw new ResourceInitializationException(ResourceConfigurationException.DIRECTORY_NOT_FOUND,
                    new Object[]{PARAM_INPUTDIR, this.getMetaData().getName(), directory.getPath()});
        }

        // get list of files in the specified directory, and subdirectories if the
        // parameter PARAM_SUBDIR is set to True
        mFiles = new ArrayList<File>();
        addFilesFromDir(directory);

    }

    /**
     * This method adds files in the directory passed in as a parameter to mFiles.
     * If mRecursive is true, it will include all files in all
     * subdirectories (recursively), as well.
     *
     * @param dir
     */
    private void addFilesFromDir(File dir) {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                mFiles.add(files[i]);
            } else if (mRecursive) {
                addFilesFromDir(files[i]);
            }
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
        File file = (File) mFiles.get(mCurrentIndex++);

        String text = FileUtils.file2String(file, mEncoding);

        jcas.setDocumentText(text);

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
        return new Progress[]{new ProgressImpl(mCurrentIndex, mFiles.size(), Progress.ENTITIES)};
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
