

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Corresponds to the UMLS Devices semantic group: medical device and research device. 
Example: arterial line has a Medical Device semantic type. This is qualifying information on how a procedure was done.
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class ProcedureDevice extends Attribute {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ProcedureDevice.class);
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
  protected ProcedureDevice() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ProcedureDevice(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ProcedureDevice(JCas jcas) {
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

  /** getter for value - gets 
   * @generated */
  public String getValue() {
    if (ProcedureDevice_Type.featOkTst && ((ProcedureDevice_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.refsem.ProcedureDevice");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ProcedureDevice_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated */
  public void setValue(String v) {
    if (ProcedureDevice_Type.featOkTst && ((ProcedureDevice_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.refsem.ProcedureDevice");
    jcasType.ll_cas.ll_setStringValue(addr, ((ProcedureDevice_Type)jcasType).casFeatCode_value, v);}    
  }

    