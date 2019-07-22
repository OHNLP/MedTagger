
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.syntax;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** Differentiates a token as being a word rather than a punctuation, symbol, newline, contraction, or number.
Equivalent to Mayo cTAKES version 2.5: edu.mayo.bmi.uima.core.type.WordToken
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class WordToken_Type extends BaseToken_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (WordToken_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = WordToken_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new WordToken(addr, WordToken_Type.this);
  			   WordToken_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new WordToken(addr, WordToken_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = WordToken.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.syntax.WordToken");
 
  /** @generated */
  final Feature casFeat_capitalization;
  /** @generated */
  final int     casFeatCode_capitalization;
  /** @generated */ 
  public int getCapitalization(int addr) {
        if (featOkTst && casFeat_capitalization == null)
      jcas.throwFeatMissing("capitalization", "org.ohnlp.typesystem.type.syntax.WordToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_capitalization);
  }
  /** @generated */    
  public void setCapitalization(int addr, int v) {
        if (featOkTst && casFeat_capitalization == null)
      jcas.throwFeatMissing("capitalization", "org.ohnlp.typesystem.type.syntax.WordToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_capitalization, v);}
    
  
 
  /** @generated */
  final Feature casFeat_numPosition;
  /** @generated */
  final int     casFeatCode_numPosition;
  /** @generated */ 
  public int getNumPosition(int addr) {
        if (featOkTst && casFeat_numPosition == null)
      jcas.throwFeatMissing("numPosition", "org.ohnlp.typesystem.type.syntax.WordToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_numPosition);
  }
  /** @generated */    
  public void setNumPosition(int addr, int v) {
        if (featOkTst && casFeat_numPosition == null)
      jcas.throwFeatMissing("numPosition", "org.ohnlp.typesystem.type.syntax.WordToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_numPosition, v);}
    
  
 
  /** @generated */
  final Feature casFeat_suggestion;
  /** @generated */
  final int     casFeatCode_suggestion;
  /** @generated */ 
  public String getSuggestion(int addr) {
        if (featOkTst && casFeat_suggestion == null)
      jcas.throwFeatMissing("suggestion", "org.ohnlp.typesystem.type.syntax.WordToken");
    return ll_cas.ll_getStringValue(addr, casFeatCode_suggestion);
  }
  /** @generated */    
  public void setSuggestion(int addr, String v) {
        if (featOkTst && casFeat_suggestion == null)
      jcas.throwFeatMissing("suggestion", "org.ohnlp.typesystem.type.syntax.WordToken");
    ll_cas.ll_setStringValue(addr, casFeatCode_suggestion, v);}
    
  
 
  /** @generated */
  final Feature casFeat_canonicalForm;
  /** @generated */
  final int     casFeatCode_canonicalForm;
  /** @generated */ 
  public String getCanonicalForm(int addr) {
        if (featOkTst && casFeat_canonicalForm == null)
      jcas.throwFeatMissing("canonicalForm", "org.ohnlp.typesystem.type.syntax.WordToken");
    return ll_cas.ll_getStringValue(addr, casFeatCode_canonicalForm);
  }
  /** @generated */    
  public void setCanonicalForm(int addr, String v) {
        if (featOkTst && casFeat_canonicalForm == null)
      jcas.throwFeatMissing("canonicalForm", "org.ohnlp.typesystem.type.syntax.WordToken");
    ll_cas.ll_setStringValue(addr, casFeatCode_canonicalForm, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public WordToken_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_capitalization = jcas.getRequiredFeatureDE(casType, "capitalization", "uima.cas.Integer", featOkTst);
    casFeatCode_capitalization  = (null == casFeat_capitalization) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_capitalization).getCode();

 
    casFeat_numPosition = jcas.getRequiredFeatureDE(casType, "numPosition", "uima.cas.Integer", featOkTst);
    casFeatCode_numPosition  = (null == casFeat_numPosition) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_numPosition).getCode();

 
    casFeat_suggestion = jcas.getRequiredFeatureDE(casType, "suggestion", "uima.cas.String", featOkTst);
    casFeatCode_suggestion  = (null == casFeat_suggestion) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_suggestion).getCode();

 
    casFeat_canonicalForm = jcas.getRequiredFeatureDE(casType, "canonicalForm", "uima.cas.String", featOkTst);
    casFeatCode_canonicalForm  = (null == casFeat_canonicalForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_canonicalForm).getCode();

  }
}



    