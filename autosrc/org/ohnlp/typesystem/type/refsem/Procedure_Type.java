
/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
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

/** This is an Event from the UMLS semantic group of Procedures (except that Laboratory procedures are separate).  Based on generic Clinical Element Models (CEMs)
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * @generated */
public class Procedure_Type extends Event_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Procedure_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Procedure_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Procedure(addr, Procedure_Type.this);
  			   Procedure_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Procedure(addr, Procedure_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Procedure.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.refsem.Procedure");
 
  /** @generated */
  final Feature casFeat_bodyLaterality;
  /** @generated */
  final int     casFeatCode_bodyLaterality;
  /** @generated */ 
  public int getBodyLaterality(int addr) {
        if (featOkTst && casFeat_bodyLaterality == null)
      jcas.throwFeatMissing("bodyLaterality", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_bodyLaterality);
  }
  /** @generated */    
  public void setBodyLaterality(int addr, int v) {
        if (featOkTst && casFeat_bodyLaterality == null)
      jcas.throwFeatMissing("bodyLaterality", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_bodyLaterality, v);}
    
  
 
  /** @generated */
  final Feature casFeat_bodySide;
  /** @generated */
  final int     casFeatCode_bodySide;
  /** @generated */ 
  public int getBodySide(int addr) {
        if (featOkTst && casFeat_bodySide == null)
      jcas.throwFeatMissing("bodySide", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_bodySide);
  }
  /** @generated */    
  public void setBodySide(int addr, int v) {
        if (featOkTst && casFeat_bodySide == null)
      jcas.throwFeatMissing("bodySide", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_bodySide, v);}
    
  
 
  /** @generated */
  final Feature casFeat_bodyLocation;
  /** @generated */
  final int     casFeatCode_bodyLocation;
  /** @generated */ 
  public int getBodyLocation(int addr) {
        if (featOkTst && casFeat_bodyLocation == null)
      jcas.throwFeatMissing("bodyLocation", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_bodyLocation);
  }
  /** @generated */    
  public void setBodyLocation(int addr, int v) {
        if (featOkTst && casFeat_bodyLocation == null)
      jcas.throwFeatMissing("bodyLocation", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_bodyLocation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_procedureDevice;
  /** @generated */
  final int     casFeatCode_procedureDevice;
  /** @generated */ 
  public int getProcedureDevice(int addr) {
        if (featOkTst && casFeat_procedureDevice == null)
      jcas.throwFeatMissing("procedureDevice", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_procedureDevice);
  }
  /** @generated */    
  public void setProcedureDevice(int addr, int v) {
        if (featOkTst && casFeat_procedureDevice == null)
      jcas.throwFeatMissing("procedureDevice", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_procedureDevice, v);}
    
  
 
  /** @generated */
  final Feature casFeat_duration;
  /** @generated */
  final int     casFeatCode_duration;
  /** @generated */ 
  public int getDuration(int addr) {
        if (featOkTst && casFeat_duration == null)
      jcas.throwFeatMissing("duration", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_duration);
  }
  /** @generated */    
  public void setDuration(int addr, int v) {
        if (featOkTst && casFeat_duration == null)
      jcas.throwFeatMissing("duration", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_duration, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endTime;
  /** @generated */
  final int     casFeatCode_endTime;
  /** @generated */ 
  public int getEndTime(int addr) {
        if (featOkTst && casFeat_endTime == null)
      jcas.throwFeatMissing("endTime", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_endTime);
  }
  /** @generated */    
  public void setEndTime(int addr, int v) {
        if (featOkTst && casFeat_endTime == null)
      jcas.throwFeatMissing("endTime", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_endTime, v);}
    
  
 
  /** @generated */
  final Feature casFeat_method;
  /** @generated */
  final int     casFeatCode_method;
  /** @generated */ 
  public int getMethod(int addr) {
        if (featOkTst && casFeat_method == null)
      jcas.throwFeatMissing("method", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_method);
  }
  /** @generated */    
  public void setMethod(int addr, int v) {
        if (featOkTst && casFeat_method == null)
      jcas.throwFeatMissing("method", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_method, v);}
    
  
 
  /** @generated */
  final Feature casFeat_startTime;
  /** @generated */
  final int     casFeatCode_startTime;
  /** @generated */ 
  public int getStartTime(int addr) {
        if (featOkTst && casFeat_startTime == null)
      jcas.throwFeatMissing("startTime", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_startTime);
  }
  /** @generated */    
  public void setStartTime(int addr, int v) {
        if (featOkTst && casFeat_startTime == null)
      jcas.throwFeatMissing("startTime", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_startTime, v);}
    
  
 
  /** @generated */
  final Feature casFeat_relativeTemporalContext;
  /** @generated */
  final int     casFeatCode_relativeTemporalContext;
  /** @generated */ 
  public int getRelativeTemporalContext(int addr) {
        if (featOkTst && casFeat_relativeTemporalContext == null)
      jcas.throwFeatMissing("relativeTemporalContext", "org.ohnlp.typesystem.type.refsem.Procedure");
    return ll_cas.ll_getRefValue(addr, casFeatCode_relativeTemporalContext);
  }
  /** @generated */    
  public void setRelativeTemporalContext(int addr, int v) {
        if (featOkTst && casFeat_relativeTemporalContext == null)
      jcas.throwFeatMissing("relativeTemporalContext", "org.ohnlp.typesystem.type.refsem.Procedure");
    ll_cas.ll_setRefValue(addr, casFeatCode_relativeTemporalContext, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Procedure_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_bodyLaterality = jcas.getRequiredFeatureDE(casType, "bodyLaterality", "org.ohnlp.typesystem.type.refsem.BodyLaterality", featOkTst);
    casFeatCode_bodyLaterality  = (null == casFeat_bodyLaterality) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bodyLaterality).getCode();

 
    casFeat_bodySide = jcas.getRequiredFeatureDE(casType, "bodySide", "org.ohnlp.typesystem.type.refsem.BodySide", featOkTst);
    casFeatCode_bodySide  = (null == casFeat_bodySide) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bodySide).getCode();

 
    casFeat_bodyLocation = jcas.getRequiredFeatureDE(casType, "bodyLocation", "org.ohnlp.typesystem.type.relation.LocationOf", featOkTst);
    casFeatCode_bodyLocation  = (null == casFeat_bodyLocation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bodyLocation).getCode();

 
    casFeat_procedureDevice = jcas.getRequiredFeatureDE(casType, "procedureDevice", "org.ohnlp.typesystem.type.refsem.ProcedureDevice", featOkTst);
    casFeatCode_procedureDevice  = (null == casFeat_procedureDevice) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_procedureDevice).getCode();

 
    casFeat_duration = jcas.getRequiredFeatureDE(casType, "duration", "org.ohnlp.typesystem.type.relation.TemporalRelation", featOkTst);
    casFeatCode_duration  = (null == casFeat_duration) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_duration).getCode();

 
    casFeat_endTime = jcas.getRequiredFeatureDE(casType, "endTime", "org.ohnlp.typesystem.type.refsem.Time", featOkTst);
    casFeatCode_endTime  = (null == casFeat_endTime) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endTime).getCode();

 
    casFeat_method = jcas.getRequiredFeatureDE(casType, "method", "org.ohnlp.typesystem.type.refsem.ProcedureMethod", featOkTst);
    casFeatCode_method  = (null == casFeat_method) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_method).getCode();

 
    casFeat_startTime = jcas.getRequiredFeatureDE(casType, "startTime", "org.ohnlp.typesystem.type.refsem.Time", featOkTst);
    casFeatCode_startTime  = (null == casFeat_startTime) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_startTime).getCode();

 
    casFeat_relativeTemporalContext = jcas.getRequiredFeatureDE(casType, "relativeTemporalContext", "org.ohnlp.typesystem.type.relation.TemporalRelation", featOkTst);
    casFeatCode_relativeTemporalContext  = (null == casFeat_relativeTemporalContext) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relativeTemporalContext).getCode();

  }
}



    