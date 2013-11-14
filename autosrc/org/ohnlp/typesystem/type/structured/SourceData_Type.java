
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.structured;

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

/** Information about the caregiving setting for the clinical document.  Typically comes from structured metadata.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class SourceData_Type extends TOP_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SourceData_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SourceData_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SourceData(addr, SourceData_Type.this);
  			   SourceData_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SourceData(addr, SourceData_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SourceData.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.structured.SourceData");
 
  /** @generated */
  final Feature casFeat_noteTypeCode;
  /** @generated */
  final int     casFeatCode_noteTypeCode;
  /** @generated */ 
  public String getNoteTypeCode(int addr) {
        if (featOkTst && casFeat_noteTypeCode == null)
      jcas.throwFeatMissing("noteTypeCode", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_noteTypeCode);
  }
  /** @generated */    
  public void setNoteTypeCode(int addr, String v) {
        if (featOkTst && casFeat_noteTypeCode == null)
      jcas.throwFeatMissing("noteTypeCode", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_noteTypeCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_noteSubTypeCode;
  /** @generated */
  final int     casFeatCode_noteSubTypeCode;
  /** @generated */ 
  public String getNoteSubTypeCode(int addr) {
        if (featOkTst && casFeat_noteSubTypeCode == null)
      jcas.throwFeatMissing("noteSubTypeCode", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_noteSubTypeCode);
  }
  /** @generated */    
  public void setNoteSubTypeCode(int addr, String v) {
        if (featOkTst && casFeat_noteSubTypeCode == null)
      jcas.throwFeatMissing("noteSubTypeCode", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_noteSubTypeCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_authorSpecialty;
  /** @generated */
  final int     casFeatCode_authorSpecialty;
  /** @generated */ 
  public String getAuthorSpecialty(int addr) {
        if (featOkTst && casFeat_authorSpecialty == null)
      jcas.throwFeatMissing("authorSpecialty", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_authorSpecialty);
  }
  /** @generated */    
  public void setAuthorSpecialty(int addr, String v) {
        if (featOkTst && casFeat_authorSpecialty == null)
      jcas.throwFeatMissing("authorSpecialty", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_authorSpecialty, v);}
    
  
 
  /** @generated */
  final Feature casFeat_documentStandard;
  /** @generated */
  final int     casFeatCode_documentStandard;
  /** @generated */ 
  public String getDocumentStandard(int addr) {
        if (featOkTst && casFeat_documentStandard == null)
      jcas.throwFeatMissing("documentStandard", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_documentStandard);
  }
  /** @generated */    
  public void setDocumentStandard(int addr, String v) {
        if (featOkTst && casFeat_documentStandard == null)
      jcas.throwFeatMissing("documentStandard", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_documentStandard, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceInstanceId;
  /** @generated */
  final int     casFeatCode_sourceInstanceId;
  /** @generated */ 
  public String getSourceInstanceId(int addr) {
        if (featOkTst && casFeat_sourceInstanceId == null)
      jcas.throwFeatMissing("sourceInstanceId", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceInstanceId);
  }
  /** @generated */    
  public void setSourceInstanceId(int addr, String v) {
        if (featOkTst && casFeat_sourceInstanceId == null)
      jcas.throwFeatMissing("sourceInstanceId", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceInstanceId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceRevisionNbr;
  /** @generated */
  final int     casFeatCode_sourceRevisionNbr;
  /** @generated */ 
  public int getSourceRevisionNbr(int addr) {
        if (featOkTst && casFeat_sourceRevisionNbr == null)
      jcas.throwFeatMissing("sourceRevisionNbr", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getIntValue(addr, casFeatCode_sourceRevisionNbr);
  }
  /** @generated */    
  public void setSourceRevisionNbr(int addr, int v) {
        if (featOkTst && casFeat_sourceRevisionNbr == null)
      jcas.throwFeatMissing("sourceRevisionNbr", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setIntValue(addr, casFeatCode_sourceRevisionNbr, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceRevisionDate;
  /** @generated */
  final int     casFeatCode_sourceRevisionDate;
  /** @generated */ 
  public String getSourceRevisionDate(int addr) {
        if (featOkTst && casFeat_sourceRevisionDate == null)
      jcas.throwFeatMissing("sourceRevisionDate", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceRevisionDate);
  }
  /** @generated */    
  public void setSourceRevisionDate(int addr, String v) {
        if (featOkTst && casFeat_sourceRevisionDate == null)
      jcas.throwFeatMissing("sourceRevisionDate", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceRevisionDate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceOriginalDate;
  /** @generated */
  final int     casFeatCode_sourceOriginalDate;
  /** @generated */ 
  public String getSourceOriginalDate(int addr) {
        if (featOkTst && casFeat_sourceOriginalDate == null)
      jcas.throwFeatMissing("sourceOriginalDate", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceOriginalDate);
  }
  /** @generated */    
  public void setSourceOriginalDate(int addr, String v) {
        if (featOkTst && casFeat_sourceOriginalDate == null)
      jcas.throwFeatMissing("sourceOriginalDate", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceOriginalDate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceInstitution;
  /** @generated */
  final int     casFeatCode_sourceInstitution;
  /** @generated */ 
  public String getSourceInstitution(int addr) {
        if (featOkTst && casFeat_sourceInstitution == null)
      jcas.throwFeatMissing("sourceInstitution", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceInstitution);
  }
  /** @generated */    
  public void setSourceInstitution(int addr, String v) {
        if (featOkTst && casFeat_sourceInstitution == null)
      jcas.throwFeatMissing("sourceInstitution", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceInstitution, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceEncounterId;
  /** @generated */
  final int     casFeatCode_sourceEncounterId;
  /** @generated */ 
  public String getSourceEncounterId(int addr) {
        if (featOkTst && casFeat_sourceEncounterId == null)
      jcas.throwFeatMissing("sourceEncounterId", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceEncounterId);
  }
  /** @generated */    
  public void setSourceEncounterId(int addr, String v) {
        if (featOkTst && casFeat_sourceEncounterId == null)
      jcas.throwFeatMissing("sourceEncounterId", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceEncounterId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceApplication;
  /** @generated */
  final int     casFeatCode_sourceApplication;
  /** @generated */ 
  public String getSourceApplication(int addr) {
        if (featOkTst && casFeat_sourceApplication == null)
      jcas.throwFeatMissing("sourceApplication", "org.ohnlp.typesystem.type.structured.SourceData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceApplication);
  }
  /** @generated */    
  public void setSourceApplication(int addr, String v) {
        if (featOkTst && casFeat_sourceApplication == null)
      jcas.throwFeatMissing("sourceApplication", "org.ohnlp.typesystem.type.structured.SourceData");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceApplication, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public SourceData_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_noteTypeCode = jcas.getRequiredFeatureDE(casType, "noteTypeCode", "uima.cas.String", featOkTst);
    casFeatCode_noteTypeCode  = (null == casFeat_noteTypeCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_noteTypeCode).getCode();

 
    casFeat_noteSubTypeCode = jcas.getRequiredFeatureDE(casType, "noteSubTypeCode", "uima.cas.String", featOkTst);
    casFeatCode_noteSubTypeCode  = (null == casFeat_noteSubTypeCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_noteSubTypeCode).getCode();

 
    casFeat_authorSpecialty = jcas.getRequiredFeatureDE(casType, "authorSpecialty", "uima.cas.String", featOkTst);
    casFeatCode_authorSpecialty  = (null == casFeat_authorSpecialty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_authorSpecialty).getCode();

 
    casFeat_documentStandard = jcas.getRequiredFeatureDE(casType, "documentStandard", "uima.cas.String", featOkTst);
    casFeatCode_documentStandard  = (null == casFeat_documentStandard) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_documentStandard).getCode();

 
    casFeat_sourceInstanceId = jcas.getRequiredFeatureDE(casType, "sourceInstanceId", "uima.cas.String", featOkTst);
    casFeatCode_sourceInstanceId  = (null == casFeat_sourceInstanceId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceInstanceId).getCode();

 
    casFeat_sourceRevisionNbr = jcas.getRequiredFeatureDE(casType, "sourceRevisionNbr", "uima.cas.Integer", featOkTst);
    casFeatCode_sourceRevisionNbr  = (null == casFeat_sourceRevisionNbr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceRevisionNbr).getCode();

 
    casFeat_sourceRevisionDate = jcas.getRequiredFeatureDE(casType, "sourceRevisionDate", "uima.cas.String", featOkTst);
    casFeatCode_sourceRevisionDate  = (null == casFeat_sourceRevisionDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceRevisionDate).getCode();

 
    casFeat_sourceOriginalDate = jcas.getRequiredFeatureDE(casType, "sourceOriginalDate", "uima.cas.String", featOkTst);
    casFeatCode_sourceOriginalDate  = (null == casFeat_sourceOriginalDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceOriginalDate).getCode();

 
    casFeat_sourceInstitution = jcas.getRequiredFeatureDE(casType, "sourceInstitution", "uima.cas.String", featOkTst);
    casFeatCode_sourceInstitution  = (null == casFeat_sourceInstitution) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceInstitution).getCode();

 
    casFeat_sourceEncounterId = jcas.getRequiredFeatureDE(casType, "sourceEncounterId", "uima.cas.String", featOkTst);
    casFeatCode_sourceEncounterId  = (null == casFeat_sourceEncounterId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceEncounterId).getCode();

 
    casFeat_sourceApplication = jcas.getRequiredFeatureDE(casType, "sourceApplication", "uima.cas.String", featOkTst);
    casFeatCode_sourceApplication  = (null == casFeat_sourceApplication) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceApplication).getCode();

  }
}



    