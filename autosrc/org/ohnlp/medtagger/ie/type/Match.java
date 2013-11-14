

/* First created by JCasGen Tue Sep 24 19:28:21 CDT 2013 */
package org.ohnlp.medtagger.ie.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.ohnlp.typesystem.type.textspan.Sentence;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 24 19:28:21 CDT 2013
 * XML source: /MedTagger/descsrc/org/ohnlp/medtagger/ie/types/MedTaggerIETypes.xml
 * @generated */
public class Match extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Match.class);
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
  protected Match() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Match(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Match(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Match(JCas jcas, int begin, int end) {
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
  //* Feature: value

  /** getter for value - gets 
   * @generated */
  public String getValue() {
    if (Match_Type.featOkTst && ((Match_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.medtagger.ie.type.Match");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Match_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated */
  public void setValue(String v) {
    if (Match_Type.featOkTst && ((Match_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.medtagger.ie.type.Match");
    jcasType.ll_cas.ll_setStringValue(addr, ((Match_Type)jcasType).casFeatCode_value, v);}    
   
    
  //*--------------*
  //* Feature: foundByRule

  /** getter for foundByRule - gets 
   * @generated */
  public String getFoundByRule() {
    if (Match_Type.featOkTst && ((Match_Type)jcasType).casFeat_foundByRule == null)
      jcasType.jcas.throwFeatMissing("foundByRule", "org.ohnlp.medtagger.ie.type.Match");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Match_Type)jcasType).casFeatCode_foundByRule);}
    
  /** setter for foundByRule - sets  
   * @generated */
  public void setFoundByRule(String v) {
    if (Match_Type.featOkTst && ((Match_Type)jcasType).casFeat_foundByRule == null)
      jcasType.jcas.throwFeatMissing("foundByRule", "org.ohnlp.medtagger.ie.type.Match");
    jcasType.ll_cas.ll_setStringValue(addr, ((Match_Type)jcasType).casFeatCode_foundByRule, v);}    
   
    
  //*--------------*
  //* Feature: sentence

  /** getter for sentence - gets 
   * @generated */
  public Sentence getSentence() {
    if (Match_Type.featOkTst && ((Match_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.ie.type.Match");
    return (Sentence)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Match_Type)jcasType).casFeatCode_sentence)));}
    
  /** setter for sentence - sets  
   * @generated */
  public void setSentence(Sentence v) {
    if (Match_Type.featOkTst && ((Match_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.ie.type.Match");
    jcasType.ll_cas.ll_setRefValue(addr, ((Match_Type)jcasType).casFeatCode_sentence, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    