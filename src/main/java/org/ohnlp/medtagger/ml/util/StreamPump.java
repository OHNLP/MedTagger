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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamPump implements Runnable {
	    private BufferedInputStream in;
	    private BufferedOutputStream out;
	    private IOException exception;
	    public StreamPump(InputStream in, OutputStream out) {
	      this.in = new BufferedInputStream(in);
	      this.out = new BufferedOutputStream(out);
	    }
	    public void run() {
	      try {
	        int b;
	        while ((b = in.read()) != -1) {
	          out.write(b);
	        }
	        in.close();
	        out.close();
	      }
	      catch (IOException ex) {
	        exception = ex;
	      }
	    }
	    public IOException getException() {
	      return exception;
	    }
}

