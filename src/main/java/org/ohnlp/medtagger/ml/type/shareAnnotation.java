

/* First created by JCasGen Tue Sep 24 19:28:07 CDT 2013 */
package org.ohnlp.medtagger.ml.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * XML source: /MedTagger/descsrc/org/ohnlp/medtagger/ml/types/MedTaggerMLTypes.xml
 * @generated */
public class shareAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(shareAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected shareAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public shareAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public shareAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public shareAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: annotType

  /** getter for annotType - gets corresponding to mentionClass
   * @generated */
  public String getAnnotType() {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotType == null)
      jcasType.jcas.throwFeatMissing("annotType", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotType);}
    
  /** setter for annotType - sets corresponding to mentionClass 
   * @generated */
  public void setAnnotType(String v) {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotType == null)
      jcasType.jcas.throwFeatMissing("annotType", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotType, v);}    
   
    
  //*--------------*
  //* Feature: annotValue

  /** getter for annotValue - gets corresponding to mentionValue
   * @generated */
  public String getAnnotValue() {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotValue == null)
      jcasType.jcas.throwFeatMissing("annotValue", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotValue);}
    
  /** setter for annotValue - sets corresponding to mentionValue 
   * @generated */
  public void setAnnotValue(String v) {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotValue == null)
      jcasType.jcas.throwFeatMissing("annotValue", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotValue, v);}    
   
    
  //*--------------*
  //* Feature: annotSlots

  /** getter for annotSlots - gets 
   * @generated */
  public FSArray getAnnotSlots() {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotSlots == null)
      jcasType.jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotSlots)));}
    
  /** setter for annotSlots - sets  
   * @generated */
  public void setAnnotSlots(FSArray v) {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotSlots == null)
      jcasType.jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotSlots, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for annotSlots - gets an indexed value - 
   * @generated */
  public shareSlot getAnnotSlots(int i) {
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotSlots == null)
      jcasType.jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotSlots), i);
    return (shareSlot)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotSlots), i)));}

  /** indexed setter for annotSlots - sets an indexed value - 
   * @generated */
  public void setAnnotSlots(int i, shareSlot v) { 
    if (shareAnnotation_Type.featOkTst && ((shareAnnotation_Type)jcasType).casFeat_annotSlots == null)
      jcasType.jcas.throwFeatMissing("annotSlots", "org.ohnlp.medtagger.ml.type.shareAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotSlots), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((shareAnnotation_Type)jcasType).casFeatCode_annotSlots), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    