/*******************************************************************************
 * Copyright: (c)  2013  Mayo Foundation for Medical Education and 
 *  Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 *  triple-shield Mayo logo are trademarks and service marks of MFMER.
 *  
 *  Except as contained in the copyright notice above, or as used to identify 
 *  MFMER as the author of this software, the trade names, trademarks, service
 *  marks, or product names of the copyright holder shall not be used in
 *  advertising, promotion or otherwise in connection with this software without
 *  prior written authorization of the copyright holder.
 *   
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *   
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *   
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 *******************************************************************************/

package org.ohnlp.medtagger.ml.feature;

import java.io.Serializable;



public class Feature implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3215288856677656204L;

  protected String name;
  protected Object value;
  protected double weight;

  public Feature() {
  }

  public Feature(Object value) {
    this.value = value;
  }

  // Default to existence
  public Feature(String name, Object value) {
    this.name = name;
    this.value = value;
    this.weight = 1;
  }

  public Feature(String name, Object value, double weight) {
	    this.name = name;
	    this.value = value;
	    this.weight = weight;
	  }

  public static Feature createFeature(String namePrefix, Feature feature) {
    return new Feature(createName(namePrefix, feature.name), feature.value);
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void setWeight(int count) {
	    this.weight = count;
	  }
  public double getWeight() {
	    return weight;
	  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static String createName(String... names) {
    StringBuffer buffer = new StringBuffer();
    for (String name : names) {
      if (name != null) {
        buffer.append(name);
        buffer.append('_');
      }
    }
    if (buffer.length() > 0) {
      buffer.deleteCharAt(buffer.length() - 1);
    }
    return buffer.toString();
  }

  public String toString() {
    String className = Feature.class.getSimpleName();
    return String.format("%s(<%s>, <%s>)", className, this.name, this.value);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Feature) {
      Feature other = (Feature) obj;
      boolean nameMatch = (this.name == null && other.name == null)
          || (this.name != null && this.name.equals(other.name));
      boolean valueMatch = (this.value == null && other.value == null)
          || (this.value != null && this.value.equals(other.value));
      return nameMatch && valueMatch;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int hash = 1;
    hash = hash * 31 + (this.name == null ? 0 : this.name.hashCode());
    hash = hash * 31 + (this.value == null ? 0 : this.value.hashCode());
    return hash;
  }

}
