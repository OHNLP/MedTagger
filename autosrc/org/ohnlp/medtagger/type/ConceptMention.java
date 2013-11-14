

/* First created by JCasGen Tue Sep 24 19:27:47 CDT 2013 */
package org.ohnlp.medtagger.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.ohnlp.typesystem.type.textspan.Sentence;
import org.apache.uima.jcas.tcas.Annotation;


/** Concept mention stands for concepts detected by the NLP system
 * Updated by JCasGen Wed Oct 30 16:30:47 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class ConceptMention extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ConceptMention.class);
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
  protected ConceptMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ConceptMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ConceptMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ConceptMention(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: detectionMethod

  /** getter for detectionMethod - gets There are multiple approaches to detect concept mentions including: dictionary lookup, 
  machine learning approaches trained using different training corpus 
  (i2b2 2010 Concept Mention, or SHARE 2013 concept mention corpus).
   * @generated */
  public String getDetectionMethod() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_detectionMethod == null)
      jcasType.jcas.throwFeatMissing("detectionMethod", "org.ohnlp.medtagger.type.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_detectionMethod);}
    
  /** setter for detectionMethod - sets There are multiple approaches to detect concept mentions including: dictionary lookup, 
  machine learning approaches trained using different training corpus 
  (i2b2 2010 Concept Mention, or SHARE 2013 concept mention corpus). 
   * @generated */
  public void setDetectionMethod(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_detectionMethod == null)
      jcasType.jcas.throwFeatMissing("detectionMethod", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_detectionMethod, v);}    
   
    
  //*--------------*
  //* Feature: normTarget

  /** getter for normTarget - gets This corresponds to the preferred names of the corresponding concepts. Currently, 
  it is chosen as the most popular synonyms appearing in the clinical corpora.
   * @generated */
  public String getNormTarget() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_normTarget == null)
      jcasType.jcas.throwFeatMissing("normTarget", "org.ohnlp.medtagger.type.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_normTarget);}
    
  /** setter for normTarget - sets This corresponds to the preferred names of the corresponding concepts. Currently, 
  it is chosen as the most popular synonyms appearing in the clinical corpora. 
   * @generated */
  public void setNormTarget(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_normTarget == null)
      jcasType.jcas.throwFeatMissing("normTarget", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_normTarget, v);}    
   
    
  //*--------------*
  //* Feature: Certainty

  /** getter for Certainty - gets This refers to the certainty context. The definition is consistent with  
  Context: see http://orbit.nlm.nih.gov/resource/context
   * @generated */
  public String getCertainty() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_Certainty == null)
      jcasType.jcas.throwFeatMissing("Certainty", "org.ohnlp.medtagger.type.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_Certainty);}
    
  /** setter for Certainty - sets This refers to the certainty context. The definition is consistent with  
  Context: see http://orbit.nlm.nih.gov/resource/context 
   * @generated */
  public void setCertainty(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_Certainty == null)
      jcasType.jcas.throwFeatMissing("Certainty", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_Certainty, v);}    
   
    
  //*--------------*
  //* Feature: semGroup

  /** getter for semGroup - gets Semantic groups of the corresponding concepts. 
  Adapted from SemGroup defined in the UMLS. See: http://semanticnetwork.nlm.nih.gov/SemGroups/
   * @generated */
  public String getSemGroup() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_semGroup == null)
      jcasType.jcas.throwFeatMissing("semGroup", "org.ohnlp.medtagger.type.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_semGroup);}
    
  /** setter for semGroup - sets Semantic groups of the corresponding concepts. 
  Adapted from SemGroup defined in the UMLS. See: http://semanticnetwork.nlm.nih.gov/SemGroups/ 
   * @generated */
  public void setSemGroup(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_semGroup == null)
      jcasType.jcas.throwFeatMissing("semGroup", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_semGroup, v);}    
   
    
  //*--------------*
  //* Feature: status

  /** getter for status - gets This refers to the status context. The definition is consist with  
  Context: see http://orbit.nlm.nih.gov/resource/context
   * @generated */
  public String getStatus() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_status == null)
      jcasType.jcas.throwFeatMissing("status", "org.ohnlp.medtagger.type.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_status);}
    
  /** setter for status - sets This refers to the status context. The definition is consist with  
  Context: see http://orbit.nlm.nih.gov/resource/context 
   * @generated */
  public void setStatus(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_status == null)
      jcasType.jcas.throwFeatMissing("status", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_status, v);}    
   
    
  //*--------------*
  //* Feature: sentence

  /** getter for sentence - gets The sentence containing the concept mention
   * @generated */
  public Sentence getSentence() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.type.ConceptMention");
    return (Sentence)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_sentence)));}
    
  /** setter for sentence - sets The sentence containing the concept mention 
   * @generated */
  public void setSentence(Sentence v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_sentence, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: experiencer

  /** getter for experiencer - gets This refers to the status context. The definition is consist with  
  Context: see http://orbit.nlm.nih.gov/resource/context
   * @generated */
  public String getExperiencer() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_experiencer == null)
      jcasType.jcas.throwFeatMissing("experiencer", "org.ohnlp.medtagger.type.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_experiencer);}
    
  /** setter for experiencer - sets This refers to the status context. The definition is consist with  
  Context: see http://orbit.nlm.nih.gov/resource/context 
   * @generated */
  public void setExperiencer(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_experiencer == null)
      jcasType.jcas.throwFeatMissing("experiencer", "org.ohnlp.medtagger.type.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_experiencer, v);}    
  }

    