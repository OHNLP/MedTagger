
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.syntax;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** A Penn Treebank Node; as the top node, this stores the whole subsumed sentence's parse tree as a string.  It also stores all the terminals, allowing for traversal of the tree bottom-up (top-down is implied through the TreebankNode's 'children' attribute).
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class TopTreebankNode_Type extends TreebankNode_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TopTreebankNode_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TopTreebankNode_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TopTreebankNode(addr, TopTreebankNode_Type.this);
  			   TopTreebankNode_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TopTreebankNode(addr, TopTreebankNode_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TopTreebankNode.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.syntax.TopTreebankNode");
 
  /** @generated */
  final Feature casFeat_treebankParse;
  /** @generated */
  final int     casFeatCode_treebankParse;
  /** @generated */ 
  public String getTreebankParse(int addr) {
        if (featOkTst && casFeat_treebankParse == null)
      jcas.throwFeatMissing("treebankParse", "org.ohnlp.typesystem.type.syntax.TopTreebankNode");
    return ll_cas.ll_getStringValue(addr, casFeatCode_treebankParse);
  }
  /** @generated */    
  public void setTreebankParse(int addr, String v) {
        if (featOkTst && casFeat_treebankParse == null)
      jcas.throwFeatMissing("treebankParse", "org.ohnlp.typesystem.type.syntax.TopTreebankNode");
    ll_cas.ll_setStringValue(addr, casFeatCode_treebankParse, v);}
    
  
 
  /** @generated */
  final Feature casFeat_terminals;
  /** @generated */
  final int     casFeatCode_terminals;
  /** @generated */ 
  public int getTerminals(int addr) {
        if (featOkTst && casFeat_terminals == null)
      jcas.throwFeatMissing("terminals", "org.ohnlp.typesystem.type.syntax.TopTreebankNode");
    return ll_cas.ll_getRefValue(addr, casFeatCode_terminals);
  }
  /** @generated */    
  public void setTerminals(int addr, int v) {
        if (featOkTst && casFeat_terminals == null)
      jcas.throwFeatMissing("terminals", "org.ohnlp.typesystem.type.syntax.TopTreebankNode");
    ll_cas.ll_setRefValue(addr, casFeatCode_terminals, v);}
    
   /** @generated */
  public int getTerminals(int addr, int i) {
        if (featOkTst && casFeat_terminals == null)
      jcas.throwFeatMissing("terminals", "org.ohnlp.typesystem.type.syntax.TopTreebankNode");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_terminals), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_terminals), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_terminals), i);
  }
   
  /** @generated */ 
  public void setTerminals(int addr, int i, int v) {
        if (featOkTst && casFeat_terminals == null)
      jcas.throwFeatMissing("terminals", "org.ohnlp.typesystem.type.syntax.TopTreebankNode");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_terminals), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_terminals), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_terminals), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public TopTreebankNode_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_treebankParse = jcas.getRequiredFeatureDE(casType, "treebankParse", "uima.cas.String", featOkTst);
    casFeatCode_treebankParse  = (null == casFeat_treebankParse) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_treebankParse).getCode();

 
    casFeat_terminals = jcas.getRequiredFeatureDE(casType, "terminals", "uima.cas.FSArray", featOkTst);
    casFeatCode_terminals  = (null == casFeat_terminals) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_terminals).getCode();

  }
}



    