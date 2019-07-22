
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.textsem;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.ohnlp.typesystem.type.relation.Relation_Type;

/** Predicate-argument structure used for semantic role labeling output.
 * Updated by JCasGen Wed Oct 30 16:30:50 CDT 2013
 * @generated */
public class SemanticRoleRelation_Type extends Relation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SemanticRoleRelation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SemanticRoleRelation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SemanticRoleRelation(addr, SemanticRoleRelation_Type.this);
  			   SemanticRoleRelation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SemanticRoleRelation(addr, SemanticRoleRelation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SemanticRoleRelation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.textsem.SemanticRoleRelation");
 
  /** @generated */
  final Feature casFeat_predicate;
  /** @generated */
  final int     casFeatCode_predicate;
  /** @generated */ 
  public int getPredicate(int addr) {
        if (featOkTst && casFeat_predicate == null)
      jcas.throwFeatMissing("predicate", "org.ohnlp.typesystem.type.textsem.SemanticRoleRelation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_predicate);
  }
  /** @generated */    
  public void setPredicate(int addr, int v) {
        if (featOkTst && casFeat_predicate == null)
      jcas.throwFeatMissing("predicate", "org.ohnlp.typesystem.type.textsem.SemanticRoleRelation");
    ll_cas.ll_setRefValue(addr, casFeatCode_predicate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_argument;
  /** @generated */
  final int     casFeatCode_argument;
  /** @generated */ 
  public int getArgument(int addr) {
        if (featOkTst && casFeat_argument == null)
      jcas.throwFeatMissing("argument", "org.ohnlp.typesystem.type.textsem.SemanticRoleRelation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_argument);
  }
  /** @generated */    
  public void setArgument(int addr, int v) {
        if (featOkTst && casFeat_argument == null)
      jcas.throwFeatMissing("argument", "org.ohnlp.typesystem.type.textsem.SemanticRoleRelation");
    ll_cas.ll_setRefValue(addr, casFeatCode_argument, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public SemanticRoleRelation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_predicate = jcas.getRequiredFeatureDE(casType, "predicate", "org.ohnlp.typesystem.type.textsem.Predicate", featOkTst);
    casFeatCode_predicate  = (null == casFeat_predicate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_predicate).getCode();

 
    casFeat_argument = jcas.getRequiredFeatureDE(casType, "argument", "org.ohnlp.typesystem.type.textsem.SemanticArgument", featOkTst);
    casFeatCode_argument  = (null == casFeat_argument) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_argument).getCode();

  }
}



    