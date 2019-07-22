

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.util;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** An Attribute-Value tuple.
Equivalent to Mayo cTAKES version 2.5: edu.mayo.bmi.uima.core.type.Property
 * Updated by JCasGen Wed Oct 30 16:30:50 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class Pair extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Pair.class);
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
  protected Pair() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Pair(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Pair(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: attribute

  /** getter for attribute - gets 
   * @generated */
  public String getAttribute() {
    if (Pair_Type.featOkTst && ((Pair_Type)jcasType).casFeat_attribute == null)
      jcasType.jcas.throwFeatMissing("attribute", "org.ohnlp.typesystem.type.util.Pair");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Pair_Type)jcasType).casFeatCode_attribute);}
    
  /** setter for attribute - sets  
   * @generated */
  public void setAttribute(String v) {
    if (Pair_Type.featOkTst && ((Pair_Type)jcasType).casFeat_attribute == null)
      jcasType.jcas.throwFeatMissing("attribute", "org.ohnlp.typesystem.type.util.Pair");
    jcasType.ll_cas.ll_setStringValue(addr, ((Pair_Type)jcasType).casFeatCode_attribute, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets 
   * @generated */
  public String getValue() {
    if (Pair_Type.featOkTst && ((Pair_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.util.Pair");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Pair_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated */
  public void setValue(String v) {
    if (Pair_Type.featOkTst && ((Pair_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "org.ohnlp.typesystem.type.util.Pair");
    jcasType.ll_cas.ll_setStringValue(addr, ((Pair_Type)jcasType).casFeatCode_value, v);}    
  }

    