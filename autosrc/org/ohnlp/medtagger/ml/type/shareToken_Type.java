
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
import org.ohnlp.typesystem.type.syntax.BaseToken_Type;

/** 
 * Updated by JCasGen Tue Sep 24 19:28:07 CDT 2013
 * @generated */
public class shareToken_Type extends BaseToken_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (shareToken_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = shareToken_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new shareToken(addr, shareToken_Type.this);
  			   shareToken_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new shareToken(addr, shareToken_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = shareToken.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.medtagger.ml.type.shareToken");
 
  /** @generated */
  final Feature casFeat_lineNumber;
  /** @generated */
  final int     casFeatCode_lineNumber;
  /** @generated */ 
  public int getLineNumber(int addr) {
        if (featOkTst && casFeat_lineNumber == null)
      jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_lineNumber);
  }
  /** @generated */    
  public void setLineNumber(int addr, int v) {
        if (featOkTst && casFeat_lineNumber == null)
      jcas.throwFeatMissing("lineNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_lineNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lineTokenNumber;
  /** @generated */
  final int     casFeatCode_lineTokenNumber;
  /** @generated */ 
  public int getLineTokenNumber(int addr) {
        if (featOkTst && casFeat_lineTokenNumber == null)
      jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_lineTokenNumber);
  }
  /** @generated */    
  public void setLineTokenNumber(int addr, int v) {
        if (featOkTst && casFeat_lineTokenNumber == null)
      jcas.throwFeatMissing("lineTokenNumber", "org.ohnlp.medtagger.ml.type.shareToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_lineTokenNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_shareBegin;
  /** @generated */
  final int     casFeatCode_shareBegin;
  /** @generated */ 
  public int getShareBegin(int addr) {
        if (featOkTst && casFeat_shareBegin == null)
      jcas.throwFeatMissing("shareBegin", "org.ohnlp.medtagger.ml.type.shareToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_shareBegin);
  }
  /** @generated */    
  public void setShareBegin(int addr, int v) {
        if (featOkTst && casFeat_shareBegin == null)
      jcas.throwFeatMissing("shareBegin", "org.ohnlp.medtagger.ml.type.shareToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_shareBegin, v);}
    
  
 
  /** @generated */
  final Feature casFeat_shareEnd;
  /** @generated */
  final int     casFeatCode_shareEnd;
  /** @generated */ 
  public int getShareEnd(int addr) {
        if (featOkTst && casFeat_shareEnd == null)
      jcas.throwFeatMissing("shareEnd", "org.ohnlp.medtagger.ml.type.shareToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_shareEnd);
  }
  /** @generated */    
  public void setShareEnd(int addr, int v) {
        if (featOkTst && casFeat_shareEnd == null)
      jcas.throwFeatMissing("shareEnd", "org.ohnlp.medtagger.ml.type.shareToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_shareEnd, v);}
    
  
 
  /** @generated */
  final Feature casFeat_capitalization;
  /** @generated */
  final int     casFeatCode_capitalization;
  /** @generated */ 
  public int getCapitalization(int addr) {
        if (featOkTst && casFeat_capitalization == null)
      jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.shareToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_capitalization);
  }
  /** @generated */    
  public void setCapitalization(int addr, int v) {
        if (featOkTst && casFeat_capitalization == null)
      jcas.throwFeatMissing("capitalization", "org.ohnlp.medtagger.ml.type.shareToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_capitalization, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public shareToken_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_lineNumber = jcas.getRequiredFeatureDE(casType, "lineNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_lineNumber  = (null == casFeat_lineNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lineNumber).getCode();

 
    casFeat_lineTokenNumber = jcas.getRequiredFeatureDE(casType, "lineTokenNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_lineTokenNumber  = (null == casFeat_lineTokenNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lineTokenNumber).getCode();

 
    casFeat_shareBegin = jcas.getRequiredFeatureDE(casType, "shareBegin", "uima.cas.Integer", featOkTst);
    casFeatCode_shareBegin  = (null == casFeat_shareBegin) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_shareBegin).getCode();

 
    casFeat_shareEnd = jcas.getRequiredFeatureDE(casType, "shareEnd", "uima.cas.Integer", featOkTst);
    casFeatCode_shareEnd  = (null == casFeat_shareEnd) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_shareEnd).getCode();

 
    casFeat_capitalization = jcas.getRequiredFeatureDE(casType, "capitalization", "uima.cas.Integer", featOkTst);
    casFeatCode_capitalization  = (null == casFeat_capitalization) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_capitalization).getCode();

  }
}



    