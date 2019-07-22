
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

/** Ontologies (e.g., SNOMED-CT) provide an expert semantic representation for concepts. They typically assign a code to a concept and normalize across various textual representations of that concept.  
IdentifiedAnnotation and Elements may point to these normalized concept representations to indicate clinical concepts.
Equivalent to Mayo cTAKES version 2.5: edu.mayo.bmi.uima.core.type.OntologyConcept
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * @generated */
public class OntologyConcept_Type extends TOP_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (OntologyConcept_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = OntologyConcept_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new OntologyConcept(addr, OntologyConcept_Type.this);
  			   OntologyConcept_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new OntologyConcept(addr, OntologyConcept_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = OntologyConcept.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.refsem.OntologyConcept");
 
  /** @generated */
  final Feature casFeat_codingScheme;
  /** @generated */
  final int     casFeatCode_codingScheme;
  /** @generated */ 
  public String getCodingScheme(int addr) {
        if (featOkTst && casFeat_codingScheme == null)
      jcas.throwFeatMissing("codingScheme", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_codingScheme);
  }
  /** @generated */    
  public void setCodingScheme(int addr, String v) {
        if (featOkTst && casFeat_codingScheme == null)
      jcas.throwFeatMissing("codingScheme", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    ll_cas.ll_setStringValue(addr, casFeatCode_codingScheme, v);}
    
  
 
  /** @generated */
  final Feature casFeat_code;
  /** @generated */
  final int     casFeatCode_code;
  /** @generated */ 
  public String getCode(int addr) {
        if (featOkTst && casFeat_code == null)
      jcas.throwFeatMissing("code", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_code);
  }
  /** @generated */    
  public void setCode(int addr, String v) {
        if (featOkTst && casFeat_code == null)
      jcas.throwFeatMissing("code", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    ll_cas.ll_setStringValue(addr, casFeatCode_code, v);}
    
  
 
  /** @generated */
  final Feature casFeat_oid;
  /** @generated */
  final int     casFeatCode_oid;
  /** @generated */ 
  public String getOid(int addr) {
        if (featOkTst && casFeat_oid == null)
      jcas.throwFeatMissing("oid", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_oid);
  }
  /** @generated */    
  public void setOid(int addr, String v) {
        if (featOkTst && casFeat_oid == null)
      jcas.throwFeatMissing("oid", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    ll_cas.ll_setStringValue(addr, casFeatCode_oid, v);}
    
  
 
  /** @generated */
  final Feature casFeat_oui;
  /** @generated */
  final int     casFeatCode_oui;
  /** @generated */ 
  public String getOui(int addr) {
        if (featOkTst && casFeat_oui == null)
      jcas.throwFeatMissing("oui", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_oui);
  }
  /** @generated */    
  public void setOui(int addr, String v) {
        if (featOkTst && casFeat_oui == null)
      jcas.throwFeatMissing("oui", "org.ohnlp.typesystem.type.refsem.OntologyConcept");
    ll_cas.ll_setStringValue(addr, casFeatCode_oui, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public OntologyConcept_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_codingScheme = jcas.getRequiredFeatureDE(casType, "codingScheme", "uima.cas.String", featOkTst);
    casFeatCode_codingScheme  = (null == casFeat_codingScheme) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_codingScheme).getCode();

 
    casFeat_code = jcas.getRequiredFeatureDE(casType, "code", "uima.cas.String", featOkTst);
    casFeatCode_code  = (null == casFeat_code) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_code).getCode();

 
    casFeat_oid = jcas.getRequiredFeatureDE(casType, "oid", "uima.cas.String", featOkTst);
    casFeatCode_oid  = (null == casFeat_oid) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_oid).getCode();

 
    casFeat_oui = jcas.getRequiredFeatureDE(casType, "oui", "uima.cas.String", featOkTst);
    casFeatCode_oui  = (null == casFeat_oui) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_oui).getCode();

  }
}



    