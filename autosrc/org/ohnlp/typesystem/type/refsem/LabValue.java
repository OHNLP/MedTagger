

/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Quantitative results of a laboratory, with number and unit.
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class LabValue extends Attribute {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(LabValue.class);
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
  protected LabValue() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public LabValue(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public LabValue(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: number

  /** getter for number - gets 
   * @generated */
  public String getNumber() {
    if (LabValue_Type.featOkTst && ((LabValue_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "org.ohnlp.typesystem.type.refsem.LabValue");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LabValue_Type)jcasType).casFeatCode_number);}
    
  /** setter for number - sets  
   * @generated */
  public void setNumber(String v) {
    if (LabValue_Type.featOkTst && ((LabValue_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "org.ohnlp.typesystem.type.refsem.LabValue");
    jcasType.ll_cas.ll_setStringValue(addr, ((LabValue_Type)jcasType).casFeatCode_number, v);}    
   
    
  //*--------------*
  //* Feature: unit

  /** getter for unit - gets 
   * @generated */
  public String getUnit() {
    if (LabValue_Type.featOkTst && ((LabValue_Type)jcasType).casFeat_unit == null)
      jcasType.jcas.throwFeatMissing("unit", "org.ohnlp.typesystem.type.refsem.LabValue");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LabValue_Type)jcasType).casFeatCode_unit);}
    
  /** setter for unit - sets  
   * @generated */
  public void setUnit(String v) {
    if (LabValue_Type.featOkTst && ((LabValue_Type)jcasType).casFeat_unit == null)
      jcasType.jcas.throwFeatMissing("unit", "org.ohnlp.typesystem.type.refsem.LabValue");
    jcasType.ll_cas.ll_setStringValue(addr, ((LabValue_Type)jcasType).casFeatCode_unit, v);}    
  }

    