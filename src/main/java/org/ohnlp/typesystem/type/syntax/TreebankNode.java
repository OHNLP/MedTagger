

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.syntax;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.StringArray;


/** A Penn Treebank-style tree node.  Conforms to PTB 2.0 guidelines.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class TreebankNode extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TreebankNode.class);
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
  protected TreebankNode() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TreebankNode(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TreebankNode(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TreebankNode(JCas jcas, int begin, int end) {
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
  //* Feature: nodeType

  /** getter for nodeType - gets 
   * @generated */
  public String getNodeType() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeType == null)
      jcasType.jcas.throwFeatMissing("nodeType", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeType);}
    
  /** setter for nodeType - sets  
   * @generated */
  public void setNodeType(String v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeType == null)
      jcasType.jcas.throwFeatMissing("nodeType", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setStringValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeType, v);}    
   
    
  //*--------------*
  //* Feature: nodeValue

  /** getter for nodeValue - gets 
   * @generated */
  public String getNodeValue() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeValue == null)
      jcasType.jcas.throwFeatMissing("nodeValue", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeValue);}
    
  /** setter for nodeValue - sets  
   * @generated */
  public void setNodeValue(String v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeValue == null)
      jcasType.jcas.throwFeatMissing("nodeValue", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setStringValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeValue, v);}    
   
    
  //*--------------*
  //* Feature: leaf

  /** getter for leaf - gets 
   * @generated */
  public boolean getLeaf() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_leaf == null)
      jcasType.jcas.throwFeatMissing("leaf", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_leaf);}
    
  /** setter for leaf - sets  
   * @generated */
  public void setLeaf(boolean v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_leaf == null)
      jcasType.jcas.throwFeatMissing("leaf", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_leaf, v);}    
   
    
  //*--------------*
  //* Feature: parent

  /** getter for parent - gets 
   * @generated */
  public TreebankNode getParent() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return (TreebankNode)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_parent)));}
    
  /** setter for parent - sets  
   * @generated */
  public void setParent(TreebankNode v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_parent, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: children

  /** getter for children - gets 
   * @generated */
  public FSArray getChildren() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_children == null)
      jcasType.jcas.throwFeatMissing("children", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_children)));}
    
  /** setter for children - sets  
   * @generated */
  public void setChildren(FSArray v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_children == null)
      jcasType.jcas.throwFeatMissing("children", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_children, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for children - gets an indexed value - 
   * @generated */
  public TreebankNode getChildren(int i) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_children == null)
      jcasType.jcas.throwFeatMissing("children", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_children), i);
    return (TreebankNode)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_children), i)));}

  /** indexed setter for children - sets an indexed value - 
   * @generated */
  public void setChildren(int i, TreebankNode v) { 
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_children == null)
      jcasType.jcas.throwFeatMissing("children", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_children), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_children), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: nodeTags

  /** getter for nodeTags - gets 
   * @generated */
  public StringArray getNodeTags() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeTags == null)
      jcasType.jcas.throwFeatMissing("nodeTags", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeTags)));}
    
  /** setter for nodeTags - sets  
   * @generated */
  public void setNodeTags(StringArray v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeTags == null)
      jcasType.jcas.throwFeatMissing("nodeTags", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeTags, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for nodeTags - gets an indexed value - 
   * @generated */
  public String getNodeTags(int i) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeTags == null)
      jcasType.jcas.throwFeatMissing("nodeTags", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeTags), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeTags), i);}

  /** indexed setter for nodeTags - sets an indexed value - 
   * @generated */
  public void setNodeTags(int i, String v) { 
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_nodeTags == null)
      jcasType.jcas.throwFeatMissing("nodeTags", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeTags), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_nodeTags), i, v);}
   
    
  //*--------------*
  //* Feature: headIndex

  /** getter for headIndex - gets The head index tracks the index into the word tokens which is the syntactic head of a phrase.
   * @generated */
  public int getHeadIndex() {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_headIndex == null)
      jcasType.jcas.throwFeatMissing("headIndex", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    return jcasType.ll_cas.ll_getIntValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_headIndex);}
    
  /** setter for headIndex - sets The head index tracks the index into the word tokens which is the syntactic head of a phrase. 
   * @generated */
  public void setHeadIndex(int v) {
    if (TreebankNode_Type.featOkTst && ((TreebankNode_Type)jcasType).casFeat_headIndex == null)
      jcasType.jcas.throwFeatMissing("headIndex", "org.ohnlp.typesystem.type.syntax.TreebankNode");
    jcasType.ll_cas.ll_setIntValue(addr, ((TreebankNode_Type)jcasType).casFeatCode_headIndex, v);}    
  }

    