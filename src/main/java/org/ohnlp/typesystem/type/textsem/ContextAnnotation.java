

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.textsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Contextual information of an entity. Equivalent to Mayo cTAKES version 2.5: edu.mayo.bmi.uima.context.type.ContextAnnotation
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class ContextAnnotation extends IdentifiedAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ContextAnnotation.class);
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
  protected ContextAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ContextAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ContextAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ContextAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: FocusText

  /** getter for FocusText - gets 
   * @generated */
  public String getFocusText() {
    if (ContextAnnotation_Type.featOkTst && ((ContextAnnotation_Type)jcasType).casFeat_FocusText == null)
      jcasType.jcas.throwFeatMissing("FocusText", "org.ohnlp.typesystem.type.textsem.ContextAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ContextAnnotation_Type)jcasType).casFeatCode_FocusText);}
    
  /** setter for FocusText - sets  
   * @generated */
  public void setFocusText(String v) {
    if (ContextAnnotation_Type.featOkTst && ((ContextAnnotation_Type)jcasType).casFeat_FocusText == null)
      jcasType.jcas.throwFeatMissing("FocusText", "org.ohnlp.typesystem.type.textsem.ContextAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((ContextAnnotation_Type)jcasType).casFeatCode_FocusText, v);}    
   
    
  //*--------------*
  //* Feature: Scope

  /** getter for Scope - gets 
   * @generated */
  public String getScope() {
    if (ContextAnnotation_Type.featOkTst && ((ContextAnnotation_Type)jcasType).casFeat_Scope == null)
      jcasType.jcas.throwFeatMissing("Scope", "org.ohnlp.typesystem.type.textsem.ContextAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ContextAnnotation_Type)jcasType).casFeatCode_Scope);}
    
  /** setter for Scope - sets  
   * @generated */
  public void setScope(String v) {
    if (ContextAnnotation_Type.featOkTst && ((ContextAnnotation_Type)jcasType).casFeat_Scope == null)
      jcasType.jcas.throwFeatMissing("Scope", "org.ohnlp.typesystem.type.textsem.ContextAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((ContextAnnotation_Type)jcasType).casFeatCode_Scope, v);}    
  }

    