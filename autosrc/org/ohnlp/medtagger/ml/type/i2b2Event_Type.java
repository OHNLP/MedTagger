
/* First created by JCasGen Tue Sep 24 19:28:07 CDT 2013 */
package org.ohnlp.medtagger.ml.type;

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

/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * @generated */
public class i2b2Event_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (i2b2Event_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = i2b2Event_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new i2b2Event(addr, i2b2Event_Type.this);
  			   i2b2Event_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new i2b2Event(addr, i2b2Event_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = i2b2Event.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.ml.type.i2b2Event");
 
  /** @generated */
  final Feature casFeat_eventType;
  /** @generated */
  final int     casFeatCode_eventType;
  /** @generated */ 
  public String getEventType(int addr) {
        if (featOkTst && casFeat_eventType == null)
      jcas.throwFeatMissing("eventType", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_eventType);
  }
  /** @generated */    
  public void setEventType(int addr, String v) {
        if (featOkTst && casFeat_eventType == null)
      jcas.throwFeatMissing("eventType", "org.ohnlp.medtagger.ml.type.i2b2Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_eventType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_beginLineToken;
  /** @generated */
  final int     casFeatCode_beginLineToken;
  /** @generated */ 
  public int getBeginLineToken(int addr) {
        if (featOkTst && casFeat_beginLineToken == null)
      jcas.throwFeatMissing("beginLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return ll_cas.ll_getIntValue(addr, casFeatCode_beginLineToken);
  }
  /** @generated */    
  public void setBeginLineToken(int addr, int v) {
        if (featOkTst && casFeat_beginLineToken == null)
      jcas.throwFeatMissing("beginLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    ll_cas.ll_setIntValue(addr, casFeatCode_beginLineToken, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endLineToken;
  /** @generated */
  final int     casFeatCode_endLineToken;
  /** @generated */ 
  public int getEndLineToken(int addr) {
        if (featOkTst && casFeat_endLineToken == null)
      jcas.throwFeatMissing("endLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return ll_cas.ll_getIntValue(addr, casFeatCode_endLineToken);
  }
  /** @generated */    
  public void setEndLineToken(int addr, int v) {
        if (featOkTst && casFeat_endLineToken == null)
      jcas.throwFeatMissing("endLineToken", "org.ohnlp.medtagger.ml.type.i2b2Event");
    ll_cas.ll_setIntValue(addr, casFeatCode_endLineToken, v);}
    
  
 
  /** @generated */
  final Feature casFeat_beginLine;
  /** @generated */
  final int     casFeatCode_beginLine;
  /** @generated */ 
  public int getBeginLine(int addr) {
        if (featOkTst && casFeat_beginLine == null)
      jcas.throwFeatMissing("beginLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return ll_cas.ll_getIntValue(addr, casFeatCode_beginLine);
  }
  /** @generated */    
  public void setBeginLine(int addr, int v) {
        if (featOkTst && casFeat_beginLine == null)
      jcas.throwFeatMissing("beginLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    ll_cas.ll_setIntValue(addr, casFeatCode_beginLine, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endLine;
  /** @generated */
  final int     casFeatCode_endLine;
  /** @generated */ 
  public int getEndLine(int addr) {
        if (featOkTst && casFeat_endLine == null)
      jcas.throwFeatMissing("endLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    return ll_cas.ll_getIntValue(addr, casFeatCode_endLine);
  }
  /** @generated */    
  public void setEndLine(int addr, int v) {
        if (featOkTst && casFeat_endLine == null)
      jcas.throwFeatMissing("endLine", "org.ohnlp.medtagger.ml.type.i2b2Event");
    ll_cas.ll_setIntValue(addr, casFeatCode_endLine, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public i2b2Event_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_eventType = jcas.getRequiredFeatureDE(casType, "eventType", "uima.cas.String", featOkTst);
    casFeatCode_eventType  = (null == casFeat_eventType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_eventType).getCode();

 
    casFeat_beginLineToken = jcas.getRequiredFeatureDE(casType, "beginLineToken", "uima.cas.Integer", featOkTst);
    casFeatCode_beginLineToken  = (null == casFeat_beginLineToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_beginLineToken).getCode();

 
    casFeat_endLineToken = jcas.getRequiredFeatureDE(casType, "endLineToken", "uima.cas.Integer", featOkTst);
    casFeatCode_endLineToken  = (null == casFeat_endLineToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endLineToken).getCode();

 
    casFeat_beginLine = jcas.getRequiredFeatureDE(casType, "beginLine", "uima.cas.Integer", featOkTst);
    casFeatCode_beginLine  = (null == casFeat_beginLine) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_beginLine).getCode();

 
    casFeat_endLine = jcas.getRequiredFeatureDE(casType, "endLine", "uima.cas.Integer", featOkTst);
    casFeatCode_endLine  = (null == casFeat_endLine) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endLine).getCode();

  }
}



    