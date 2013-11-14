
/* First created by JCasGen Wed Oct 30 16:30:48 CDT 2013 */
package org.ohnlp.medtagger.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Stores the context matches
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * @generated */
public class Context_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Context_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Context_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Context(addr, Context_Type.this);
  			   Context_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Context(addr, Context_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Context.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.type.Context");
 
  /** @generated */
  final Feature casFeat_position;
  /** @generated */
  final int     casFeatCode_position;
  /** @generated */ 
  public String getPosition(int addr) {
        if (featOkTst && casFeat_position == null)
      jcas.throwFeatMissing("position", "org.ohnlp.medtagger.type.Context");
    return ll_cas.ll_getStringValue(addr, casFeatCode_position);
  }
  /** @generated */    
  public void setPosition(int addr, String v) {
        if (featOkTst && casFeat_position == null)
      jcas.throwFeatMissing("position", "org.ohnlp.medtagger.type.Context");
    ll_cas.ll_setStringValue(addr, casFeatCode_position, v);}
    
  
 
  /** @generated */
  final Feature casFeat_contextType;
  /** @generated */
  final int     casFeatCode_contextType;
  /** @generated */ 
  public String getContextType(int addr) {
        if (featOkTst && casFeat_contextType == null)
      jcas.throwFeatMissing("contextType", "org.ohnlp.medtagger.type.Context");
    return ll_cas.ll_getStringValue(addr, casFeatCode_contextType);
  }
  /** @generated */    
  public void setContextType(int addr, String v) {
        if (featOkTst && casFeat_contextType == null)
      jcas.throwFeatMissing("contextType", "org.ohnlp.medtagger.type.Context");
    ll_cas.ll_setStringValue(addr, casFeatCode_contextType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Context_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_position = jcas.getRequiredFeatureDE(casType, "position", "uima.cas.String", featOkTst);
    casFeatCode_position  = (null == casFeat_position) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_position).getCode();

 
    casFeat_contextType = jcas.getRequiredFeatureDE(casType, "contextType", "uima.cas.String", featOkTst);
    casFeatCode_contextType  = (null == casFeat_contextType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_contextType).getCode();

  }
}



    