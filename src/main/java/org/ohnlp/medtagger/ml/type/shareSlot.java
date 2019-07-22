

/* First created by JCasGen Tue Sep 24 19:28:07 CDT 2013 */
package org.ohnlp.medtagger.ml.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * XML source: /MedTagger/descsrc/org/ohnlp/medtagger/ml/types/MedTaggerMLTypes.xml
 * @generated */
public class shareSlot extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(shareSlot.class);
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
  protected shareSlot() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public shareSlot(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public shareSlot(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public shareSlot(JCas jcas, int begin, int end) {
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
  //* Feature: slotClass

  /** getter for slotClass - gets The corresponding semantic classes for the slot
   * @generated */
  public String getSlotClass() {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_slotClass == null)
      jcasType.jcas.throwFeatMissing("slotClass", "org.ohnlp.medtagger.ml.type.shareSlot");
    return jcasType.ll_cas.ll_getStringValue(addr, ((shareSlot_Type)jcasType).casFeatCode_slotClass);}
    
  /** setter for slotClass - sets The corresponding semantic classes for the slot 
   * @generated */
  public void setSlotClass(String v) {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_slotClass == null)
      jcasType.jcas.throwFeatMissing("slotClass", "org.ohnlp.medtagger.ml.type.shareSlot");
    jcasType.ll_cas.ll_setStringValue(addr, ((shareSlot_Type)jcasType).casFeatCode_slotClass, v);}    
   
    
  //*--------------*
  //* Feature: annotBegin

  /** getter for annotBegin - gets The corresponding annotation begins
   * @generated */
  public int getAnnotBegin() {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_annotBegin == null)
      jcasType.jcas.throwFeatMissing("annotBegin", "org.ohnlp.medtagger.ml.type.shareSlot");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareSlot_Type)jcasType).casFeatCode_annotBegin);}
    
  /** setter for annotBegin - sets The corresponding annotation begins 
   * @generated */
  public void setAnnotBegin(int v) {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_annotBegin == null)
      jcasType.jcas.throwFeatMissing("annotBegin", "org.ohnlp.medtagger.ml.type.shareSlot");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareSlot_Type)jcasType).casFeatCode_annotBegin, v);}    
   
    
  //*--------------*
  //* Feature: annotEnd

  /** getter for annotEnd - gets The corresponding annotation ends
   * @generated */
  public int getAnnotEnd() {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_annotEnd == null)
      jcasType.jcas.throwFeatMissing("annotEnd", "org.ohnlp.medtagger.ml.type.shareSlot");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareSlot_Type)jcasType).casFeatCode_annotEnd);}
    
  /** setter for annotEnd - sets The corresponding annotation ends 
   * @generated */
  public void setAnnotEnd(int v) {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_annotEnd == null)
      jcasType.jcas.throwFeatMissing("annotEnd", "org.ohnlp.medtagger.ml.type.shareSlot");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareSlot_Type)jcasType).casFeatCode_annotEnd, v);}    
   
    
  //*--------------*
  //* Feature: slotConceptValue

  /** getter for slotConceptValue - gets The corresponding normalized concept values for the slots
   * @generated */
  public String getSlotConceptValue() {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_slotConceptValue == null)
      jcasType.jcas.throwFeatMissing("slotConceptValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    return jcasType.ll_cas.ll_getStringValue(addr, ((shareSlot_Type)jcasType).casFeatCode_slotConceptValue);}
    
  /** setter for slotConceptValue - sets The corresponding normalized concept values for the slots 
   * @generated */
  public void setSlotConceptValue(String v) {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_slotConceptValue == null)
      jcasType.jcas.throwFeatMissing("slotConceptValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    jcasType.ll_cas.ll_setStringValue(addr, ((shareSlot_Type)jcasType).casFeatCode_slotConceptValue, v);}    
   
    
  //*--------------*
  //* Feature: slotStringValue

  /** getter for slotStringValue - gets Normalized String Value
   * @generated */
  public String getSlotStringValue() {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_slotStringValue == null)
      jcasType.jcas.throwFeatMissing("slotStringValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    return jcasType.ll_cas.ll_getStringValue(addr, ((shareSlot_Type)jcasType).casFeatCode_slotStringValue);}
    
  /** setter for slotStringValue - sets Normalized String Value 
   * @generated */
  public void setSlotStringValue(String v) {
    if (shareSlot_Type.featOkTst && ((shareSlot_Type)jcasType).casFeat_slotStringValue == null)
      jcasType.jcas.throwFeatMissing("slotStringValue", "org.ohnlp.medtagger.ml.type.shareSlot");
    jcasType.ll_cas.ll_setStringValue(addr, ((shareSlot_Type)jcasType).casFeatCode_slotStringValue, v);}    
  }

    