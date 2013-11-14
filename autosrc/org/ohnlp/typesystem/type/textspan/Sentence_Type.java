
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.textspan;

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

/** Sentences may or may not be fully-formed and grammatical.  They form the unit of analysis for some downstream components (e.g., constituency parsers).
Equivalent to Mayo cTAKES version 2.5: edu.mayo.bmi.uima.core.type.Sentence
 * Updated by JCasGen Wed Oct 30 16:30:50 CDT 2013
 * @generated */
public class Sentence_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Sentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Sentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Sentence(addr, Sentence_Type.this);
  			   Sentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Sentence(addr, Sentence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Sentence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.textspan.Sentence");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.textspan.Sentence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.textspan.Sentence");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_segment;
  /** @generated */
  final int     casFeatCode_segment;
  /** @generated */ 
  public int getSegment(int addr) {
        if (featOkTst && casFeat_segment == null)
      jcas.throwFeatMissing("segment", "org.ohnlp.typesystem.type.textspan.Sentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_segment);
  }
  /** @generated */    
  public void setSegment(int addr, int v) {
        if (featOkTst && casFeat_segment == null)
      jcas.throwFeatMissing("segment", "org.ohnlp.typesystem.type.textspan.Sentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_segment, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Sentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_segment = jcas.getRequiredFeatureDE(casType, "segment", "org.ohnlp.typesystem.type.textspan.Segment", featOkTst);
    casFeatCode_segment  = (null == casFeat_segment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_segment).getCode();

  }
}



    