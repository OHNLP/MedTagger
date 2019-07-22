

/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Strength indicates the strength number and unit of the prescribed drug.  E.g. "5 mg" in "one 5 mg tablet twice-a-day for 2 weeks"
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class MedicationStrength extends Attribute {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MedicationStrength.class);
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
  protected MedicationStrength() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public MedicationStrength(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public MedicationStrength(JCas jcas) {
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
    if (MedicationStrength_Type.featOkTst && ((MedicationStrength_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "org.ohnlp.typesystem.type.refsem.MedicationStrength");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MedicationStrength_Type)jcasType).casFeatCode_number);}
    
  /** setter for number - sets  
   * @generated */
  public void setNumber(String v) {
    if (MedicationStrength_Type.featOkTst && ((MedicationStrength_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "org.ohnlp.typesystem.type.refsem.MedicationStrength");
    jcasType.ll_cas.ll_setStringValue(addr, ((MedicationStrength_Type)jcasType).casFeatCode_number, v);}    
   
    
  //*--------------*
  //* Feature: unit

  /** getter for unit - gets the unit of measurement
   * @generated */
  public String getUnit() {
    if (MedicationStrength_Type.featOkTst && ((MedicationStrength_Type)jcasType).casFeat_unit == null)
      jcasType.jcas.throwFeatMissing("unit", "org.ohnlp.typesystem.type.refsem.MedicationStrength");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MedicationStrength_Type)jcasType).casFeatCode_unit);}
    
  /** setter for unit - sets the unit of measurement 
   * @generated */
  public void setUnit(String v) {
    if (MedicationStrength_Type.featOkTst && ((MedicationStrength_Type)jcasType).casFeat_unit == null)
      jcasType.jcas.throwFeatMissing("unit", "org.ohnlp.typesystem.type.refsem.MedicationStrength");
    jcasType.ll_cas.ll_setStringValue(addr, ((MedicationStrength_Type)jcasType).casFeatCode_unit, v);}    
  }

    