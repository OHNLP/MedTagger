

/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Status refers to the whether the medication is currently being taken or not.  Value set: start, stop, increase, decrease, no change.
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class MedicationStatusChange extends Attribute {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MedicationStatusChange.class);
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
  protected MedicationStatusChange() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public MedicationStatusChange(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public MedicationStatusChange(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets Indicates the change status of 'stop', 'start', 'increase', 'decrease', or 'noChange'.
   * @generated */
  public String getValue() {
    if (MedicationStatusChange_Type.featOkTst && ((MedicationStatusChange_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.refsem.MedicationStatusChange");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MedicationStatusChange_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets Indicates the change status of 'stop', 'start', 'increase', 'decrease', or 'noChange'. 
   * @generated */
  public void setValue(String v) {
    if (MedicationStatusChange_Type.featOkTst && ((MedicationStatusChange_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.refsem.MedicationStatusChange");
    jcasType.ll_cas.ll_setStringValue(addr, ((MedicationStatusChange_Type)jcasType).casFeatCode_value, v);}    
  }

    