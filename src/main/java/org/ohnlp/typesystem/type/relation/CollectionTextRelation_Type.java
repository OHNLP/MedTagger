
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.relation;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class CollectionTextRelation_Type extends Relation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CollectionTextRelation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CollectionTextRelation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CollectionTextRelation(addr, CollectionTextRelation_Type.this);
  			   CollectionTextRelation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CollectionTextRelation(addr, CollectionTextRelation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CollectionTextRelation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.relation.CollectionTextRelation");
 
  /** @generated */
  final Feature casFeat_members;
  /** @generated */
  final int     casFeatCode_members;
  /** @generated */ 
  public int getMembers(int addr) {
        if (featOkTst && casFeat_members == null)
      jcas.throwFeatMissing("members", "org.ohnlp.typesystem.type.relation.CollectionTextRelation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_members);
  }
  /** @generated */    
  public void setMembers(int addr, int v) {
        if (featOkTst && casFeat_members == null)
      jcas.throwFeatMissing("members", "org.ohnlp.typesystem.type.relation.CollectionTextRelation");
    ll_cas.ll_setRefValue(addr, casFeatCode_members, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public CollectionTextRelation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_members = jcas.getRequiredFeatureDE(casType, "members", "uima.cas.FSList", featOkTst);
    casFeatCode_members  = (null == casFeat_members) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_members).getCode();

  }
}



    