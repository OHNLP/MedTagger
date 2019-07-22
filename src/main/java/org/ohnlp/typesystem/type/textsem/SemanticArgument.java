

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.textsem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** SemanticArguments are used for predicate-argument structures (SemanticRoleRelations).  The "label" features should contain the type of semantic role (e.g., ARG0, ARGM) that this argument has w.r.t. the predicate.
 * Updated by JCasGen Wed Oct 30 16:30:50 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class SemanticArgument extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SemanticArgument.class);
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
  protected SemanticArgument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SemanticArgument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SemanticArgument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SemanticArgument(JCas jcas, int begin, int end) {
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
  //* Feature: relation

  /** getter for relation - gets 
   * @generated */
  public SemanticRoleRelation getRelation() {
    if (SemanticArgument_Type.featOkTst && ((SemanticArgument_Type)jcasType).casFeat_relation == null)
      jcasType.jcas.throwFeatMissing("relation", "org.ohnlp.typesystem.type.textsem.SemanticArgument");
    return (SemanticRoleRelation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((SemanticArgument_Type)jcasType).casFeatCode_relation)));}
    
  /** setter for relation - sets  
   * @generated */
  public void setRelation(SemanticRoleRelation v) {
    if (SemanticArgument_Type.featOkTst && ((SemanticArgument_Type)jcasType).casFeat_relation == null)
      jcasType.jcas.throwFeatMissing("relation", "org.ohnlp.typesystem.type.textsem.SemanticArgument");
    jcasType.ll_cas.ll_setRefValue(addr, ((SemanticArgument_Type)jcasType).casFeatCode_relation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: label

  /** getter for label - gets 
   * @generated */
  public String getLabel() {
    if (SemanticArgument_Type.featOkTst && ((SemanticArgument_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "org.ohnlp.typesystem.type.textsem.SemanticArgument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticArgument_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets  
   * @generated */
  public void setLabel(String v) {
    if (SemanticArgument_Type.featOkTst && ((SemanticArgument_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "org.ohnlp.typesystem.type.textsem.SemanticArgument");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticArgument_Type)jcasType).casFeatCode_label, v);}    
  }

    