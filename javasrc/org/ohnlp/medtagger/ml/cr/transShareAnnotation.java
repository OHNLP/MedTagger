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

import java.io.*;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.AttributesImpl;

import org.ohnlp.medtagger.ml.type.shareAnnotation;
import org.ohnlp.medtagger.ml.type.shareSlot;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import java.util.Stack;

public class transShareAnnotation extends DefaultHandler {
	private JCas mjcas = null;
    private String mentionValue="";
    private String mentionClass="";
	private Stack <String> starts=new Stack <String>();
	private Stack <String> ends=new Stack <String> ();
	
	public transShareAnnotation(JCas jcas) {
		mjcas=jcas;
	};

	public transShareAnnotation(String str, JCas jcas) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		mjcas = jcas;
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new ByteArrayInputStream(str.getBytes()), this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public transShareAnnotation(File xmlfile, JCas jcas) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		mjcas=jcas;
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(xmlfile, this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ===========================================================
	// SAX DocumentHandler methods
	// ===========================================================

	public void startElement(String namespaceURI, String lName, // local name
			String qName, // qualified name
			Attributes attrs) throws SAXException {
		String tag = qName;
		AttributesImpl attrsimpl = new AttributesImpl(attrs);
		if(tag.equals("annotation")){
		 if(! starts.isEmpty()){
			int i=0; 
			int b=Integer.parseInt(starts.get(0));
			int e=Integer.parseInt(ends.peek());
			shareAnnotation sa=new shareAnnotation(mjcas); 
		    sa.setAnnotValue(mentionValue);
		    sa.setAnnotType(mentionClass);
		    sa.setAnnotSlots(new FSArray(mjcas,starts.size()));
			while(!starts.isEmpty()){			  
				  shareSlot  ss= new shareSlot(mjcas);
			      ss.setBegin(Integer.parseInt(starts.pop()));
			      ss.setEnd(Integer.parseInt(ends.pop()));
			      ss.setSlotClass(mentionClass);
			      ss.setSlotConceptValue(mentionValue);		     
			      ss.setAnnotBegin(b);
			      ss.setAnnotEnd(e);
			      ss.addToIndexes(mjcas);
			      sa.setAnnotSlots(i++,ss);
			}
			sa.addToIndexes(mjcas);
		 }
		}
		if (tag.equals("span")){
			 starts.add(attrsimpl.getValue("start"));
			 ends.add(attrsimpl.getValue("end"));			
		}
		if(tag.equals("stringSlotMentionValue")){
			 mentionValue=attrsimpl.getValue("value");
		}
		if(tag.equals("mentionClass")){
			 mentionClass=attrsimpl.getValue("id");
		}
	}

	public void endElement(String namespaceURI, String sName, // simple name
			String qName // qualified name
	) throws SAXException {
		
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {		
	}
}
