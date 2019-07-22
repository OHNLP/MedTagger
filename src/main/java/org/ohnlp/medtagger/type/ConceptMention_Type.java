
/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.medtagger.type;

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

/** Concept mention stands for concepts detected by the NLP system
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * @generated */
public class ConceptMention_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ConceptMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ConceptMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ConceptMention(addr, ConceptMention_Type.this);
  			   ConceptMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ConceptMention(addr, ConceptMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ConceptMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.type.ConceptMention");
 
  /** @generated */
  final Feature casFeat_detectionMethod;
  /** @generated */
  final int     casFeatCode_detectionMethod;
  /** @generated */ 
  public String getDetectionMethod(int addr) {
        if (featOkTst && casFeat_detectionMethod == null)
      jcas.throwFeatMissing("detectionMethod", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_detectionMethod);
  }
  /** @generated */    
  public void setDetectionMethod(int addr, String v) {
        if (featOkTst && casFeat_detectionMethod == null)
      jcas.throwFeatMissing("detectionMethod", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_detectionMethod, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normTarget;
  /** @generated */
  final int     casFeatCode_normTarget;
  /** @generated */ 
  public String getNormTarget(int addr) {
        if (featOkTst && casFeat_normTarget == null)
      jcas.throwFeatMissing("normTarget", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normTarget);
  }
  /** @generated */    
  public void setNormTarget(int addr, String v) {
        if (featOkTst && casFeat_normTarget == null)
      jcas.throwFeatMissing("normTarget", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_normTarget, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Certainty;
  /** @generated */
  final int     casFeatCode_Certainty;
  /** @generated */ 
  public String getCertainty(int addr) {
        if (featOkTst && casFeat_Certainty == null)
      jcas.throwFeatMissing("Certainty", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Certainty);
  }
  /** @generated */    
  public void setCertainty(int addr, String v) {
        if (featOkTst && casFeat_Certainty == null)
      jcas.throwFeatMissing("Certainty", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_Certainty, v);}
    
  
 
  /** @generated */
  final Feature casFeat_semGroup;
  /** @generated */
  final int     casFeatCode_semGroup;
  /** @generated */ 
  public String getSemGroup(int addr) {
        if (featOkTst && casFeat_semGroup == null)
      jcas.throwFeatMissing("semGroup", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_semGroup);
  }
  /** @generated */    
  public void setSemGroup(int addr, String v) {
        if (featOkTst && casFeat_semGroup == null)
      jcas.throwFeatMissing("semGroup", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_semGroup, v);}
    
  
 
  /** @generated */
  final Feature casFeat_status;
  /** @generated */
  final int     casFeatCode_status;
  /** @generated */ 
  public String getStatus(int addr) {
        if (featOkTst && casFeat_status == null)
      jcas.throwFeatMissing("status", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_status);
  }
  /** @generated */    
  public void setStatus(int addr, String v) {
        if (featOkTst && casFeat_status == null)
      jcas.throwFeatMissing("status", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_status, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentence;
  /** @generated */
  final int     casFeatCode_sentence;
  /** @generated */ 
  public int getSentence(int addr) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentence);
  }
  /** @generated */    
  public void setSentence(int addr, int v) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_experiencer;
  /** @generated */
  final int     casFeatCode_experiencer;
  /** @generated */ 
  public String getExperiencer(int addr) {
        if (featOkTst && casFeat_experiencer == null)
      jcas.throwFeatMissing("experiencer", "org.ohnlp.medtagger.type.ConceptMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_experiencer);
  }
  /** @generated */    
  public void setExperiencer(int addr, String v) {
        if (featOkTst && casFeat_experiencer == null)
      jcas.throwFeatMissing("experiencer", "org.ohnlp.medtagger.type.ConceptMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_experiencer, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public ConceptMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_detectionMethod = jcas.getRequiredFeatureDE(casType, "detectionMethod", "uima.cas.String", featOkTst);
    casFeatCode_detectionMethod  = (null == casFeat_detectionMethod) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_detectionMethod).getCode();

 
    casFeat_normTarget = jcas.getRequiredFeatureDE(casType, "normTarget", "uima.cas.String", featOkTst);
    casFeatCode_normTarget  = (null == casFeat_normTarget) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normTarget).getCode();

 
    casFeat_Certainty = jcas.getRequiredFeatureDE(casType, "Certainty", "uima.cas.String", featOkTst);
    casFeatCode_Certainty  = (null == casFeat_Certainty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Certainty).getCode();

 
    casFeat_semGroup = jcas.getRequiredFeatureDE(casType, "semGroup", "uima.cas.String", featOkTst);
    casFeatCode_semGroup  = (null == casFeat_semGroup) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_semGroup).getCode();

 
    casFeat_status = jcas.getRequiredFeatureDE(casType, "status", "uima.cas.String", featOkTst);
    casFeatCode_status  = (null == casFeat_status) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_status).getCode();

 
    casFeat_sentence = jcas.getRequiredFeatureDE(casType, "sentence", "org.ohnlp.typesystem.type.textspan.Sentence", featOkTst);
    casFeatCode_sentence  = (null == casFeat_sentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentence).getCode();

 
    casFeat_experiencer = jcas.getRequiredFeatureDE(casType, "experiencer", "uima.cas.String", featOkTst);
    casFeatCode_experiencer  = (null == casFeat_experiencer) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_experiencer).getCode();

  }
}



    