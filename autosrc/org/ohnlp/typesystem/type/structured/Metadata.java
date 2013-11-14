

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.structured;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.LongArray;
import org.apache.uima.jcas.cas.TOP;


/** Structured data that captures information about the document, patient, or context of the clinical text.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class Metadata extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Metadata.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Metadata() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Metadata(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Metadata(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: patientID

  /** getter for patientID - gets 
   * @generated */
  public long getPatientID() {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_patientID == null)
      jcasType.jcas.throwFeatMissing("patientID", "org.ohnlp.typesystem.type.structured.Metadata");
    return jcasType.ll_cas.ll_getLongValue(addr, ((Metadata_Type)jcasType).casFeatCode_patientID);}
    
  /** setter for patientID - sets  
   * @generated */
  public void setPatientID(long v) {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_patientID == null)
      jcasType.jcas.throwFeatMissing("patientID", "org.ohnlp.typesystem.type.structured.Metadata");
    jcasType.ll_cas.ll_setLongValue(addr, ((Metadata_Type)jcasType).casFeatCode_patientID, v);}    
   
    
  //*--------------*
  //* Feature: providerID

  /** getter for providerID - gets 
   * @generated */
  public LongArray getProviderID() {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_providerID == null)
      jcasType.jcas.throwFeatMissing("providerID", "org.ohnlp.typesystem.type.structured.Metadata");
    return (LongArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_providerID)));}
    
  /** setter for providerID - sets  
   * @generated */
  public void setProviderID(LongArray v) {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_providerID == null)
      jcasType.jcas.throwFeatMissing("providerID", "org.ohnlp.typesystem.type.structured.Metadata");
    jcasType.ll_cas.ll_setRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_providerID, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for providerID - gets an indexed value - 
   * @generated */
  public long getProviderID(int i) {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_providerID == null)
      jcasType.jcas.throwFeatMissing("providerID", "org.ohnlp.typesystem.type.structured.Metadata");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_providerID), i);
    return jcasType.ll_cas.ll_getLongArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_providerID), i);}

  /** indexed setter for providerID - sets an indexed value - 
   * @generated */
  public void setProviderID(int i, long v) { 
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_providerID == null)
      jcasType.jcas.throwFeatMissing("providerID", "org.ohnlp.typesystem.type.structured.Metadata");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_providerID), i);
    jcasType.ll_cas.ll_setLongArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_providerID), i, v);}
   
    
  //*--------------*
  //* Feature: sourceData

  /** getter for sourceData - gets 
   * @generated */
  public SourceData getSourceData() {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_sourceData == null)
      jcasType.jcas.throwFeatMissing("sourceData", "org.ohnlp.typesystem.type.structured.Metadata");
    return (SourceData)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_sourceData)));}
    
  /** setter for sourceData - sets  
   * @generated */
  public void setSourceData(SourceData v) {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_sourceData == null)
      jcasType.jcas.throwFeatMissing("sourceData", "org.ohnlp.typesystem.type.structured.Metadata");
    jcasType.ll_cas.ll_setRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_sourceData, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: demographics

  /** getter for demographics - gets 
   * @generated */
  public Demographics getDemographics() {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_demographics == null)
      jcasType.jcas.throwFeatMissing("demographics", "org.ohnlp.typesystem.type.structured.Metadata");
    return (Demographics)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_demographics)));}
    
  /** setter for demographics - sets  
   * @generated */
  public void setDemographics(Demographics v) {
    if (Metadata_Type.featOkTst && ((Metadata_Type)jcasType).casFeat_demographics == null)
      jcasType.jcas.throwFeatMissing("demographics", "org.ohnlp.typesystem.type.structured.Metadata");
    jcasType.ll_cas.ll_setRefValue(addr, ((Metadata_Type)jcasType).casFeatCode_demographics, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    