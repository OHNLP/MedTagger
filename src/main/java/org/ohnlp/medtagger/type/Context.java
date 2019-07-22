

/* First created by JCasGen Wed Oct 30 16:30:48 CDT 2013 */
package org.ohnlp.medtagger.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Stores the context matches
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class Context extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Context.class);
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
  protected Context() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Context(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Context(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Context(JCas jcas, int begin, int end) {
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
  //* Feature: position

  /** getter for position - gets Locate before or after or a termination of a context or pseudo of the concept mention (POST, PRE, BOTH, TERMIN, PSEUDO)
   * @generated */
  public String getPosition() {
    if (Context_Type.featOkTst && ((Context_Type)jcasType).casFeat_position == null)
      jcasType.jcas.throwFeatMissing("position", "org.ohnlp.medtagger.type.Context");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Context_Type)jcasType).casFeatCode_position);}
    
  /** setter for position - sets Locate before or after or a termination of a context or pseudo of the concept mention (POST, PRE, BOTH, TERMIN, PSEUDO) 
   * @generated */
  public void setPosition(String v) {
    if (Context_Type.featOkTst && ((Context_Type)jcasType).casFeat_position == null)
      jcasType.jcas.throwFeatMissing("position", "org.ohnlp.medtagger.type.Context");
    jcasType.ll_cas.ll_setStringValue(addr, ((Context_Type)jcasType).casFeatCode_position, v);}    
   
    
  //*--------------*
  //* Feature: contextType

  /** getter for contextType - gets the type of the context words (neg, hist, poss, exp, hypoexp)
   * @generated */
  public String getContextType() {
    if (Context_Type.featOkTst && ((Context_Type)jcasType).casFeat_contextType == null)
      jcasType.jcas.throwFeatMissing("contextType", "org.ohnlp.medtagger.type.Context");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Context_Type)jcasType).casFeatCode_contextType);}
    
  /** setter for contextType - sets the type of the context words (neg, hist, poss, exp, hypoexp) 
   * @generated */
  public void setContextType(String v) {
    if (Context_Type.featOkTst && ((Context_Type)jcasType).casFeat_contextType == null)
      jcasType.jcas.throwFeatMissing("contextType", "org.ohnlp.medtagger.type.Context");
    jcasType.ll_cas.ll_setStringValue(addr, ((Context_Type)jcasType).casFeatCode_contextType, v);}    
  }

    