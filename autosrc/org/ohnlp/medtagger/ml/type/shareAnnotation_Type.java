
/* First created by JCasGen Tue Sep 24 19:28:07 CDT 2013 */
package org.ohnlp.medtagger.ml.type;

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
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * @generated */
public class shareAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (shareAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = shareAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new shareAnnotation(addr, shareAnnotation_Type.this);
  			   shareAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new shareAnnotation(addr, shareAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = shareAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.ml.type.shareAnnotation");
 
  /** @generated */
  final Feature casFeat_annotType;
  /** @generated */
  final int     casFeatCode_annotType;
  /** @generated */ 
  public String getAnnotType(int addr) {
        if (featOkTst && casFeat_annotType == null)
      jcas.throwFeatMissing("annotType", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_annotType);
  }
  /** @generated */    
  public void setAnnotType(int addr, String v) {
        if (featOkTst && casFeat_annotType == null)
      jcas.throwFeatMissing("annotType", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_annotType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_annotValue;
  /** @generated */
  final int     casFeatCode_annotValue;
  /** @generated */ 
  public String getAnnotValue(int addr) {
        if (featOkTst && casFeat_annotValue == null)
      jcas.throwFeatMissing("annotValue", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_annotValue);
  }
  /** @generated */    
  public void setAnnotValue(int addr, String v) {
        if (featOkTst && casFeat_annotValue == null)
      jcas.throwFeatMissing("annotValue", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_annotValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_annotSlots;
  /** @generated */
  final int     casFeatCode_annotSlots;
  /** @generated */ 
  public int getAnnotSlots(int addr) {
        if (featOkTst && casFeat_annotSlots == null)
      jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots);
  }
  /** @generated */    
  public void setAnnotSlots(int addr, int v) {
        if (featOkTst && casFeat_annotSlots == null)
      jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_annotSlots, v);}
    
   /** @generated */
  public int getAnnotSlots(int addr, int i) {
        if (featOkTst && casFeat_annotSlots == null)
      jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots), i);
  }
   
  /** @generated */ 
  public void setAnnotSlots(int addr, int i, int v) {
        if (featOkTst && casFeat_annotSlots == null)
      jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_annotSlots), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public shareAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_annotType = jcas.getRequiredFeatureDE(casType, "annotType", "uima.cas.String", featOkTst);
    casFeatCode_annotType  = (null == casFeat_annotType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotType).getCode();

 
    casFeat_annotValue = jcas.getRequiredFeatureDE(casType, "annotValue", "uima.cas.String", featOkTst);
    casFeatCode_annotValue  = (null == casFeat_annotValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotValue).getCode();

 
    casFeat_annotSlots = jcas.getRequiredFeatureDE(casType, "annotSlots", "uima.cas.FSArray", featOkTst);
    casFeatCode_annotSlots  = (null == casFeat_annotSlots) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotSlots).getCode();

  }
}



    