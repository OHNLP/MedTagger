

/* First created by JCasGen Tue Sep 24 19:27:48 CDT 2013 */
package org.ohnlp.typesystem.type.relation;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A biomedical relationship between two spans of text that have been mapped to UMLS concepts. Inherits "category" from Relation, with values such as affects, causes, complicates, contraindicates, degree_of, etc.
 * Updated by JCasGen Wed Oct 30 16:30:49 CDT 2013
 * XML source: /home/liu/edtnlp/MedTagger-1.0/descsrc/org/ohnlp/medtagger/types/MedTaggerTypes.xml
 * @generated */
public class UMLSRelation extends BinaryTextRelation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(UMLSRelation.class);
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
  protected UMLSRelation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public UMLSRelation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public UMLSRelation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
}

    