
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

/** Demographic information about the patient in a clinical document.  Typically comes from structured metadata.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * @generated */
public class Demographics_Type extends TOP_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Demographics_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Demographics_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Demographics(addr, Demographics_Type.this);
  			   Demographics_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Demographics(addr, Demographics_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Demographics.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.structured.Demographics");
 
  /** @generated */
  final Feature casFeat_birthDate;
  /** @generated */
  final int     casFeatCode_birthDate;
  /** @generated */ 
  public String getBirthDate(int addr) {
        if (featOkTst && casFeat_birthDate == null)
      jcas.throwFeatMissing("birthDate", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_birthDate);
  }
  /** @generated */    
  public void setBirthDate(int addr, String v) {
        if (featOkTst && casFeat_birthDate == null)
      jcas.throwFeatMissing("birthDate", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_birthDate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_deathDate;
  /** @generated */
  final int     casFeatCode_deathDate;
  /** @generated */ 
  public String getDeathDate(int addr) {
        if (featOkTst && casFeat_deathDate == null)
      jcas.throwFeatMissing("deathDate", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_deathDate);
  }
  /** @generated */    
  public void setDeathDate(int addr, String v) {
        if (featOkTst && casFeat_deathDate == null)
      jcas.throwFeatMissing("deathDate", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_deathDate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_gender;
  /** @generated */
  final int     casFeatCode_gender;
  /** @generated */ 
  public String getGender(int addr) {
        if (featOkTst && casFeat_gender == null)
      jcas.throwFeatMissing("gender", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_gender);
  }
  /** @generated */    
  public void setGender(int addr, String v) {
        if (featOkTst && casFeat_gender == null)
      jcas.throwFeatMissing("gender", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_gender, v);}
    
  
 
  /** @generated */
  final Feature casFeat_firstName;
  /** @generated */
  final int     casFeatCode_firstName;
  /** @generated */ 
  public String getFirstName(int addr) {
        if (featOkTst && casFeat_firstName == null)
      jcas.throwFeatMissing("firstName", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_firstName);
  }
  /** @generated */    
  public void setFirstName(int addr, String v) {
        if (featOkTst && casFeat_firstName == null)
      jcas.throwFeatMissing("firstName", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_firstName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_middleName;
  /** @generated */
  final int     casFeatCode_middleName;
  /** @generated */ 
  public String getMiddleName(int addr) {
        if (featOkTst && casFeat_middleName == null)
      jcas.throwFeatMissing("middleName", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_middleName);
  }
  /** @generated */    
  public void setMiddleName(int addr, String v) {
        if (featOkTst && casFeat_middleName == null)
      jcas.throwFeatMissing("middleName", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_middleName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lastName;
  /** @generated */
  final int     casFeatCode_lastName;
  /** @generated */ 
  public String getLastName(int addr) {
        if (featOkTst && casFeat_lastName == null)
      jcas.throwFeatMissing("lastName", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lastName);
  }
  /** @generated */    
  public void setLastName(int addr, String v) {
        if (featOkTst && casFeat_lastName == null)
      jcas.throwFeatMissing("lastName", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_lastName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_firstNameSoundex;
  /** @generated */
  final int     casFeatCode_firstNameSoundex;
  /** @generated */ 
  public String getFirstNameSoundex(int addr) {
        if (featOkTst && casFeat_firstNameSoundex == null)
      jcas.throwFeatMissing("firstNameSoundex", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_firstNameSoundex);
  }
  /** @generated */    
  public void setFirstNameSoundex(int addr, String v) {
        if (featOkTst && casFeat_firstNameSoundex == null)
      jcas.throwFeatMissing("firstNameSoundex", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_firstNameSoundex, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lastNameSoundex;
  /** @generated */
  final int     casFeatCode_lastNameSoundex;
  /** @generated */ 
  public String getLastNameSoundex(int addr) {
        if (featOkTst && casFeat_lastNameSoundex == null)
      jcas.throwFeatMissing("lastNameSoundex", "org.ohnlp.typesystem.type.structured.Demographics");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lastNameSoundex);
  }
  /** @generated */    
  public void setLastNameSoundex(int addr, String v) {
        if (featOkTst && casFeat_lastNameSoundex == null)
      jcas.throwFeatMissing("lastNameSoundex", "org.ohnlp.typesystem.type.structured.Demographics");
    ll_cas.ll_setStringValue(addr, casFeatCode_lastNameSoundex, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Demographics_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_birthDate = jcas.getRequiredFeatureDE(casType, "birthDate", "uima.cas.String", featOkTst);
    casFeatCode_birthDate  = (null == casFeat_birthDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_birthDate).getCode();

 
    casFeat_deathDate = jcas.getRequiredFeatureDE(casType, "deathDate", "uima.cas.String", featOkTst);
    casFeatCode_deathDate  = (null == casFeat_deathDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_deathDate).getCode();

 
    casFeat_gender = jcas.getRequiredFeatureDE(casType, "gender", "uima.cas.String", featOkTst);
    casFeatCode_gender  = (null == casFeat_gender) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_gender).getCode();

 
    casFeat_firstName = jcas.getRequiredFeatureDE(casType, "firstName", "uima.cas.String", featOkTst);
    casFeatCode_firstName  = (null == casFeat_firstName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstName).getCode();

 
    casFeat_middleName = jcas.getRequiredFeatureDE(casType, "middleName", "uima.cas.String", featOkTst);
    casFeatCode_middleName  = (null == casFeat_middleName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_middleName).getCode();

 
    casFeat_lastName = jcas.getRequiredFeatureDE(casType, "lastName", "uima.cas.String", featOkTst);
    casFeatCode_lastName  = (null == casFeat_lastName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lastName).getCode();

 
    casFeat_firstNameSoundex = jcas.getRequiredFeatureDE(casType, "firstNameSoundex", "uima.cas.String", featOkTst);
    casFeatCode_firstNameSoundex  = (null == casFeat_firstNameSoundex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstNameSoundex).getCode();

 
    casFeat_lastNameSoundex = jcas.getRequiredFeatureDE(casType, "lastNameSoundex", "uima.cas.String", featOkTst);
    casFeatCode_lastNameSoundex  = (null == casFeat_lastNameSoundex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lastNameSoundex).getCode();

  }
}



    