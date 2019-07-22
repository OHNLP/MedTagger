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

package org.ohnlp.medtagger.ml.crfsuite;

import static org.ohnlp.medtagger.ml.util.PlatformDetection.ARCH_X86_32;
import static org.ohnlp.medtagger.ml.util.PlatformDetection.ARCH_X86_64;
import static org.ohnlp.medtagger.ml.util.PlatformDetection.OS_WINDOWS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ohnlp.medtagger.ml.feature.Feature;
import org.ohnlp.medtagger.ml.util.Executer;
import org.ohnlp.medtagger.ml.util.PlatformDetection;


public class CRFSuiteWrapper {
  private Logger iv_logger = Logger.getLogger(getClass().getName());
  private File executable;
  
  public CRFSuiteWrapper() throws Exception {
    Executables exec = new Executables();
    if (exec.isInstalled()) {
      iv_logger.info("The CRFSuite is installed on the system");
      executable = new File(exec.getExecutableName());
    } else {
      this.executable = exec.getExecutable();
      if (!exec.isInstalled(this.executable.getAbsolutePath())) {
    	  iv_logger.warn(
            "The CRFSuite binary is not available for the current operation system, please install it!");
      } else {
        iv_logger.info("The CRFSuite binary is successfully extracted");
      }
    }
  }
  
  public CRFSuiteWrapper(String crfhome) throws Exception {
	    Executables exec = new Executables(crfhome);
	    System.out.println(exec.getExecutableName());
	    if (exec.isInstalled(exec.getExecutableName())) {
	      iv_logger.info("The CRFSuite is installed on the system");
	      executable = new File(exec.getExecutableName());
	    } else {
	      this.executable = exec.getExecutable();
	      if (!exec.isInstalled(this.executable.getAbsolutePath())) {
	    	  iv_logger.warn(
	            "The CRFSuite binary is not available for the current operation system, please install it!");
	      } else {
	        iv_logger.info("The CRFSuite binary is successfully extracted");
	      }
	    }
	  }

  class Executables {
    PlatformDetection pd = new PlatformDetection();
    String crfsuitehome = "";
    
    public Executables() {
      if (pd.getOs().equals(OS_WINDOWS) && pd.getArch().equals(ARCH_X86_64)) {
        pd.setArch(ARCH_X86_32);
      }
    }

    public Executables(String csh){
    	 if (pd.getOs().equals(OS_WINDOWS) && pd.getArch().equals(ARCH_X86_64)) {
    	        pd.setArch(ARCH_X86_32);
    	      }
    	crfsuitehome=csh.replaceAll("\\\\","/");
     }
    
    public String getExecutablePath() {

      String[] path = new String[] { crfsuitehome, pd.toString(), "bin" };
      String sep = "/";
      String p = "";
      for (String s : path) {
        p += s + sep;
      }
      return p;
    }

    public boolean isInstalled() throws Exception {
      return isInstalled("crfsuite");
    }

    public boolean isInstalled(String path) throws Exception {
      Executer exec=new Executer();	
      
      try {
    	  exec.execute(path+" -h");
    	  int status = exec.getStatus();
    	  if (status != 0) {
    	           throw new Exception("Error status " + status);
    	     }

    	      byte[] err = exec.getErr();
    	      if (err != null) {
    	          throw new Exception("Error message: " + new String(err));
    	      }
    	      byte[] out = exec.getOut();
    	      String str=new String(out);
    	      if (str.length() < 8) {
    	    	  iv_logger.warn("CRFSuite could not be executed!");
    	      }
    	      return str.length() >= 8 && str.substring(0, 8).equals("CRFSuite");
      } catch (IOException e) {
          iv_logger.warn(e.getMessage());
      }
      return false;
    }

    public String getExecutableName() {
      return  getExecutablePath()+ "crfsuite" +pd.getExecutableSuffix();
    }

    public File getExecutable() {
      String loc = getExecutablePath() + getExecutableName();
      URL crfExecUrl = getClass().getResource(loc);
      crfExecUrl = ClassLoader.getSystemResource(loc);
      iv_logger.info("CrfSuite Location " + loc);
      iv_logger.info("CrfSuite Url: " + crfExecUrl);
      File f;
      try {
        if (crfExecUrl != null) {
          f = new File(crfExecUrl.getFile());
          if (!f.exists()) {
            f = new File(URLDecoder.decode(f.getAbsolutePath(), ("UTF-8")));
          }
          f.setExecutable(true);
          return f;
        }
        iv_logger.warn("The executable could not be found at " + loc);
        return null;
      } catch (IOException e) {
        e.printStackTrace();

        return null;
      }

    }

  }

