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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;


public class PlatformDetection {
  private String os;

  private String arch;

  public static String OS_WINDOWS = "windows";

  public static String OS_OSX = "osx";

  public static String OS_SOLARIS = "solaris";

  public static String OS_LINUX = "linux";

  public static String ARCH_PPC = "ppc";

  public static String ARCH_X86_32 = "x86_32";

  public static String ARCH_X86_64 = "x86_64";

  public PlatformDetection() {
    // resolve OS
    if (SystemUtils.IS_OS_WINDOWS) {
      this.os = OS_WINDOWS;
    } else if (SystemUtils.IS_OS_MAC_OSX) {
      this.os = OS_OSX;
    } else if (SystemUtils.IS_OS_SOLARIS) {
      this.os = OS_SOLARIS;
    } else if (SystemUtils.IS_OS_LINUX) {
      this.os = OS_LINUX;
    } else {
      throw new IllegalArgumentException("Unknown operating system " + SystemUtils.OS_NAME);
    }

    // resolve architecture
    Map<String, String> archMap = new HashMap<String, String>();
    archMap.put("x86", ARCH_X86_32);
    archMap.put("i386", ARCH_X86_32);
    archMap.put("i486", ARCH_X86_32);
    archMap.put("i586", ARCH_X86_32);
    archMap.put("i686", ARCH_X86_32);
    archMap.put("x86_64", ARCH_X86_64);
    archMap.put("amd64", ARCH_X86_64);
    archMap.put("powerpc", ARCH_PPC);
    this.arch = archMap.get(SystemUtils.OS_ARCH);
    if (this.arch == null) {
      throw new IllegalArgumentException("Unknown architecture " + SystemUtils.OS_ARCH);
    }
  }

  public String getOs() {
    return os;
  }

  public String getArch() {
    return arch;
  }

  public void setArch(String arch) {
    this.arch = arch;
  }

  public void setOs(String os) {
    this.os = os;
  }

  @Override
  public String toString() {

    return os + "_" + arch;
  }

  public String getExecutableSuffix() {
    if (getOs().equals(OS_WINDOWS))
      return ".exe";
    return "";
  }
}
