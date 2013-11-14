

/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.ohnlp.typesystem.type.relation.DegreeOf;


/** This is an Event from the UMLS semantic group of Laboratory Procedures.  Based on generic Clinical Element Models (CEMs)
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class Lab extends Event {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Lab.class);
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
  protected Lab() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Lab(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Lab(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: abnormalInterpretation

  /** getter for abnormalInterpretation - gets 
   * @generated */
  public DegreeOf getAbnormalInterpretation() {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_abnormalInterpretation == null)
      jcasType.jcas.throwFeatMissing("abnormalInterpretation", "org.ohnlp.typesystem.type.refsem.Lab");
    return (DegreeOf)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Lab_Type)jcasType).casFeatCode_abnormalInterpretation)));}
    
  /** setter for abnormalInterpretation - sets  
   * @generated */
  public void setAbnormalInterpretation(DegreeOf v) {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_abnormalInterpretation == null)
      jcasType.jcas.throwFeatMissing("abnormalInterpretation", "org.ohnlp.typesystem.type.refsem.Lab");
    jcasType.ll_cas.ll_setRefValue(addr, ((Lab_Type)jcasType).casFeatCode_abnormalInterpretation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: deltaFlag

  /** getter for deltaFlag - gets 
   * @generated */
  public LabDeltaFlag getDeltaFlag() {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_deltaFlag == null)
      jcasType.jcas.throwFeatMissing("deltaFlag", "org.ohnlp.typesystem.type.refsem.Lab");
    return (LabDeltaFlag)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Lab_Type)jcasType).casFeatCode_deltaFlag)));}
    
  /** setter for deltaFlag - sets  
   * @generated */
  public void setDeltaFlag(LabDeltaFlag v) {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_deltaFlag == null)
      jcasType.jcas.throwFeatMissing("deltaFlag", "org.ohnlp.typesystem.type.refsem.Lab");
    jcasType.ll_cas.ll_setRefValue(addr, ((Lab_Type)jcasType).casFeatCode_deltaFlag, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: labValue

  /** getter for labValue - gets 
   * @generated */
  public LabValue getLabValue() {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_labValue == null)
      jcasType.jcas.throwFeatMissing("labValue", "org.ohnlp.typesystem.type.refsem.Lab");
    return (LabValue)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Lab_Type)jcasType).casFeatCode_labValue)));}
    
  /** setter for labValue - sets  
   * @generated */
  public void setLabValue(LabValue v) {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_labValue == null)
      jcasType.jcas.throwFeatMissing("labValue", "org.ohnlp.typesystem.type.refsem.Lab");
    jcasType.ll_cas.ll_setRefValue(addr, ((Lab_Type)jcasType).casFeatCode_labValue, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: ordinalInterpretation

  /** getter for ordinalInterpretation - gets 
   * @generated */
  public DegreeOf getOrdinalInterpretation() {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_ordinalInterpretation == null)
      jcasType.jcas.throwFeatMissing("ordinalInterpretation", "org.ohnlp.typesystem.type.refsem.Lab");
    return (DegreeOf)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Lab_Type)jcasType).casFeatCode_ordinalInterpretation)));}
    
  /** setter for ordinalInterpretation - sets  
   * @generated */
  public void setOrdinalInterpretation(DegreeOf v) {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_ordinalInterpretation == null)
      jcasType.jcas.throwFeatMissing("ordinalInterpretation", "org.ohnlp.typesystem.type.refsem.Lab");
    jcasType.ll_cas.ll_setRefValue(addr, ((Lab_Type)jcasType).casFeatCode_ordinalInterpretation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: referenceRangeNarrative

  /** getter for referenceRangeNarrative - gets 
   * @generated */
  public LabReferenceRange getReferenceRangeNarrative() {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_referenceRangeNarrative == null)
      jcasType.jcas.throwFeatMissing("referenceRangeNarrative", "org.ohnlp.typesystem.type.refsem.Lab");
    return (LabReferenceRange)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Lab_Type)jcasType).casFeatCode_referenceRangeNarrative)));}
    
  /** setter for referenceRangeNarrative - sets  
   * @generated */
  public void setReferenceRangeNarrative(LabReferenceRange v) {
    if (Lab_Type.featOkTst && ((Lab_Type)jcasType).casFeat_referenceRangeNarrative == null)
      jcasType.jcas.throwFeatMissing("referenceRangeNarrative", "org.ohnlp.typesystem.type.refsem.Lab");
    jcasType.ll_cas.ll_setRefValue(addr, ((Lab_Type)jcasType).casFeatCode_referenceRangeNarrative, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    