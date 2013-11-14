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

package org.ohnlp.medtagger.ml.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Executer {
	    private int status = -1;
	    private byte[] out;
	    private byte[] err;

	   public void execute(String command) throws IOException {

	      Process process = Runtime.getRuntime().exec(command);
	      ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
	      ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();

	      StreamPump outPump = new StreamPump(
	          process.getInputStream(), outBuffer);
	      StreamPump errPump = new StreamPump(
	          process.getErrorStream(), errBuffer);

	      Thread outThread = new Thread(outPump);
	      Thread errThread = new Thread(errPump);
	      outThread.start();
	      errThread.start();


	      // wait untill the process end, and check return status
	       status = -1;
	      try {
	       status = process.waitFor();
	      }
	       catch (InterruptedException ex) {
	       throw new IOException("Interrupted when waiting for external process finished.");
	      }

	      // to make sure that all streams are finished

	      try {
	        outThread.join();
	      }
	      catch (InterruptedException ex) {
	      }
	      try {
	        errThread.join();
	      }
	      catch (InterruptedException ex) {
	      }

	      // check if an exception is raised on writing thread

	      if (outPump.getException() != null) {
	        throw outPump.getException();
	      }
	      if (errPump.getException() != null) {
	        throw errPump.getException();
	      }

	      out = outBuffer.toByteArray();
	      if (out.length == 0) {
	        out = null;
	      }
	      err = errBuffer.toByteArray();
	      if (err.length == 0) {
	        err = null;
	      }
	   }




	    public int getStatus() {
	      return status;
	    }
	    public byte[] getOut() {
	      return out;
	    }
	    public byte[] getErr() {
	      return err;
	    }
	  }


	  