  public void trainClassifier(String model, String trainingDataFile, String[] args)
      throws Exception {

    StringBuffer cmd = new StringBuffer();
    cmd.append(executable.getPath());
    cmd.append(" ");
    cmd.append("learn");
    cmd.append(" ");
    cmd.append("-m");
    cmd.append(" ");
    cmd.append(model);
    cmd.append(" ");
    for (String a : args) {
      cmd.append(a);
      cmd.append(" ");
    }
    cmd.append(trainingDataFile);
    System.out.println(cmd.toString());
    Executer exec=new Executer();	
    exec.execute(cmd.toString());
  	  int status = exec.getStatus();
  	  if (status != 0) {
  	           throw new Exception("Error status " + status);
  	     }

  	      byte[] err = exec.getErr();
  	      if (err != null) {
  	          throw new Exception("Error message: " + new String(err));
  	      } 	      
    }

  public void trainClassifier(String model, HashMap<List<Feature>,String> featureslabel , String[] args)
	      throws Exception {
	  
	  	File featureFile = File.createTempFile("features", ".crfsuite");
	    featureFile.deleteOnExit();
	    iv_logger.info("Write features to classify to " + featureFile.getAbsolutePath());
	    BufferedWriter out = new BufferedWriter(new FileWriter(featureFile));
	      Set<List<Feature>> features=featureslabel.keySet();
	      for (List<Feature> fs : features) {
	    	  out.append(featureslabel.get(fs)+"\t");
	    	  for (Feature f : fs) {
	    		     if(f.getWeight()!=0)	{
	    		    	out.append(f.getName()+"="+f.getValue()+":"+f.getWeight());
	    		        out.append("\t");
	    		     }
	    	  }    	  
	        out.append("\n");
	      }
	      out.close();
	      trainClassifier(model,featureFile.toString(), args);
  }
  
  public List<String> classifyFeatures(String featureFile, String modelFile, int featureSize)
      throws IOException {
    return classifyFeatures(new File(featureFile), new File(modelFile), featureSize);
  }

  public List<String> classifyFeatures(File featureFile, File modelFile, int featureSize)
      throws IOException {
    List<String> result = new ArrayList<String>();
    result = classifyFeatures(featureFile, modelFile);
     
    if (result.size() != featureSize) {
      throw new IllegalStateException(
          "The number of extracted classified labels is not equivalent with the number of instanzes ("
              + result.size() + "!=" + featureSize + ")");
    }

    return result;

  }

  public List<String> classifyFeatures(
	      HashMap<List<Feature>,String> featureslabel,
	      File modelFile) throws IOException {

	    File featureFile = File.createTempFile("features", ".crfsuite");
	    featureFile.deleteOnExit();
	    iv_logger.info("Write features to classify to " + featureFile.getAbsolutePath());
	    try {
	      BufferedWriter out = new BufferedWriter(new FileWriter(featureFile));
	      Set<List<Feature>> features=featureslabel.keySet();
	      for (List<Feature> fs : features) {
	    	  out.append(featureslabel.get(fs)+"\t");
	    	  for (Feature f : fs) {
	    		     if(f.getWeight()!=0)	{
	    		    	out.append(f.getName()+"="+f.getValue()+":"+f.getWeight());
	    		        out.append("\t");
	    		     }
	    	  }    	  
	        out.append("\n");
	      }
	      out.close();
	      return classifyFeatures(featureFile, modelFile, features.size());
	    } catch (Exception e) {
	     iv_logger.warn(e.getMessage());
	    }
	    return null;
	  }
  
  
  private List<String> classifyFeatures(File featureFile, File modelFile) throws IOException {
    StringBuffer cmd = new StringBuffer();
    cmd.append(executable.getPath());
    cmd.append(" tag -m ");
    cmd.append(modelFile.getAbsolutePath());
    cmd.append(" ");
    cmd.append(featureFile.getAbsolutePath());
     Executer exec=new Executer();	
    List<String> tagslist=new ArrayList <String>();
 	 
    try {
      exec.execute(cmd.toString());
  	  int status = exec.getStatus();
  	  if (status != 0) {
  	           throw new Exception("Error status " + status);
  	     }

  	      byte[] err = exec.getErr();
  	      if (err != null) {
  	          throw new Exception("Error message: " + new String(err));
  	      }
  	      byte[] out = exec.getOut();
  	      String str=new String(out);
  	      String[] tags=str.split("\n");
  	      for(String tag:tags){
  	    	  if(tag.length()==0) continue;
  	    	  tagslist.add(tag);
  	    	//  System.out.println(tag);
  	       }
    }
    catch (Exception e) {
        iv_logger.warn(e.getMessage());
       }
    return tagslist;
  }

}
