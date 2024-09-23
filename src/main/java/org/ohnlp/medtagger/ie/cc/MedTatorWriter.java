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
package org.ohnlp.medtagger.ie.cc;


import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.structured.Document;

import org.ohnlp.typesystem.type.textspan.Sentence;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * 
 * Generate XML output format
 * @author Hongfang Liu
 *
 */
public class MedTatorWriter extends JCasAnnotator_ImplBase {

	public static final String PARAM_OUTPUTDIR = "OutputDir";

	private File mOutputDir;
 
	private int mDocNum;

    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;

    /**
	 * initialize
	 */
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
    
		mDocNum = 0;
		mOutputDir = new File((String) aContext.getConfigParameterValue(PARAM_OUTPUTDIR));
		if (!mOutputDir.exists()) {
			mOutputDir.mkdirs();
		}
		dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

	// print out match and concept mention
	public void printAnnotationsInline(JCas jcas) throws IOException {
		JFSIndexRepository indexes = jcas.getJFSIndexRepository();
	 	FSIterator<TOP> docIterator = indexes.getAllIndexedFS(Document.type);
	 	 File outFile = null;
		  File inFile = null;


		  if (docIterator.hasNext()) {
	    	Document docAnn = (Document) docIterator.next();
	 	    String fileLoc =docAnn.getFileLoc(); 
	      try {
	        inFile = new File(new URL(fileLoc).getPath());
	        String outFileName = inFile.getName();
	        outFile = new File(mOutputDir, outFileName+".xml");
	      } catch (MalformedURLException e1) {
	       }
	    }
	    if (outFile == null) {
	      outFile = new File(mOutputDir, "doc" + mDocNum++);
	    }
	    


		String toprint = "";
		int tagid = 0;
		// document text
		String doctext    = jcas.getDocumentText();

        org.w3c.dom.Document doc = db.newDocument();
		Element rootElement = doc.createElement("MedTagger");
		doc.appendChild(rootElement);
		Node cdata = doc.createCDATASection(doctext);
		Element textElement = (Element) doc.createElement("TEXT");
		textElement.appendChild(cdata);
		rootElement.appendChild(textElement);
		Element tagsElement = (Element) doc.createElement("TAGS");


		// get concept mention index
		FSIterator<? extends Annotation> cmIter = jcas.getAnnotationIndex(ConceptMention.type).iterator();

		while (cmIter.hasNext()) {
			ConceptMention cm = (ConceptMention) cmIter.next();
			Element tagElement = doc.createElement("CM");
			tagElement.setAttribute("spans", cm.getBegin()+"~"+cm.getEnd());
			tagElement.setAttribute("text", cm.getCoveredText());
			tagElement.setAttribute("id", "P"+tagid);
			tagElement.setAttribute("certainty", cm.getCertainty());
			tagElement.setAttribute("status", cm.getStatus());
			tagElement.setAttribute("experiencer", cm.getExperiencer());
			tagElement.setAttribute("semG", cm.getSemGroup());
			tagElement.setAttribute("section", cm.getSentence().getSegment().getId() );
			tagElement.setAttribute("normTarget", cm.getNormTarget());
			tagsElement.appendChild(tagElement);
			tagid++;
		}
		rootElement.appendChild(tagsElement);

	     try{
			 FileOutputStream output =
					 new FileOutputStream(outFile);
			writeXml(doc, output);
		} catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

	public static void writeXml(org.w3c.dom.Document doc,
								 OutputStream output)
			throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource((Node) doc);
		StreamResult result = new StreamResult(output);
		transformer.transform(source, result);
	}
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
            printAnnotationsInline(jCas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
