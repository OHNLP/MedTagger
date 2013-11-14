

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.textspan;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;


/** A semi-structured text span, containing other Annotations (typically Sentences, other Lists, etc).
 * Updated by JCasGen Wed Oct 30 16:30:50 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class List extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(List.class);
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
  protected List() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public List(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public List(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public List(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.textspan.List");
    return jcasType.ll_cas.ll_getStringValue(addr, ((List_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.textspan.List");
    jcasType.ll_cas.ll_setStringValue(addr, ((List_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: items

  /** getter for items - gets 
   * @generated */
  public FSList getItems() {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_items == null)
      jcasType.jcas.throwFeatMissing("items", "org.ohnlp.typesystem.type.textspan.List");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_items)));}
    
  /** setter for items - sets  
   * @generated */
  public void setItems(FSList v) {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_items == null)
      jcasType.jcas.throwFeatMissing("items", "org.ohnlp.typesystem.type.textspan.List");
    jcasType.ll_cas.ll_setRefValue(addr, ((List_Type)jcasType).casFeatCode_items, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    