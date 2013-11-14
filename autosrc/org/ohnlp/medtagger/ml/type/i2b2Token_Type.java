
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
import org.ohnlp.typesystem.type.syntax.WordToken_Type;

/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * @generated */
public class i2b2Token_Type extends WordToken_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (i2b2Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = i2b2Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new i2b2Token(addr, i2b2Token_Type.this);
  			   i2b2Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new i2b2Token(addr, i2b2Token_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = i2b2Token.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.ml.type.i2b2Token");
 
  /** @generated */
  final Feature casFeat_lineNumber;
  /** @generated */
  final int     casFeatCode_lineNumber;
  /** @generated */ 
  public int getLineNumber(int addr) {
        if (featOkTst && casFeat_lineNumber == null)
      jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return ll_cas.ll_getIntValue(addr, casFeatCode_lineNumber);
  }
  /** @generated */    
  public void setLineNumber(int addr, int v) {
        if (featOkTst && casFeat_lineNumber == null)
      jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    ll_cas.ll_setIntValue(addr, casFeatCode_lineNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lineTokenNumber;
  /** @generated */
  final int     casFeatCode_lineTokenNumber;
  /** @generated */ 
  public int getLineTokenNumber(int addr) {
        if (featOkTst && casFeat_lineTokenNumber == null)
      jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return ll_cas.ll_getIntValue(addr, casFeatCode_lineTokenNumber);
  }
  /** @generated */    
  public void setLineTokenNumber(int addr, int v) {
        if (featOkTst && casFeat_lineTokenNumber == null)
      jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.i2b2Token");
    ll_cas.ll_setIntValue(addr, casFeatCode_lineTokenNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_i2b2Begin;
  /** @generated */
  final int     casFeatCode_i2b2Begin;
  /** @generated */ 
  public int getI2b2Begin(int addr) {
        if (featOkTst && casFeat_i2b2Begin == null)
      jcas.throwFeatMissing("i2b2Begin", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return ll_cas.ll_getIntValue(addr, casFeatCode_i2b2Begin);
  }
  /** @generated */    
  public void setI2b2Begin(int addr, int v) {
        if (featOkTst && casFeat_i2b2Begin == null)
      jcas.throwFeatMissing("i2b2Begin", "org.ohnlp.medtagger.ml.type.i2b2Token");
    ll_cas.ll_setIntValue(addr, casFeatCode_i2b2Begin, v);}
    
  
 
  /** @generated */
  final Feature casFeat_i2b2End;
  /** @generated */
  final int     casFeatCode_i2b2End;
  /** @generated */ 
  public int getI2b2End(int addr) {
        if (featOkTst && casFeat_i2b2End == null)
      jcas.throwFeatMissing("i2b2End", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return ll_cas.ll_getIntValue(addr, casFeatCode_i2b2End);
  }
  /** @generated */    
  public void setI2b2End(int addr, int v) {
        if (featOkTst && casFeat_i2b2End == null)
      jcas.throwFeatMissing("i2b2End", "org.ohnlp.medtagger.ml.type.i2b2Token");
    ll_cas.ll_setIntValue(addr, casFeatCode_i2b2End, v);}
    
  
 
  /** @generated */
  final Feature casFeat_capitalization;
  /** @generated */
  final int     casFeatCode_capitalization;
  /** @generated */ 
  public int getCapitalization(int addr) {
        if (featOkTst && casFeat_capitalization == null)
      jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.i2b2Token");
    return ll_cas.ll_getIntValue(addr, casFeatCode_capitalization);
  }
  /** @generated */    
  public void setCapitalization(int addr, int v) {
        if (featOkTst && casFeat_capitalization == null)
      jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.i2b2Token");
    ll_cas.ll_setIntValue(addr, casFeatCode_capitalization, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public i2b2Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_lineNumber = jcas.getRequiredFeatureDE(casType, "lineNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_lineNumber  = (null == casFeat_lineNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lineNumber).getCode();

 
    casFeat_lineTokenNumber = jcas.getRequiredFeatureDE(casType, "lineTokenNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_lineTokenNumber  = (null == casFeat_lineTokenNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lineTokenNumber).getCode();

 
    casFeat_i2b2Begin = jcas.getRequiredFeatureDE(casType, "i2b2Begin", "uima.cas.Integer", featOkTst);
    casFeatCode_i2b2Begin  = (null == casFeat_i2b2Begin) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_i2b2Begin).getCode();

 
    casFeat_i2b2End = jcas.getRequiredFeatureDE(casType, "i2b2End", "uima.cas.Integer", featOkTst);
    casFeatCode_i2b2End  = (null == casFeat_i2b2End) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_i2b2End).getCode();

 
    casFeat_capitalization = jcas.getRequiredFeatureDE(casType, "capitalization", "uima.cas.Integer", featOkTst);
    casFeatCode_capitalization  = (null == casFeat_capitalization) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_capitalization).getCode();

  }
}



    