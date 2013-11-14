
/* First created by JCasGen Tue Sep 24 19:28:21 CDT 2013 */
package org.ohnlp.medtagger.ie.type;

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

/** 
 * Updated by JCasGen Tue Sep 24 19:28:21 CDT 2013
 * @generated */
public class Match_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Match_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Match_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Match(addr, Match_Type.this);
  			   Match_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Match(addr, Match_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Match.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.ie.type.Match");
 
  /** @generated */
  final Feature casFeat_value;
  /** @generated */
  final int     casFeatCode_value;
  /** @generated */ 
  public String getValue(int addr) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "org.ohnlp.medtagger.ie.type.Match");
    return ll_cas.ll_getStringValue(addr, casFeatCode_value);
  }
  /** @generated */    
  public void setValue(int addr, String v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "org.ohnlp.medtagger.ie.type.Match");
    ll_cas.ll_setStringValue(addr, casFeatCode_value, v);}
    
  
 
  /** @generated */
  final Feature casFeat_foundByRule;
  /** @generated */
  final int     casFeatCode_foundByRule;
  /** @generated */ 
  public String getFoundByRule(int addr) {
        if (featOkTst && casFeat_foundByRule == null)
      jcas.throwFeatMissing("foundByRule", "org.ohnlp.medtagger.ie.type.Match");
    return ll_cas.ll_getStringValue(addr, casFeatCode_foundByRule);
  }
  /** @generated */    
  public void setFoundByRule(int addr, String v) {
        if (featOkTst && casFeat_foundByRule == null)
      jcas.throwFeatMissing("foundByRule", "org.ohnlp.medtagger.ie.type.Match");
    ll_cas.ll_setStringValue(addr, casFeatCode_foundByRule, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentence;
  /** @generated */
  final int     casFeatCode_sentence;
  /** @generated */ 
  public int getSentence(int addr) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.ie.type.Match");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentence);
  }
  /** @generated */    
  public void setSentence(int addr, int v) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.ie.type.Match");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Match_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_value = jcas.getRequiredFeatureDE(casType, "value", "uima.cas.String", featOkTst);
    casFeatCode_value  = (null == casFeat_value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_value).getCode();

 
    casFeat_foundByRule = jcas.getRequiredFeatureDE(casType, "foundByRule", "uima.cas.String", featOkTst);
    casFeatCode_foundByRule  = (null == casFeat_foundByRule) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_foundByRule).getCode();

 
    casFeat_sentence = jcas.getRequiredFeatureDE(casType, "sentence", "org.ohnlp.typesystem.type.textspan.Sentence", featOkTst);
    casFeatCode_sentence  = (null == casFeat_sentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentence).getCode();

  }
}



    