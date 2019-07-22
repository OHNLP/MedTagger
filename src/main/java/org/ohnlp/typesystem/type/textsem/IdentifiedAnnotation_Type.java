
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Any span of text that has been discovered or flagged for some reason, such as a Named Entity.  Allows for mapping to an ontology.  Generalized from Mayo cTAKES version 2.5: org.ohnlp.typesystem.type.IdentifiedAnnotation.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class IdentifiedAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (IdentifiedAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = IdentifiedAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new IdentifiedAnnotation(addr, IdentifiedAnnotation_Type.this);
  			   IdentifiedAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new IdentifiedAnnotation(addr, IdentifiedAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = IdentifiedAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public int getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, int v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setIntValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ontologyConceptArr;
  /** @generated */
  final int     casFeatCode_ontologyConceptArr;
  /** @generated */ 
  public int getOntologyConceptArr(int addr) {
        if (featOkTst && casFeat_ontologyConceptArr == null)
      jcas.throwFeatMissing("ontologyConceptArr", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr);
  }
  /** @generated */    
  public void setOntologyConceptArr(int addr, int v) {
        if (featOkTst && casFeat_ontologyConceptArr == null)
      jcas.throwFeatMissing("ontologyConceptArr", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_ontologyConceptArr, v);}
    
   /** @generated */
  public int getOntologyConceptArr(int addr, int i) {
        if (featOkTst && casFeat_ontologyConceptArr == null)
      jcas.throwFeatMissing("ontologyConceptArr", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr), i);
  }
   
  /** @generated */ 
  public void setOntologyConceptArr(int addr, int i, int v) {
        if (featOkTst && casFeat_ontologyConceptArr == null)
      jcas.throwFeatMissing("ontologyConceptArr", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConceptArr), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_typeID;
  /** @generated */
  final int     casFeatCode_typeID;
  /** @generated */ 
  public int getTypeID(int addr) {
        if (featOkTst && casFeat_typeID == null)
      jcas.throwFeatMissing("typeID", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_typeID);
  }
  /** @generated */    
  public void setTypeID(int addr, int v) {
        if (featOkTst && casFeat_typeID == null)
      jcas.throwFeatMissing("typeID", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setIntValue(addr, casFeatCode_typeID, v);}
    
  
 
  /** @generated */
  final Feature casFeat_segmentID;
  /** @generated */
  final int     casFeatCode_segmentID;
  /** @generated */ 
  public String getSegmentID(int addr) {
        if (featOkTst && casFeat_segmentID == null)
      jcas.throwFeatMissing("segmentID", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_segmentID);
  }
  /** @generated */    
  public void setSegmentID(int addr, String v) {
        if (featOkTst && casFeat_segmentID == null)
      jcas.throwFeatMissing("segmentID", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_segmentID, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentenceID;
  /** @generated */
  final int     casFeatCode_sentenceID;
  /** @generated */ 
  public String getSentenceID(int addr) {
        if (featOkTst && casFeat_sentenceID == null)
      jcas.throwFeatMissing("sentenceID", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sentenceID);
  }
  /** @generated */    
  public void setSentenceID(int addr, String v) {
        if (featOkTst && casFeat_sentenceID == null)
      jcas.throwFeatMissing("sentenceID", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_sentenceID, v);}
    
  
 
  /** @generated */
  final Feature casFeat_discoveryTechnique;
  /** @generated */
  final int     casFeatCode_discoveryTechnique;
  /** @generated */ 
  public int getDiscoveryTechnique(int addr) {
        if (featOkTst && casFeat_discoveryTechnique == null)
      jcas.throwFeatMissing("discoveryTechnique", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_discoveryTechnique);
  }
  /** @generated */    
  public void setDiscoveryTechnique(int addr, int v) {
        if (featOkTst && casFeat_discoveryTechnique == null)
      jcas.throwFeatMissing("discoveryTechnique", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setIntValue(addr, casFeatCode_discoveryTechnique, v);}
    
  
 
  /** @generated */
  final Feature casFeat_confidence;
  /** @generated */
  final int     casFeatCode_confidence;
  /** @generated */ 
  public float getConfidence(int addr) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_confidence);
  }
  /** @generated */    
  public void setConfidence(int addr, float v) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setFloatValue(addr, casFeatCode_confidence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_polarity;
  /** @generated */
  final int     casFeatCode_polarity;
  /** @generated */ 
  public int getPolarity(int addr) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_polarity);
  }
  /** @generated */    
  public void setPolarity(int addr, int v) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setIntValue(addr, casFeatCode_polarity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_uncertainty;
  /** @generated */
  final int     casFeatCode_uncertainty;
  /** @generated */ 
  public int getUncertainty(int addr) {
        if (featOkTst && casFeat_uncertainty == null)
      jcas.throwFeatMissing("uncertainty", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_uncertainty);
  }
  /** @generated */    
  public void setUncertainty(int addr, int v) {
        if (featOkTst && casFeat_uncertainty == null)
      jcas.throwFeatMissing("uncertainty", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setIntValue(addr, casFeatCode_uncertainty, v);}
    
  
 
  /** @generated */
  final Feature casFeat_conditional;
  /** @generated */
  final int     casFeatCode_conditional;
  /** @generated */ 
  public boolean getConditional(int addr) {
        if (featOkTst && casFeat_conditional == null)
      jcas.throwFeatMissing("conditional", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_conditional);
  }
  /** @generated */    
  public void setConditional(int addr, boolean v) {
        if (featOkTst && casFeat_conditional == null)
      jcas.throwFeatMissing("conditional", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_conditional, v);}
    
  
 
  /** @generated */
  final Feature casFeat_generic;
  /** @generated */
  final int     casFeatCode_generic;
  /** @generated */ 
  public boolean getGeneric(int addr) {
        if (featOkTst && casFeat_generic == null)
      jcas.throwFeatMissing("generic", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_generic);
  }
  /** @generated */    
  public void setGeneric(int addr, boolean v) {
        if (featOkTst && casFeat_generic == null)
      jcas.throwFeatMissing("generic", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_generic, v);}
    
  
 
  /** @generated */
  final Feature casFeat_subject;
  /** @generated */
  final int     casFeatCode_subject;
  /** @generated */ 
  public String getSubject(int addr) {
        if (featOkTst && casFeat_subject == null)
      jcas.throwFeatMissing("subject", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_subject);
  }
  /** @generated */    
  public void setSubject(int addr, String v) {
        if (featOkTst && casFeat_subject == null)
      jcas.throwFeatMissing("subject", "org.ohnlp.typesystem.type.textsem.IdentifiedAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_subject, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public IdentifiedAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.Integer", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_ontologyConceptArr = jcas.getRequiredFeatureDE(casType, "ontologyConceptArr", "uima.cas.FSArray", featOkTst);
    casFeatCode_ontologyConceptArr  = (null == casFeat_ontologyConceptArr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ontologyConceptArr).getCode();

 
    casFeat_typeID = jcas.getRequiredFeatureDE(casType, "typeID", "uima.cas.Integer", featOkTst);
    casFeatCode_typeID  = (null == casFeat_typeID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_typeID).getCode();

 
    casFeat_segmentID = jcas.getRequiredFeatureDE(casType, "segmentID", "uima.cas.String", featOkTst);
    casFeatCode_segmentID  = (null == casFeat_segmentID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_segmentID).getCode();

 
    casFeat_sentenceID = jcas.getRequiredFeatureDE(casType, "sentenceID", "uima.cas.String", featOkTst);
    casFeatCode_sentenceID  = (null == casFeat_sentenceID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentenceID).getCode();

 
    casFeat_discoveryTechnique = jcas.getRequiredFeatureDE(casType, "discoveryTechnique", "uima.cas.Integer", featOkTst);
    casFeatCode_discoveryTechnique  = (null == casFeat_discoveryTechnique) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_discoveryTechnique).getCode();

 
    casFeat_confidence = jcas.getRequiredFeatureDE(casType, "confidence", "uima.cas.Float", featOkTst);
    casFeatCode_confidence  = (null == casFeat_confidence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_confidence).getCode();

 
    casFeat_polarity = jcas.getRequiredFeatureDE(casType, "polarity", "uima.cas.Integer", featOkTst);
    casFeatCode_polarity  = (null == casFeat_polarity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_polarity).getCode();

 
    casFeat_uncertainty = jcas.getRequiredFeatureDE(casType, "uncertainty", "uima.cas.Integer", featOkTst);
    casFeatCode_uncertainty  = (null == casFeat_uncertainty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_uncertainty).getCode();

 
    casFeat_conditional = jcas.getRequiredFeatureDE(casType, "conditional", "uima.cas.Boolean", featOkTst);
    casFeatCode_conditional  = (null == casFeat_conditional) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_conditional).getCode();

 
    casFeat_generic = jcas.getRequiredFeatureDE(casType, "generic", "uima.cas.Boolean", featOkTst);
    casFeatCode_generic  = (null == casFeat_generic) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_generic).getCode();

 
    casFeat_subject = jcas.getRequiredFeatureDE(casType, "subject", "uima.cas.String", featOkTst);
    casFeatCode_subject  = (null == casFeat_subject) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_subject).getCode();

  }
}



    