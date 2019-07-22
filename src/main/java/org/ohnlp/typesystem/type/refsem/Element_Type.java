
/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.typesystem.type.refsem;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.cas.TOP_Type;

/** A basic semantic unit that refers to something in the real world, including Entities, Events, Attributes, Dates.  Element inherits from uima.cas.TOP to combine textual mentions of these real-world objects.
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * @generated */
public class Element_Type extends TOP_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Element_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Element_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Element(addr, Element_Type.this);
  			   Element_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Element(addr, Element_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Element.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.refsem.Element");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public int getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getIntValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, int v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setIntValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ontologyConcept;
  /** @generated */
  final int     casFeatCode_ontologyConcept;
  /** @generated */ 
  public int getOntologyConcept(int addr) {
        if (featOkTst && casFeat_ontologyConcept == null)
      jcas.throwFeatMissing("ontologyConcept", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ontologyConcept);
  }
  /** @generated */    
  public void setOntologyConcept(int addr, int v) {
        if (featOkTst && casFeat_ontologyConcept == null)
      jcas.throwFeatMissing("ontologyConcept", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setRefValue(addr, casFeatCode_ontologyConcept, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mentions;
  /** @generated */
  final int     casFeatCode_mentions;
  /** @generated */ 
  public int getMentions(int addr) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getRefValue(addr, casFeatCode_mentions);
  }
  /** @generated */    
  public void setMentions(int addr, int v) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setRefValue(addr, casFeatCode_mentions, v);}
    
   /** @generated */
  public int getMentions(int addr, int i) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "org.ohnlp.typesystem.type.refsem.Element");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i);
  }
   
  /** @generated */ 
  public void setMentions(int addr, int i, int v) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "org.ohnlp.typesystem.type.refsem.Element");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_discoveryTechnique;
  /** @generated */
  final int     casFeatCode_discoveryTechnique;
  /** @generated */ 
  public int getDiscoveryTechnique(int addr) {
        if (featOkTst && casFeat_discoveryTechnique == null)
      jcas.throwFeatMissing("discoveryTechnique", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getIntValue(addr, casFeatCode_discoveryTechnique);
  }
  /** @generated */    
  public void setDiscoveryTechnique(int addr, int v) {
        if (featOkTst && casFeat_discoveryTechnique == null)
      jcas.throwFeatMissing("discoveryTechnique", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setIntValue(addr, casFeatCode_discoveryTechnique, v);}
    
  
 
  /** @generated */
  final Feature casFeat_confidence;
  /** @generated */
  final int     casFeatCode_confidence;
  /** @generated */ 
  public double getConfidence(int addr) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_confidence);
  }
  /** @generated */    
  public void setConfidence(int addr, double v) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_confidence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_conditional;
  /** @generated */
  final int     casFeatCode_conditional;
  /** @generated */ 
  public boolean getConditional(int addr) {
        if (featOkTst && casFeat_conditional == null)
      jcas.throwFeatMissing("conditional", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_conditional);
  }
  /** @generated */    
  public void setConditional(int addr, boolean v) {
        if (featOkTst && casFeat_conditional == null)
      jcas.throwFeatMissing("conditional", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_conditional, v);}
    
  
 
  /** @generated */
  final Feature casFeat_generic;
  /** @generated */
  final int     casFeatCode_generic;
  /** @generated */ 
  public boolean getGeneric(int addr) {
        if (featOkTst && casFeat_generic == null)
      jcas.throwFeatMissing("generic", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_generic);
  }
  /** @generated */    
  public void setGeneric(int addr, boolean v) {
        if (featOkTst && casFeat_generic == null)
      jcas.throwFeatMissing("generic", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_generic, v);}
    
  
 
  /** @generated */
  final Feature casFeat_subject;
  /** @generated */
  final int     casFeatCode_subject;
  /** @generated */ 
  public String getSubject(int addr) {
        if (featOkTst && casFeat_subject == null)
      jcas.throwFeatMissing("subject", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getStringValue(addr, casFeatCode_subject);
  }
  /** @generated */    
  public void setSubject(int addr, String v) {
        if (featOkTst && casFeat_subject == null)
      jcas.throwFeatMissing("subject", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setStringValue(addr, casFeatCode_subject, v);}
    
  
 
  /** @generated */
  final Feature casFeat_polarity;
  /** @generated */
  final int     casFeatCode_polarity;
  /** @generated */ 
  public int getPolarity(int addr) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getIntValue(addr, casFeatCode_polarity);
  }
  /** @generated */    
  public void setPolarity(int addr, int v) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setIntValue(addr, casFeatCode_polarity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_uncertainty;
  /** @generated */
  final int     casFeatCode_uncertainty;
  /** @generated */ 
  public int getUncertainty(int addr) {
        if (featOkTst && casFeat_uncertainty == null)
      jcas.throwFeatMissing("uncertainty", "org.ohnlp.typesystem.type.refsem.Element");
    return ll_cas.ll_getIntValue(addr, casFeatCode_uncertainty);
  }
  /** @generated */    
  public void setUncertainty(int addr, int v) {
        if (featOkTst && casFeat_uncertainty == null)
      jcas.throwFeatMissing("uncertainty", "org.ohnlp.typesystem.type.refsem.Element");
    ll_cas.ll_setIntValue(addr, casFeatCode_uncertainty, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Element_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.Integer", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_ontologyConcept = jcas.getRequiredFeatureDE(casType, "ontologyConcept", "org.ohnlp.typesystem.type.refsem.OntologyConcept", featOkTst);
    casFeatCode_ontologyConcept  = (null == casFeat_ontologyConcept) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ontologyConcept).getCode();

 
    casFeat_mentions = jcas.getRequiredFeatureDE(casType, "mentions", "uima.cas.FSArray", featOkTst);
    casFeatCode_mentions  = (null == casFeat_mentions) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mentions).getCode();

 
    casFeat_discoveryTechnique = jcas.getRequiredFeatureDE(casType, "discoveryTechnique", "uima.cas.Integer", featOkTst);
    casFeatCode_discoveryTechnique  = (null == casFeat_discoveryTechnique) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_discoveryTechnique).getCode();

 
    casFeat_confidence = jcas.getRequiredFeatureDE(casType, "confidence", "uima.cas.Double", featOkTst);
    casFeatCode_confidence  = (null == casFeat_confidence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_confidence).getCode();

 
    casFeat_conditional = jcas.getRequiredFeatureDE(casType, "conditional", "uima.cas.Boolean", featOkTst);
    casFeatCode_conditional  = (null == casFeat_conditional) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_conditional).getCode();

 
    casFeat_generic = jcas.getRequiredFeatureDE(casType, "generic", "uima.cas.Boolean", featOkTst);
    casFeatCode_generic  = (null == casFeat_generic) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_generic).getCode();

 
    casFeat_subject = jcas.getRequiredFeatureDE(casType, "subject", "uima.cas.String", featOkTst);
    casFeatCode_subject  = (null == casFeat_subject) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_subject).getCode();

 
    casFeat_polarity = jcas.getRequiredFeatureDE(casType, "polarity", "uima.cas.Integer", featOkTst);
    casFeatCode_polarity  = (null == casFeat_polarity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_polarity).getCode();

 
    casFeat_uncertainty = jcas.getRequiredFeatureDE(casType, "uncertainty", "uima.cas.Integer", featOkTst);
    casFeatCode_uncertainty  = (null == casFeat_uncertainty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_uncertainty).getCode();

  }
}



    