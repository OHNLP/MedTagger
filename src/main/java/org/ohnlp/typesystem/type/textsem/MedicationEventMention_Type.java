
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

/** A text string (EventMention and therefore IdentifiedAnnotation) that refers to a (Medication) Event.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class MedicationEventMention_Type extends EventMention_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MedicationEventMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MedicationEventMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MedicationEventMention(addr, MedicationEventMention_Type.this);
  			   MedicationEventMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MedicationEventMention(addr, MedicationEventMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MedicationEventMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.textsem.MedicationEventMention");
 
  /** @generated */
  final Feature casFeat_medicationFrequency;
  /** @generated */
  final int     casFeatCode_medicationFrequency;
  /** @generated */ 
  public int getMedicationFrequency(int addr) {
        if (featOkTst && casFeat_medicationFrequency == null)
      jcas.throwFeatMissing("medicationFrequency", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationFrequency);
  }
  /** @generated */    
  public void setMedicationFrequency(int addr, int v) {
        if (featOkTst && casFeat_medicationFrequency == null)
      jcas.throwFeatMissing("medicationFrequency", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationFrequency, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medicationDuration;
  /** @generated */
  final int     casFeatCode_medicationDuration;
  /** @generated */ 
  public int getMedicationDuration(int addr) {
        if (featOkTst && casFeat_medicationDuration == null)
      jcas.throwFeatMissing("medicationDuration", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationDuration);
  }
  /** @generated */    
  public void setMedicationDuration(int addr, int v) {
        if (featOkTst && casFeat_medicationDuration == null)
      jcas.throwFeatMissing("medicationDuration", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationDuration, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medicationRoute;
  /** @generated */
  final int     casFeatCode_medicationRoute;
  /** @generated */ 
  public int getMedicationRoute(int addr) {
        if (featOkTst && casFeat_medicationRoute == null)
      jcas.throwFeatMissing("medicationRoute", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationRoute);
  }
  /** @generated */    
  public void setMedicationRoute(int addr, int v) {
        if (featOkTst && casFeat_medicationRoute == null)
      jcas.throwFeatMissing("medicationRoute", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationRoute, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medicationStatusChange;
  /** @generated */
  final int     casFeatCode_medicationStatusChange;
  /** @generated */ 
  public int getMedicationStatusChange(int addr) {
        if (featOkTst && casFeat_medicationStatusChange == null)
      jcas.throwFeatMissing("medicationStatusChange", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationStatusChange);
  }
  /** @generated */    
  public void setMedicationStatusChange(int addr, int v) {
        if (featOkTst && casFeat_medicationStatusChange == null)
      jcas.throwFeatMissing("medicationStatusChange", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationStatusChange, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medicationDosage;
  /** @generated */
  final int     casFeatCode_medicationDosage;
  /** @generated */ 
  public int getMedicationDosage(int addr) {
        if (featOkTst && casFeat_medicationDosage == null)
      jcas.throwFeatMissing("medicationDosage", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationDosage);
  }
  /** @generated */    
  public void setMedicationDosage(int addr, int v) {
        if (featOkTst && casFeat_medicationDosage == null)
      jcas.throwFeatMissing("medicationDosage", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationDosage, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medicationStrength;
  /** @generated */
  final int     casFeatCode_medicationStrength;
  /** @generated */ 
  public int getMedicationStrength(int addr) {
        if (featOkTst && casFeat_medicationStrength == null)
      jcas.throwFeatMissing("medicationStrength", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationStrength);
  }
  /** @generated */    
  public void setMedicationStrength(int addr, int v) {
        if (featOkTst && casFeat_medicationStrength == null)
      jcas.throwFeatMissing("medicationStrength", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationStrength, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medicationForm;
  /** @generated */
  final int     casFeatCode_medicationForm;
  /** @generated */ 
  public int getMedicationForm(int addr) {
        if (featOkTst && casFeat_medicationForm == null)
      jcas.throwFeatMissing("medicationForm", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_medicationForm);
  }
  /** @generated */    
  public void setMedicationForm(int addr, int v) {
        if (featOkTst && casFeat_medicationForm == null)
      jcas.throwFeatMissing("medicationForm", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_medicationForm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_startDate;
  /** @generated */
  final int     casFeatCode_startDate;
  /** @generated */ 
  public int getStartDate(int addr) {
        if (featOkTst && casFeat_startDate == null)
      jcas.throwFeatMissing("startDate", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_startDate);
  }
  /** @generated */    
  public void setStartDate(int addr, int v) {
        if (featOkTst && casFeat_startDate == null)
      jcas.throwFeatMissing("startDate", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_startDate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endDate;
  /** @generated */
  final int     casFeatCode_endDate;
  /** @generated */ 
  public int getEndDate(int addr) {
        if (featOkTst && casFeat_endDate == null)
      jcas.throwFeatMissing("endDate", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_endDate);
  }
  /** @generated */    
  public void setEndDate(int addr, int v) {
        if (featOkTst && casFeat_endDate == null)
      jcas.throwFeatMissing("endDate", "org.ohnlp.typesystem.type.textsem.MedicationEventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_endDate, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public MedicationEventMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_medicationFrequency = jcas.getRequiredFeatureDE(casType, "medicationFrequency", "org.ohnlp.typesystem.type.refsem.MedicationFrequency", featOkTst);
    casFeatCode_medicationFrequency  = (null == casFeat_medicationFrequency) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationFrequency).getCode();

 
    casFeat_medicationDuration = jcas.getRequiredFeatureDE(casType, "medicationDuration", "org.ohnlp.typesystem.type.refsem.MedicationDuration", featOkTst);
    casFeatCode_medicationDuration  = (null == casFeat_medicationDuration) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationDuration).getCode();

 
    casFeat_medicationRoute = jcas.getRequiredFeatureDE(casType, "medicationRoute", "org.ohnlp.typesystem.type.refsem.MedicationRoute", featOkTst);
    casFeatCode_medicationRoute  = (null == casFeat_medicationRoute) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationRoute).getCode();

 
    casFeat_medicationStatusChange = jcas.getRequiredFeatureDE(casType, "medicationStatusChange", "org.ohnlp.typesystem.type.refsem.MedicationStatusChange", featOkTst);
    casFeatCode_medicationStatusChange  = (null == casFeat_medicationStatusChange) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationStatusChange).getCode();

 
    casFeat_medicationDosage = jcas.getRequiredFeatureDE(casType, "medicationDosage", "org.ohnlp.typesystem.type.refsem.MedicationDosage", featOkTst);
    casFeatCode_medicationDosage  = (null == casFeat_medicationDosage) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationDosage).getCode();

 
    casFeat_medicationStrength = jcas.getRequiredFeatureDE(casType, "medicationStrength", "org.ohnlp.typesystem.type.refsem.MedicationStrength", featOkTst);
    casFeatCode_medicationStrength  = (null == casFeat_medicationStrength) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationStrength).getCode();

 
    casFeat_medicationForm = jcas.getRequiredFeatureDE(casType, "medicationForm", "org.ohnlp.typesystem.type.refsem.MedicationForm", featOkTst);
    casFeatCode_medicationForm  = (null == casFeat_medicationForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medicationForm).getCode();

 
    casFeat_startDate = jcas.getRequiredFeatureDE(casType, "startDate", "org.ohnlp.typesystem.type.refsem.Date", featOkTst);
    casFeatCode_startDate  = (null == casFeat_startDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_startDate).getCode();

 
    casFeat_endDate = jcas.getRequiredFeatureDE(casType, "endDate", "org.ohnlp.typesystem.type.refsem.Date", featOkTst);
    casFeatCode_endDate  = (null == casFeat_endDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endDate).getCode();

  }
}



    