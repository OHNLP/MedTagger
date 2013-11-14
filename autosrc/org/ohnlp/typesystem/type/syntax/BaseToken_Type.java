
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** A supertype for tokens subsuming word, punctuation, symbol, newline, contraction, or number.  Includes parts of speech, which are grammatical categories, e.g., noun (NN) or preposition (IN) that use Penn Treebank tags with a few additions.
Equivalent to Mayo cTAKES version 2.5: edu.mayo.bmi.uima.core.type.BaseToken
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class BaseToken_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (BaseToken_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = BaseToken_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new BaseToken(addr, BaseToken_Type.this);
  			   BaseToken_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new BaseToken(addr, BaseToken_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = BaseToken.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.syntax.BaseToken");
 
  /** @generated */
  final Feature casFeat_tokenNumber;
  /** @generated */
  final int     casFeatCode_tokenNumber;
  /** @generated */ 
  public int getTokenNumber(int addr) {
        if (featOkTst && casFeat_tokenNumber == null)
      jcas.throwFeatMissing("tokenNumber", "org.ohnlp.typesystem.type.syntax.BaseToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_tokenNumber);
  }
  /** @generated */    
  public void setTokenNumber(int addr, int v) {
        if (featOkTst && casFeat_tokenNumber == null)
      jcas.throwFeatMissing("tokenNumber", "org.ohnlp.typesystem.type.syntax.BaseToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_tokenNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normalizedForm;
  /** @generated */
  final int     casFeatCode_normalizedForm;
  /** @generated */ 
  public String getNormalizedForm(int addr) {
        if (featOkTst && casFeat_normalizedForm == null)
      jcas.throwFeatMissing("normalizedForm", "org.ohnlp.typesystem.type.syntax.BaseToken");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normalizedForm);
  }
  /** @generated */    
  public void setNormalizedForm(int addr, String v) {
        if (featOkTst && casFeat_normalizedForm == null)
      jcas.throwFeatMissing("normalizedForm", "org.ohnlp.typesystem.type.syntax.BaseToken");
    ll_cas.ll_setStringValue(addr, casFeatCode_normalizedForm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_partOfSpeech;
  /** @generated */
  final int     casFeatCode_partOfSpeech;
  /** @generated */ 
  public String getPartOfSpeech(int addr) {
        if (featOkTst && casFeat_partOfSpeech == null)
      jcas.throwFeatMissing("partOfSpeech", "org.ohnlp.typesystem.type.syntax.BaseToken");
    return ll_cas.ll_getStringValue(addr, casFeatCode_partOfSpeech);
  }
  /** @generated */    
  public void setPartOfSpeech(int addr, String v) {
        if (featOkTst && casFeat_partOfSpeech == null)
      jcas.throwFeatMissing("partOfSpeech", "org.ohnlp.typesystem.type.syntax.BaseToken");
    ll_cas.ll_setStringValue(addr, casFeatCode_partOfSpeech, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lemmaEntries;
  /** @generated */
  final int     casFeatCode_lemmaEntries;
  /** @generated */ 
  public int getLemmaEntries(int addr) {
        if (featOkTst && casFeat_lemmaEntries == null)
      jcas.throwFeatMissing("lemmaEntries", "org.ohnlp.typesystem.type.syntax.BaseToken");
    return ll_cas.ll_getRefValue(addr, casFeatCode_lemmaEntries);
  }
  /** @generated */    
  public void setLemmaEntries(int addr, int v) {
        if (featOkTst && casFeat_lemmaEntries == null)
      jcas.throwFeatMissing("lemmaEntries", "org.ohnlp.typesystem.type.syntax.BaseToken");
    ll_cas.ll_setRefValue(addr, casFeatCode_lemmaEntries, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public BaseToken_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tokenNumber = jcas.getRequiredFeatureDE(casType, "tokenNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_tokenNumber  = (null == casFeat_tokenNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenNumber).getCode();

 
    casFeat_normalizedForm = jcas.getRequiredFeatureDE(casType, "normalizedForm", "uima.cas.String", featOkTst);
    casFeatCode_normalizedForm  = (null == casFeat_normalizedForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalizedForm).getCode();

 
    casFeat_partOfSpeech = jcas.getRequiredFeatureDE(casType, "partOfSpeech", "uima.cas.String", featOkTst);
    casFeatCode_partOfSpeech  = (null == casFeat_partOfSpeech) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_partOfSpeech).getCode();

 
    casFeat_lemmaEntries = jcas.getRequiredFeatureDE(casType, "lemmaEntries", "uima.cas.FSList", featOkTst);
    casFeatCode_lemmaEntries  = (null == casFeat_lemmaEntries) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemmaEntries).getCode();

  }
}



    