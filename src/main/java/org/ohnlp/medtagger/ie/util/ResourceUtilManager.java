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
package org.ohnlp.medtagger.ie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * 
 * Abstract class for all Resource Managers to inherit from. Contains basic
 * functionality such as file system access and some private members.
 *
 */
public class ResourceUtilManager {

	public static String RESOURCEDIR;
	private static ResourceUtilManager INSTANCE = null;
	
	private Logger iv_logger = Logger.getLogger(getClass().getName());
	
	private Pattern regexpPattern = Pattern.compile("(.*)");
	private Pattern normPattern = Pattern.compile("^(.*?)\t(.*?)$");
	private Pattern rulePattern = Pattern.compile("RULENAME=\"(.*?)\",REGEXP=\"(.*?)\",LOCATION=\"(.*?)\",NORM=\"(.*?)\"(.*)");
	
	private TreeMap <String, String>hmRegExpEntries = new TreeMap<String, String>(); //regular expression
	private HashMap<String, HashMap<String,String>> hmNormEntries = new HashMap<String, HashMap<String,String>>(); //normalization

	HashMap<Pattern, String> hmRulePattern     = new HashMap<Pattern, String>(); // patterns in rules
	HashMap<String, String> hmRuleNormalization = new HashMap<String, String>(); // normalization target in rules
	HashMap<String, String> hmRuleLocation     = new HashMap<String, String>(); // location of the patterns

	public static ResourceUtilManager getInstance() {
		if(ResourceUtilManager.INSTANCE == null)
			ResourceUtilManager.INSTANCE = new ResourceUtilManager(RESOURCEDIR);	
		return ResourceUtilManager.INSTANCE;
	}

	ClassLoader classLoader = null;
	
	public ResourceUtilManager(String resourcedir) {
		try {
			Class cls = Class.forName("org.ohnlp.medtagger.ie.util.ResourceUtilManager");
			classLoader = cls.getClassLoader();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}

		// returns the ClassLoader object associated with this Class

		RESOURCEDIR = resourcedir;
		readResources(readResourcesFiles("norm"), normPattern, "norm");
		readResources(readResourcesFiles("regexp"), regexpPattern, "regexp");
		reformatRegExp();
		readResources(readResourcesFiles("rules"), rulePattern, "rules");	
	}

	/**
	 * Reads resource files of the type resourceType from the "used_resources.txt" file and returns a HashMap
	 * containing information to access these resources.
	 * @return HashMap containing filename/path tuples
	 */
	protected HashMap<String, String> readResourcesFiles(String resourceType) {

		HashMap<String, String> hmResources = new HashMap<>();
		
		try{
			String resourcefile =  RESOURCEDIR + "/used_resources.txt";

			InputStream inputStream = Files.newInputStream(Paths.get(resourcefile));

			if(inputStream == null){
				throw new IOException(resourcefile);
			}

			Scanner sc = new Scanner(inputStream);

	 		while (sc.hasNextLine()) {
	 			String line = sc.nextLine();
	 			Pattern paResource = Pattern.compile("\\./"+resourceType+"/resources_"+resourceType+"_"+"(.*?)\\.txt");
	 			for (Object r : findMatches(paResource, line)){
	 				MatchResult ro=(MatchResult) r;
	 				String foundResource  = ro.group(1);
	 				String pathToResource = RESOURCEDIR+"/"+resourceType+"/resources_"+resourceType+"_"+foundResource+".txt";
	 				hmResources.put(foundResource, pathToResource);
	 			}
	 		}
	 		sc.close();
		} catch (IOException e) {
			// TODO: better Exception handling
			e.printStackTrace();
			iv_logger.warn("Failed to read a resource from used_resources.txt.");
			System.exit(-1);
		}
		return hmResources;
	}
	
