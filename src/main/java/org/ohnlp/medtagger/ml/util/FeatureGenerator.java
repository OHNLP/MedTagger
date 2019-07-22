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

package org.ohnlp.medtagger.ml.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.ohnlp.medtagger.ml.type.i2b2Event;
import org.ohnlp.medtagger.ml.type.i2b2Token;
import org.ohnlp.medtagger.ml.type.shareSlot;
import org.ohnlp.medtagger.ml.type.shareToken;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.syntax.BaseToken;
import org.ohnlp.typesystem.type.syntax.NewlineToken;
import org.ohnlp.typesystem.type.syntax.WordToken;
import org.ohnlp.typesystem.type.textspan.Segment;
import org.ohnlp.typesystem.type.textspan.Sentence;
import org.ohnlp.medtagger.ml.crfsuite.CRFSuiteWrapper;

public class FeatureGenerator {

	CRFSuiteWrapper csw=null; 
	File modelFile=null;

	public FeatureGenerator() throws Exception{    
	}
	public FeatureGenerator(File modelfile, String csh) throws Exception{    
		csw=new CRFSuiteWrapper(csh);
		modelFile=modelfile;
	}

	@SuppressWarnings("rawtypes")
	public void generateTrainFeatureLabel(JCas aJCas, String type, String inFile, File name,  int leftWindow, int rightWindow) throws Exception {
		FileOutputStream out = null;

		try {
			FSIterator it = aJCas.getAnnotationIndex(DocumentAnnotation.type).iterator();
			AnnotationIndex segIndex = aJCas.getAnnotationIndex(Segment.type);
			AnnotationIndex senIndex = aJCas.getAnnotationIndex(Sentence.type);
			AnnotationIndex tokenIndex = aJCas.getAnnotationIndex(BaseToken.type);
			AnnotationIndex annotIndex = null;
			if(type.equals("i2b2"))
				annotIndex = aJCas.getAnnotationIndex(i2b2Event.type);
			if(type.equals("share")) 
				annotIndex = aJCas.getAnnotationIndex(shareSlot.type);
			AnnotationIndex cmIndex = aJCas.getAnnotationIndex(ConceptMention.type);
			DocumentAnnotation docInfo;
			if(it.hasNext()){
				docInfo = (DocumentAnnotation) it.next();
			}else return;

			// write XMI

			out = new FileOutputStream(name, true);
			final PrintStream printStream = new PrintStream(out); 
			FSIterator segIter= segIndex.iterator();
			FSIterator annotIter = annotIndex.iterator();
			List annotList=new ArrayList();
			while (annotIter.hasNext())    
				annotList.add(annotIter.next());             

			while(segIter.hasNext()){
				Segment seg=(Segment) segIter.next();
				FSIterator senIter= senIndex.subiterator(seg);

				while (senIter.hasNext()) {
					Sentence sen=(Sentence) senIter.next();
					FSIterator baseTokenIter = tokenIndex.subiterator(sen);
					FSIterator cmIter = cmIndex.subiterator(sen);
					List<BaseToken> baseTokenList = new ArrayList<BaseToken>();
					List<ConceptMention> cmList = new ArrayList<ConceptMention>();

					while (baseTokenIter.hasNext()) 
						baseTokenList.add((BaseToken) baseTokenIter.next());

					while (cmIter.hasNext()) 
						cmList.add((ConceptMention) cmIter.next());


					for(int li=0; li < baseTokenList.size(); li++){
						BaseToken bt= (BaseToken) baseTokenList.get(li);
						if(bt instanceof NewlineToken) continue;
						String ctext=bt.getCoveredText();
						if(ctext.equals(":")) ctext="PUNC_COLON";
						if(ctext.equals("\\")) ctext="PUNC_SLASH";
						printStream.print(getAnnotString(bt,type, annotList));
						printStream.print("\t"+ctext);
						printStream.print("\tsectionHeader="+seg.getId());
						printStream.print(getCMString(bt, cmList));

						for(int j=li-leftWindow; j<=li+rightWindow; j++ ){
							int ws=j-li;
							if(j>=0 && j< baseTokenList.size()){
								printStream.print(getTokenString((BaseToken) baseTokenList.get(j), ws));          
							}
							else if(j<0) printStream.print("\ttok["+ws+"]SSSSS");
							else printStream.print("\ttok["+ws+"]EEEEE");
						}
						//printStream.print("#"+inFile);
						printStream.print("\n");
					}
				}
				printStream.print("\n");
			}

			printStream.close();
		}

		finally {  
			if (out != null) {
				out.close();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List<ConceptMention> applyCRF(JCas aJCas, int leftWindow, int rightWindow) throws Exception {
		FileOutputStream out = null;
		File featureFile = File.createTempFile("features", ".crfsuite");
		// System.out.println(featureFile.toString()); 
		featureFile.deleteOnExit();
		HashMap<Integer,BaseToken> tokenIndexes=new HashMap<Integer,BaseToken>();
		HashMap<Integer,Sentence> sentIndexes=new HashMap<Integer,Sentence>();
		int featureIndex=0;
		int tagSize=0;
		try {
			FSIterator it = aJCas.getAnnotationIndex(DocumentAnnotation.type).iterator();
			AnnotationIndex senIndex = aJCas.getAnnotationIndex(Sentence.type);
			AnnotationIndex tokenIndex = aJCas.getAnnotationIndex(BaseToken.type);
			AnnotationIndex cmIndex = aJCas.getAnnotationIndex(ConceptMention.type);

			DocumentAnnotation docInfo;
			if(it.hasNext()){
				docInfo = (DocumentAnnotation) it.next();
			}else {
				return null;
			}
			out = new FileOutputStream(featureFile);
			final PrintStream printStream = new PrintStream(out); 
			FSIterator senIter= senIndex.subiterator(docInfo);
			while (senIter.hasNext()) {
				Sentence sen=(Sentence) senIter.next();
				if(sen.getCoveredText().equals("")) continue;
				FSIterator baseTokenIter = tokenIndex.subiterator(sen);
				FSIterator cmIter = cmIndex.subiterator(sen);
				List<BaseToken> baseTokenList = new ArrayList<BaseToken>();
				List<ConceptMention> cmList = new ArrayList<ConceptMention>();

				while (baseTokenIter.hasNext()) 
					baseTokenList.add((BaseToken) baseTokenIter.next());
				while (cmIter.hasNext()) 
					cmList.add((ConceptMention) cmIter.next());

				for(int li=0; li < baseTokenList.size(); li++){
					BaseToken bt= (BaseToken) baseTokenList.get(li);
					if(bt instanceof NewlineToken) continue;
					if(bt.getCoveredText().length()==0) continue;
					printStream.print("U");
					tagSize++;
					tokenIndexes.put(featureIndex, bt);
					sentIndexes.put(featureIndex, sen);
					featureIndex++;

					String ctext=bt.getCoveredText();
					if(ctext.equals(":")) ctext="PUNC_COLON";
					if(ctext.equals("\\")) ctext="PUNC_SLASH";
					printStream.print("\ttok[0]="+ctext);
					printStream.print(getCMString(bt, cmList));

					for(int j=li-leftWindow; j<=li+rightWindow; j++ ){
						int ws=j-li;
						if(j>=0 && j< baseTokenList.size()){
							printStream.print(getTokenString((BaseToken) baseTokenList.get(j), ws));          
						}
						else if(j<0) printStream.print("\ttok["+ws+"]SSSSS");
						else printStream.print("\ttok["+ws+"]EEEEE");
					}
					printStream.print("\n");
				}
				printStream.print("\n");
				//      tokenIndexes.put(featureIndex, null);
				//    sentIndexes.put(featureIndex, null);                    
				// featureIndex++;
			}

			printStream.close();
		}
		finally {  
			if (out != null) {
				out.close();
			}
		}     
		List<ConceptMention> pCMs=new ArrayList<ConceptMention>();
		//  System.out.println(tokenIndexes.size()+" "+tagSize);
		List<String> result=csw.classifyFeatures(featureFile, modelFile, tagSize); 
		boolean inE=false;
		ConceptMention cm=null;
		int lastEnd=-1;
		for(int li=0; li<result.size(); li++){
			BaseToken bt=tokenIndexes.get(li);
			String rstr=result.get(li).trim();
			//  if(bt==null || rstr.equals("")) {inE=false; continue;}
			if(rstr.equals("O") || bt.getCoveredText().equals(",")) {
				if(inE) {
					cm.setEnd(lastEnd);
					pCMs.add(cm);
				}
				cm=null;
				inE=false;
				lastEnd=bt.getEnd();
				continue;
			}
			if(rstr.indexOf("_") >=0){
				String[] pre=rstr.split("_");      
				if(pre[pre.length-1].equals("B") || !inE ){
					inE=true;
					cm=new ConceptMention(aJCas);
					cm.setSemGroup(rstr.substring(0, rstr.length()-2));
					cm.setBegin(bt.getBegin());
					cm.setSentence(sentIndexes.get(li));
					lastEnd=bt.getEnd();
					continue;
				}
				if(pre[pre.length-1].equals("I")){
					inE=true;
					lastEnd=bt.getEnd();
					continue;
				}
			}
		}  
		if(inE) {
			cm.setEnd(lastEnd);
			pCMs.add(cm);
		}
		return pCMs;
	}


	private String getTokenString(BaseToken bt, int ws){
		String featStr="";
		if(bt.getClass().equals(shareToken.class) || bt.getClass().equals(i2b2Token.class) || bt.getClass().equals(WordToken.class)){ 
			WordToken wordToken=(WordToken) bt;
			String norm=wordToken.getCanonicalForm();
			if(norm==null) norm=wordToken.getCoveredText().toLowerCase(); 
			String ctext=bt.getCoveredText();
			if(ctext.equals(":")) {ctext="PUNC_COLON"; norm=ctext;}
			if(ctext.equals("\\")) {ctext="PUNC_SLASH"; norm=ctext;}

			featStr="\ttok["+ws+"]="+ctext+"\tnorm"+"["+ws+"]="+norm
					+"\tcapital"+"["+ws+"]="+wordToken.getCapitalization()
					+"\tpos"+"["+ws+"]="+wordToken.getPartOfSpeech()
					+getOtherFeatures(wordToken.getCoveredText(), ws) 
					+getPrefixAndSuffix(wordToken.getCoveredText(), ws);   
		}

		return featStr.replaceAll("  "," ");
	}

	private String getAnnotString(BaseToken bt, String type, List annotl){
		int size=annotl.size();
		if(type.equals("share")){
			if(bt instanceof shareToken) {
				shareToken wt = (shareToken) bt;     
				for(int j=0; j<size; j++){
					shareSlot ss=(shareSlot) annotl.get(j);
					if(ss.getSlotClass().indexOf("Disease")>=0){
						//if(ss.getSlotClass().indexOf("Abbreviation")>=0){
						if(ss.getBegin()==wt.getShareBegin()) {
							return ss.getSlotClass()+"_B";
						}
						else if (ss.getBegin() < wt.getShareBegin() && ss.getEnd()>= wt.getShareEnd()) 
						{
							return ss.getSlotClass()+"_I"; 
						}
					}
				}
				return "O";
			}
		}

		if(type.equals("i2b2")){
			if(!(bt instanceof WordToken)){}
			else{
				i2b2Token wt = (i2b2Token) bt;     

				for(int j=0; j<size; j++) {
					i2b2Event e=(i2b2Event) annotl.get(j);
					if(e.getBegin()>=0){
						if (wt.getBegin() - wt.getLineNumber() +1 ==e.getBegin()+0)
							return e.getEventType()+"_B";
						else if (wt.getBegin() - wt.getLineNumber() +1 > e.getBegin()+0 && wt.getEnd() - wt.getLineNumber() +1<= e.getEnd()+0 )
							return e.getEventType()+"_I";
					}
					else{
						if(e.getBeginLine()==wt.getLineNumber()){
							if(e.getBeginLineToken()==wt.getLineTokenNumber()) return e.getEventType()+"_B";
							else if(e.getBeginLine()==e.getEndLine() && wt.getLineTokenNumber()>e.getBeginLineToken() && wt.getLineTokenNumber()<=e.getEndLineToken()) return e.getEventType()+"_I";
							else if(e.getBeginLine()+1==e.getEndLine()&& wt.getLineTokenNumber()>e.getBeginLineToken()) return e.getEventType()+"_I";
						}
						else if(e.getBeginLine()+1==wt.getLineNumber() && e.getBeginLine()+1==e.getEndLine() && wt.getLineTokenNumber()<=e.getEndLineToken()) return e.getEventType()+"_I";
					}
				}
				return "O";
			}
		}

		return "O";
	}

	private String getCMString(BaseToken bt, List<ConceptMention> cl){
		String msg="";
		for(int j=0; j<cl.size(); j++) {
			ConceptMention cm=(ConceptMention) cl.get(j);
			if (bt.getBegin()==cm.getBegin()){
				msg+="\tcer="+cm.getCertainty() +"\tsemGrp="+cm.getSemGroup()+"\tsemText="+cm.getNormTarget().replaceAll(" ","_");
				msg+= "\tsemGrpBI="+cm.getSemGroup()+"_B";
			}
			else if (bt.getBegin()>cm.getBegin() && bt.getEnd()<=cm.getEnd() ){
				msg+="\tcer="+cm.getCertainty() +"\tsemGrp="+cm.getSemGroup()+"\tsemText="+cm.getNormTarget().replaceAll(" ","_");
				msg+="\tsemGrpBI="+cm.getSemGroup()+"_I";
			}
		}
		return msg;
	}



	private String getOtherFeatures(String text, int ws){
		String str="";

		str+=getRegexMatch(text, ws, "ALPHA", Pattern.compile("^[A-Za-z]+$"));
		str+=getRegexMatch(text, ws, "INITCAPS", Pattern.compile("^[A-Z].*$"));
		str+=getRegexMatch(text, ws, "UPPER-LOWER", Pattern.compile("^[A-Z][a-z].*$"));
		str+=getRegexMatch(text, ws, "LOWER-UPPER", Pattern.compile("^[a-z]+[A-Z]+.*$"));
		str+=getRegexMatch(text, ws, "ALLCAPS", Pattern.compile("^[A-Z]+$"));
		str+=getRegexMatch(text, ws, "MIXEDCAPS", Pattern.compile("^[A-Z][a-z]+[A-Z][A-Za-z]*$"));
		str+=getRegexMatch(text, ws, "SINGLECHAR", Pattern.compile("^[A-Za-z]$"));
		str+=getRegexMatch(text, ws, "SINGLEDIGIT", Pattern.compile("^[0-9]$"));
		str+=getRegexMatch(text, ws, "NUMBER", Pattern.compile("^[0-9,]+$"));
		str+=getRegexMatch(text, ws, "HASDIGIT", Pattern.compile("^.*[0-9].*$"));
		str+=getRegexMatch(text, ws, "ALPHANUMERIC", Pattern.compile("^.*[0-9].*[A-Za-z].*$"));
		str+=getRegexMatch(text, ws, "ALPHANUMERIC", Pattern.compile("^.*[A-Za-z].*[0-9].*$"));
		str+=getRegexMatch(text, ws, "NUMBERS_LETTERS", Pattern.compile("^[0-9]+[A-Za-z]+$"));
		str+=getRegexMatch(text, ws, "LETTERS_NUMBERS", Pattern.compile("^[A-Za-z]+[0-9]+$"));
		str+=getRegexMatch(text, ws, "ROMAN", Pattern.compile("^[IVXDLCM]+$",Pattern.CASE_INSENSITIVE));
		str+=getRegexMatch(text, ws, "GREEK", Pattern.compile("^(alpha|beta|gamma|delta|epsilon|zeta|eta|theta|iota|kappa|lambda|mu|nu|xi|omicron|pi|rho|sigma|tau|upsilon|phi|chi|psi|omega)$"));
		str+=getRegexMatch(text, ws, "ISPUNCT", Pattern.compile("^[`~!@#$%^&*()-=_+\\[\\]\\\\{}|;\':\\\",./<>?]+$"));


		return str;
	}

	private String getRegexMatch(String str, int ws, String strClass , Pattern p){
		Matcher m = p.matcher(str);
		if (m.matches()) {return "\tSTCLS["+ws+"]="+strClass;}
		else { return "";}
	}

	private String getPrefixAndSuffix(String txt, int ws){
		if( txt.length()>=3){
			return "\tPRE["+ws+"]="+txt.substring(0,3)+"\tSUF["+ws+"]="+txt.substring(txt.length()-3,txt.length());
		}
		return "";
	}
	@SuppressWarnings("rawtypes")
	public List<shareSlot> applyCRFShare(JCas aJCas, int leftWindow, int rightWindow) throws Exception {
		FileOutputStream out = null;
		File featureFile = File.createTempFile("features", ".crfsuite");
		featureFile.deleteOnExit();
		HashMap<Integer,BaseToken> tokenIndexes=new HashMap<Integer,BaseToken>();
		int featureIndex=0;    
		try {
			FSIterator it = aJCas.getAnnotationIndex(DocumentAnnotation.type).iterator();
			AnnotationIndex senIndex = aJCas.getAnnotationIndex(Sentence.type);
			AnnotationIndex tokenIndex = aJCas.getAnnotationIndex(BaseToken.type);
			AnnotationIndex cmIndex = aJCas.getAnnotationIndex(ConceptMention.type);

			DocumentAnnotation docInfo;
			if(it.hasNext()){
				docInfo = (DocumentAnnotation) it.next();
			}else {
				return null;
			}
			out = new FileOutputStream(featureFile);
			final PrintStream printStream = new PrintStream(out); 
			FSIterator senIter= senIndex.subiterator(docInfo);
			while (senIter.hasNext()) {
				Sentence sen=(Sentence) senIter.next();
				FSIterator baseTokenIter = tokenIndex.subiterator(sen);
				FSIterator cmIter = cmIndex.subiterator(sen);
				List<BaseToken> baseTokenList = new ArrayList<BaseToken>();
				List<ConceptMention> cmList = new ArrayList<ConceptMention>();

				while (baseTokenIter.hasNext()) 
					baseTokenList.add((BaseToken) baseTokenIter.next());
				while (cmIter.hasNext()) 
					cmList.add((ConceptMention) cmIter.next());


				for(int li=0; li < baseTokenList.size(); li++){
					BaseToken bt= (BaseToken) baseTokenList.get(li);
					tokenIndexes.put(featureIndex, bt);
					featureIndex++;
					printStream.print("U");
					String ctext=bt.getCoveredText();
					if(ctext.equals(":")) ctext="PUNC_COLON";
					if(ctext.equals("\\")) ctext="PUNC_SLASH";
					printStream.print("\t"+ctext);
					printStream.print(getCMString(bt, cmList));

					for(int j=li-leftWindow; j<=li+rightWindow; j++ ){
						int ws=j-li;
						if(j>=0 && j< baseTokenList.size()){
							printStream.print(getTokenString((BaseToken) baseTokenList.get(j), ws));          
						}
						else if(j<0) printStream.print("\ttok["+ws+"]SSSSS");
						else printStream.print("\ttok["+ws+"]EEEEE");
					}
					printStream.print("\n");
				}
				printStream.print("\n");
				tokenIndexes.put(featureIndex, null);
				featureIndex++;
			}

			printStream.close();
		}
		finally {  
			if (out != null) {
				out.close();
			}
		}     
		List<shareSlot> pss=new ArrayList<shareSlot>();
		List<String> result=csw.classifyFeatures(featureFile, modelFile, tokenIndexes.size()); 
		boolean inE=false;
		shareSlot ss=null;
		int lastEnd=-1;
		for(int li=0; li<result.size(); li++){
			BaseToken bt=tokenIndexes.get(li);
			String rstr=result.get(li).trim();
			if(bt==null || rstr.equals("")) {inE=false;}
			if(rstr.equals("O")) {
				if(inE) {
					ss.setEnd(lastEnd);
					pss.add(ss);
				}
				ss=null;
				inE=false;
				lastEnd=bt.getEnd();
				continue;
			}
			if(rstr.indexOf("_") >=0){
				String[] pre=rstr.split("_");      
				if(pre[1].equals("B")){
					inE=true;
					ss=new shareSlot(aJCas);
					ss.setSlotClass(pre[0]);
					ss.setBegin(bt.getBegin());
					lastEnd=bt.getEnd();
					continue;
				}
				if(pre[1].equals("I")){
					inE=true;
					lastEnd=bt.getEnd();
					continue;
				}
			}
		}  
		if(inE) {
			ss.setEnd(lastEnd);
			pss.add(ss);
		}
		return pss;
	}
}
