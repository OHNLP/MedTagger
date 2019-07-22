

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The way or the equipment used to give or administration something (medication, test). This corresponds to the Procedures UMLS semantic group.
More qualifying information on how the procedure was done.
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class ProcedureMethod extends Attribute {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ProcedureMethod.class);
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
  protected ProcedureMethod() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ProcedureMethod(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ProcedureMethod(JCas jcas) {
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
    if (ProcedureMethod_Type.featOkTst && ((ProcedureMethod_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.refsem.ProcedureMethod");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ProcedureMethod_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated */
  public void setValue(String v) {
    if (ProcedureMethod_Type.featOkTst && ((ProcedureMethod_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.refsem.ProcedureMethod");
    jcasType.ll_cas.ll_setStringValue(addr, ((ProcedureMethod_Type)jcasType).casFeatCode_value, v);}    
  }

    