	private void readResources(HashMap<String, String> hmResources, Pattern p, String resourceType) {
		
		try {
			
			for (String resource : hmResources.keySet()) {
				iv_logger.info("Adding "+resourceType+" from resource: "+resource);

				InputStream inputStream = Files.newInputStream(Paths.get(hmResources.get(resource)));

				if(inputStream == null){
					throw new IOException(hmResources.get(resource));
				}

				Scanner sc = new Scanner(inputStream);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					if (!line.startsWith("//") && ! line.equals("")) {
						boolean correctLine = false;
						iv_logger.debug("Reading "+resource+" at line: "+ line);
						for (Object r : findMatches(p, line)) {
							MatchResult mr = (MatchResult) r;
							correctLine = true;
							if(resourceType.equals("norm")) {
								String resource_word   = mr.group(1);
								String normalized_word = mr.group(2);
								boolean flag=true;
								for (Object key: hmNormEntries.keySet()) {
									String entry=(String) key;						
									if (resource.equals(entry)) {
										hmNormEntries.get(key).put(resource_word,normalized_word);
										flag=false;
									}
								}
								if(flag) {
									  HashMap <String, String> lhm=new HashMap <String, String>();
									  lhm.put(resource_word, normalized_word);
									  hmNormEntries.put(resource,lhm);
								}
							}
						   if(resourceType.equals("regexp")) {
							   String regexp_entry=mr.group(1);
							   if(regexp_entry.equals("")) continue;
							   boolean flag=true;
							   for (Object lentry : hmRegExpEntries.keySet()) {
							    String entry=(String) lentry;
								if (resource.equals(entry)) {
									String oldentries = (String) hmRegExpEntries.get(entry);
									oldentries = oldentries + "|" + regexp_entry;
									flag=false;
									hmRegExpEntries.put(entry, oldentries);
								}
							   }
								if(flag) hmRegExpEntries.put(resource,regexp_entry);
						   }
						   if(resourceType.equals("rules")){
							 	String rule_name          = mr.group(1);
								String rule_extraction    = mr.group(2);
								String rule_location	  = mr.group(3);
								String rule_normalization = mr.group(4);
								Pattern paVariable = Pattern.compile("%(re[a-zA-Z0-9]*)");
								for (Object o1 : findMatches(paVariable,rule_extraction)) {
									MatchResult mr1 = (MatchResult) o1;
									iv_logger.debug("Replacing patterns..."+ mr1.group());
									if (!(hmRegExpEntries.containsKey(mr1.group(1)))) {
										iv_logger.error("Error creating rule:"+rule_name);
										iv_logger.error("The pattern may not exist : "+mr1.group(1));
										System.exit(-1);
									}
									rule_extraction = rule_extraction.replaceAll("%"+mr1.group(1), getRegExp(mr1.group(1)));
								}
								rule_extraction = rule_extraction.replaceAll(" ", "[\\\\s]+");
								Pattern pattern = null;
								try{
									pattern = Pattern.compile(rule_extraction);
								}
								catch (java.util.regex.PatternSyntaxException e) {
									iv_logger.error("Cannot compile pattern in "+rule_name+": "+rule_extraction);
										e.printStackTrace();
										System.exit(-1);
									}

								if (resource.equals("matchrules")) {
									hmRulePattern.put(pattern, rule_name);// get pattern part
									hmRuleNormalization.put(rule_name, rule_normalization);	//get normalization part
									hmRuleLocation.put(rule_name,rule_location); // get location part 
								} 
						}
						if ((correctLine == false) && (!(line.matches("")))) {
							iv_logger.error("Cannot read one of the lines of "+resource+" at Line: "+line);
						}
					}
				}
			}			
			sc.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public final Boolean containsRegExpKey(String key) {
		return hmRegExpEntries.containsKey(key);
	}

	public final String getRegExp(String key) {
		return (String) hmRegExpEntries.get(key);
	}
	
	public final HashMap<Pattern, String> getHmRulePattern() {
		return hmRulePattern;
	}

	
	public final HashMap<String, String> getHmRuleNormalization() {
		return hmRuleNormalization;
	}
	
	public final HashMap<String, String> getHmRuleLocation() {
		return hmRuleLocation;
	}
	
	public final HashMap<String, String> getHmNormEntry(String key) {
		return hmNormEntries.get(key);
	}
	
	 
	private void reformatRegExp() {
		 for (Object lkey : hmRegExpEntries.keySet()) {
			    String key=(String) lkey;
			    String regexp = (String) hmRegExpEntries.get(key);				
		regexp = regexp.replaceAll("\\|\\|", "\\|");
		regexp = regexp.replaceAll("\\(([^\\?])", "(?:$1");
		regexp = regexp.replace("\\|$", "");
		regexp = "(" + regexp + ")";
		regexp = regexp.replaceAll("\\\\", "\\\\\\\\");
		hmRegExpEntries.put(key, regexp);
	}
}
	
	public static Iterable<MatchResult> findMatches(Pattern pattern, CharSequence s) {
		List<MatchResult> results = new ArrayList();

		for (Matcher m = pattern.matcher(s); m.find();)
			results.add(m.toMatchResult());

		return results;
	}
	
	public static List<Pattern> sortByValue(final HashMap<Pattern,String> m) {
        List<Pattern> keys = new ArrayList();
        keys.addAll(m.keySet());
        Collections.sort(keys, new Comparator<Object>() {
        	public int compare(Object o1, Object o2) {
                Object v1 = m.get(o1);
                Object v2 = m.get(o2);
                if (v1 == null) {
                    return (v2 == null) ? 0 : 1;
                } else if (v1 instanceof Comparable) {
                    return ((Comparable) v1).compareTo(v2);
                } else {
                    return 0;
                }
            }
        });
        return keys;
    }
}
