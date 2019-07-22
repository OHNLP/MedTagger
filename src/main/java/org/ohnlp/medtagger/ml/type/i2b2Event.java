

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
public class i2b2Event extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(i2b2Event.class);
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
  protected i2b2Event() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public i2b2Event(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public i2b2Event(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public i2b2Event(JCas jcas, int begin, int end) {
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
  //* Feature: eventType

  /** getter for eventType - gets occurrence, evidential, test, problem, treatment or clinical_dept
   * @generated */
  public String getEventType() {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_eventType == null)
      jcasType.jcas.throwFeatMissing("eventType", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_eventType);}
    
  /** setter for eventType - sets occurrence, evidential, test, problem, treatment or clinical_dept 
   * @generated */
  public void setEventType(String v) {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_eventType == null)
      jcasType.jcas.throwFeatMissing("eventType", "org.ohnlp.medtagger.ml.type.i2b2Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_eventType, v);}    
   
    
  //*--------------*
  //* Feature: beginLineToken

  /** getter for beginLineToken - gets index of the tokens in the line, where the event starts starts with 0
   * @generated */
  public int getBeginLineToken() {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_beginLineToken == null)
      jcasType.jcas.throwFeatMissing("beginLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_beginLineToken);}
    
  /** setter for beginLineToken - sets index of the tokens in the line, where the event starts starts with 0 
   * @generated */
  public void setBeginLineToken(int v) {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_beginLineToken == null)
      jcasType.jcas.throwFeatMissing("beginLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_beginLineToken, v);}    
   
    
  //*--------------*
  //* Feature: endLineToken

  /** getter for endLineToken - gets index of the tokens in the line, where the event ends
   * @generated */
  public int getEndLineToken() {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_endLineToken == null)
      jcasType.jcas.throwFeatMissing("endLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_endLineToken);}
    
  /** setter for endLineToken - sets index of the tokens in the line, where the event ends 
   * @generated */
  public void setEndLineToken(int v) {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_endLineToken == null)
      jcasType.jcas.throwFeatMissing("endLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_endLineToken, v);}    
   
    
  //*--------------*
  //* Feature: beginLine

  /** getter for beginLine - gets 
   * @generated */
  public int getBeginLine() {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_beginLine == null)
      jcasType.jcas.throwFeatMissing("beginLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_beginLine);}
    
  /** setter for beginLine - sets  
   * @generated */
  public void setBeginLine(int v) {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_beginLine == null)
      jcasType.jcas.throwFeatMissing("beginLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_beginLine, v);}    
   
    
  //*--------------*
  //* Feature: endLine

  /** getter for endLine - gets 
   * @generated */
  public int getEndLine() {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_endLine == null)
      jcasType.jcas.throwFeatMissing("endLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_endLine);}
    
  /** setter for endLine - sets  
   * @generated */
  public void setEndLine(int v) {
    if (i2b2Event_Type.featOkTst && ((i2b2Event_Type)jcasType).casFeat_endLine == null)
      jcasType.jcas.throwFeatMissing("endLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Event_Type)jcasType).casFeatCode_endLine, v);}    
  }

    