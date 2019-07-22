
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
public class shareSlot_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (shareSlot_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = shareSlot_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new shareSlot(addr, shareSlot_Type.this);
  			   shareSlot_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new shareSlot(addr, shareSlot_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = shareSlot.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.ml.type.shareSlot");
 
  /** @generated */
  final Feature casFeat_slotClass;
  /** @generated */
  final int     casFeatCode_slotClass;
  /** @generated */ 
  public String getSlotClass(int addr) {
        if (featOkTst && casFeat_slotClass == null)
      jcas.throwFeatMissing("slotClass", "org.ohnlp.medtagger.ml.type.shareSlot");
    return ll_cas.ll_getStringValue(addr, casFeatCode_slotClass);
  }
  /** @generated */    
  public void setSlotClass(int addr, String v) {
        if (featOkTst && casFeat_slotClass == null)
      jcas.throwFeatMissing("slotClass", "org.ohnlp.medtagger.ml.type.shareSlot");
    ll_cas.ll_setStringValue(addr, casFeatCode_slotClass, v);}
    
  
 
  /** @generated */
  final Feature casFeat_annotBegin;
  /** @generated */
  final int     casFeatCode_annotBegin;
  /** @generated */ 
  public int getAnnotBegin(int addr) {
        if (featOkTst && casFeat_annotBegin == null)
      jcas.throwFeatMissing("annotBegin", "org.ohnlp.medtagger.ml.type.shareSlot");
    return ll_cas.ll_getIntValue(addr, casFeatCode_annotBegin);
  }
  /** @generated */    
  public void setAnnotBegin(int addr, int v) {
        if (featOkTst && casFeat_annotBegin == null)
      jcas.throwFeatMissing("annotBegin", "org.ohnlp.medtagger.ml.type.shareSlot");
    ll_cas.ll_setIntValue(addr, casFeatCode_annotBegin, v);}
    
  
 
  /** @generated */
  final Feature casFeat_annotEnd;
  /** @generated */
  final int     casFeatCode_annotEnd;
  /** @generated */ 
  public int getAnnotEnd(int addr) {
        if (featOkTst && casFeat_annotEnd == null)
      jcas.throwFeatMissing("annotEnd", "org.ohnlp.medtagger.ml.type.shareSlot");
    return ll_cas.ll_getIntValue(addr, casFeatCode_annotEnd);
  }
  /** @generated */    
  public void setAnnotEnd(int addr, int v) {
        if (featOkTst && casFeat_annotEnd == null)
      jcas.throwFeatMissing("annotEnd", "org.ohnlp.medtagger.ml.type.shareSlot");
    ll_cas.ll_setIntValue(addr, casFeatCode_annotEnd, v);}
    
  
 
  /** @generated */
  final Feature casFeat_slotConceptValue;
  /** @generated */
  final int     casFeatCode_slotConceptValue;
  /** @generated */ 
  public String getSlotConceptValue(int addr) {
        if (featOkTst && casFeat_slotConceptValue == null)
      jcas.throwFeatMissing("slotConceptValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    return ll_cas.ll_getStringValue(addr, casFeatCode_slotConceptValue);
  }
  /** @generated */    
  public void setSlotConceptValue(int addr, String v) {
        if (featOkTst && casFeat_slotConceptValue == null)
      jcas.throwFeatMissing("slotConceptValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    ll_cas.ll_setStringValue(addr, casFeatCode_slotConceptValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_slotStringValue;
  /** @generated */
  final int     casFeatCode_slotStringValue;
  /** @generated */ 
  public String getSlotStringValue(int addr) {
        if (featOkTst && casFeat_slotStringValue == null)
      jcas.throwFeatMissing("slotStringValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    return ll_cas.ll_getStringValue(addr, casFeatCode_slotStringValue);
  }
  /** @generated */    
  public void setSlotStringValue(int addr, String v) {
        if (featOkTst && casFeat_slotStringValue == null)
      jcas.throwFeatMissing("slotStringValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    ll_cas.ll_setStringValue(addr, casFeatCode_slotStringValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public shareSlot_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_slotClass = jcas.getRequiredFeatureDE(casType, "slotClass", "uima.cas.String", featOkTst);
    casFeatCode_slotClass  = (null == casFeat_slotClass) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_slotClass).getCode();

 
    casFeat_annotBegin = jcas.getRequiredFeatureDE(casType, "annotBegin", "uima.cas.Integer", featOkTst);
    casFeatCode_annotBegin  = (null == casFeat_annotBegin) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotBegin).getCode();

 
    casFeat_annotEnd = jcas.getRequiredFeatureDE(casType, "annotEnd", "uima.cas.Integer", featOkTst);
    casFeatCode_annotEnd  = (null == casFeat_annotEnd) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotEnd).getCode();

 
    casFeat_slotConceptValue = jcas.getRequiredFeatureDE(casType, "slotConceptValue", "uima.cas.String", featOkTst);
    casFeatCode_slotConceptValue  = (null == casFeat_slotConceptValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_slotConceptValue).getCode();

 
    casFeat_slotStringValue = jcas.getRequiredFeatureDE(casType, "slotStringValue", "uima.cas.String", featOkTst);
    casFeatCode_slotStringValue  = (null == casFeat_slotStringValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_slotStringValue).getCode();

  }
}



    