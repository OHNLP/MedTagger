

/* First created by JCasGen Tue Sep 24 19:28:07 CDT 2013 */
package org.ohnlp.medtagger.ml.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.ohnlp.typesystem.type.syntax.WordToken;


/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * XML source: /MedTagger/descsrc/org/ohnlp/medtagger/ml/types/MedTaggerMLTypes.xml
 * @generated */
public class i2b2Token extends WordToken {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(i2b2Token.class);
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
  protected i2b2Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public i2b2Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public i2b2Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public i2b2Token(JCas jcas, int begin, int end) {
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
  //* Feature: lineNumber

  /** getter for lineNumber - gets 
   * @generated */
  public int getLineNumber() {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_lineNumber == null)
      jcasType.jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_lineNumber);}
    
  /** setter for lineNumber - sets  
   * @generated */
  public void setLineNumber(int v) {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_lineNumber == null)
      jcasType.jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_lineNumber, v);}    
   
    
  //*--------------*
  //* Feature: lineTokenNumber

  /** getter for lineTokenNumber - gets index of the token on the line beginning from 0
   * @generated */
  public int getLineTokenNumber() {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_lineTokenNumber == null)
      jcasType.jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_lineTokenNumber);}
    
  /** setter for lineTokenNumber - sets index of the token on the line beginning from 0 
   * @generated */
  public void setLineTokenNumber(int v) {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_lineTokenNumber == null)
      jcasType.jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_lineTokenNumber, v);}    
   
    
  //*--------------*
  //* Feature: i2b2Begin

  /** getter for i2b2Begin - gets 
   * @generated */
  public int getI2b2Begin() {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_i2b2Begin == null)
      jcasType.jcas.throwFeatMissing("i2b2Begin", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_i2b2Begin);}
    
  /** setter for i2b2Begin - sets  
   * @generated */
  public void setI2b2Begin(int v) {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_i2b2Begin == null)
      jcasType.jcas.throwFeatMissing("i2b2Begin", "org.ohnlp.medtagger.ml.type.i2b2Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_i2b2Begin, v);}    
   
    
  //*--------------*
  //* Feature: i2b2End

  /** getter for i2b2End - gets 
   * @generated */
  public int getI2b2End() {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_i2b2End == null)
      jcasType.jcas.throwFeatMissing("i2b2End", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_i2b2End);}
    
  /** setter for i2b2End - sets  
   * @generated */
  public void setI2b2End(int v) {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_i2b2End == null)
      jcasType.jcas.throwFeatMissing("i2b2End", "org.ohnlp.medtagger.ml.type.i2b2Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_i2b2End, v);}    
   
    
  //*--------------*
  //* Feature: capitalization

  /** getter for capitalization - gets 
   * @generated */
  public int getCapitalization() {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_capitalization == null)
      jcasType.jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_capitalization);}
    
  /** setter for capitalization - sets  
   * @generated */
  public void setCapitalization(int v) {
    if (i2b2Token_Type.featOkTst && ((i2b2Token_Type)jcasType).casFeat_capitalization == null)
      jcasType.jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.i2b2Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((i2b2Token_Type)jcasType).casFeatCode_capitalization, v);}    
  }

    