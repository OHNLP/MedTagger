

/* First created by JCasGen Tue Sep 24 19:28:07 CDT 2013 */
package org.ohnlp.medtagger.ml.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.ohnlp.typesystem.type.syntax.BaseToken;


/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * XML source: /MedTagger/descsrc/org/ohnlp/medtagger/ml/types/MedTaggerMLTypes.xml
 * @generated */
public class shareToken extends BaseToken {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(shareToken.class);
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
  protected shareToken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public shareToken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public shareToken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public shareToken(JCas jcas, int begin, int end) {
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
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_lineNumber == null)
      jcasType.jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_lineNumber);}
    
  /** setter for lineNumber - sets  
   * @generated */
  public void setLineNumber(int v) {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_lineNumber == null)
      jcasType.jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_lineNumber, v);}    
   
    
  //*--------------*
  //* Feature: lineTokenNumber

  /** getter for lineTokenNumber - gets index of the token on the line begining from 0
   * @generated */
  public int getLineTokenNumber() {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_lineTokenNumber == null)
      jcasType.jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_lineTokenNumber);}
    
  /** setter for lineTokenNumber - sets index of the token on the line begining from 0 
   * @generated */
  public void setLineTokenNumber(int v) {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_lineTokenNumber == null)
      jcasType.jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_lineTokenNumber, v);}    
   
    
  //*--------------*
  //* Feature: shareBegin

  /** getter for shareBegin - gets 
   * @generated */
  public int getShareBegin() {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_shareBegin == null)
      jcasType.jcas.throwFeatMissing("shareBegin", "org.ohnlp.medtagger.ml.type.shareToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_shareBegin);}
    
  /** setter for shareBegin - sets  
   * @generated */
  public void setShareBegin(int v) {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_shareBegin == null)
      jcasType.jcas.throwFeatMissing("shareBegin", "org.ohnlp.medtagger.ml.type.shareToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_shareBegin, v);}    
   
    
  //*--------------*
  //* Feature: shareEnd

  /** getter for shareEnd - gets 
   * @generated */
  public int getShareEnd() {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_shareEnd == null)
      jcasType.jcas.throwFeatMissing("shareEnd", "org.ohnlp.medtagger.ml.type.shareToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_shareEnd);}
    
  /** setter for shareEnd - sets  
   * @generated */
  public void setShareEnd(int v) {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_shareEnd == null)
      jcasType.jcas.throwFeatMissing("shareEnd", "org.ohnlp.medtagger.ml.type.shareToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_shareEnd, v);}    
   
    
  //*--------------*
  //* Feature: capitalization

  /** getter for capitalization - gets 
   * @generated */
  public int getCapitalization() {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_capitalization == null)
      jcasType.jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.shareToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_capitalization);}
    
  /** setter for capitalization - sets  
   * @generated */
  public void setCapitalization(int v) {
    if (shareToken_Type.featOkTst && ((shareToken_Type)jcasType).casFeat_capitalization == null)
      jcasType.jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.shareToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((shareToken_Type)jcasType).casFeatCode_capitalization, v);}    
  }

    