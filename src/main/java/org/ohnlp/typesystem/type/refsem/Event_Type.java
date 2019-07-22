
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

/** A real-world event; similar to Entity, but may bear some temporal properties and relationships.  In the constrained clinical context, many concepts are discussed as instances with some temporal component, e.g., Medications, Labs.
 * Updated by JCasGen Wed Oct 30 16:30:48 CDT 2013
 * @generated */
public class Event_Type extends Element_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Event_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Event_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Event(addr, Event_Type.this);
  			   Event_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Event(addr, Event_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Event.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.ohnlp.typesystem.type.refsem.Event");
 
  /** @generated */
  final Feature casFeat_properties;
  /** @generated */
  final int     casFeatCode_properties;
  /** @generated */ 
  public int getProperties(int addr) {
        if (featOkTst && casFeat_properties == null)
      jcas.throwFeatMissing("properties", "org.ohnlp.typesystem.type.refsem.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_properties);
  }
  /** @generated */    
  public void setProperties(int addr, int v) {
        if (featOkTst && casFeat_properties == null)
      jcas.throwFeatMissing("properties", "org.ohnlp.typesystem.type.refsem.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_properties, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Event_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_properties = jcas.getRequiredFeatureDE(casType, "properties", "org.ohnlp.typesystem.type.refsem.EventProperties", featOkTst);
    casFeatCode_properties  = (null == casFeat_properties) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_properties).getCode();

  }
}



    