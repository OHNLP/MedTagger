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
 package org.ohnlp.medtagger.dict;


/**
 * AhoCorasick.java
 *
 * @author Hongfang Liu
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;


public class AhoCorasickDict {

	// LOG4J logger based on class name
   private Logger iv_logger = Logger.getLogger(getClass().getName());
	
	public static final String FAILLINK="_FAIL_";
	public static final String SLDELIM="||";
	public static final String DLDELIM="|||";
	public static final String DELIMITER="\\t|\\s";
	public static final String DICTDATADELIM="|";
	public static final String LINEDELIM="\\n";
	public Node root;

	AhoCorasickDict(){
		root=new Node(null);
		root.addChild(FAILLINK, root);
	}

	public AhoCorasickDict(String dictfile){
		root=new Node(null);
		root.addChild(FAILLINK, root);
		iv_logger.info("building aho-corasick trie from: "+dictfile);
		try{
			BufferedReader reader = new BufferedReader
					(new InputStreamReader
							(new FileInputStream(dictfile)));
			String line;
			int count=0;
			while ((line = reader.readLine()) != null) {
				count++;
				if(count%100000==0) iv_logger.info(count);
				int index=line.indexOf(DICTDATADELIM);
				if(index>=0){
					String key=line.substring(0, index);
					String data=line.substring(index+DICTDATADELIM.length());
					put(key,data);
				}
			}
			reader.close();
			faillinking();
			iv_logger.info("built aho-corasick trie. "+ count +" terms");
		}
		catch (IOException ioe){
			ioe.printStackTrace();			
		}	
	}

	class Node{

		public HashMap <String, Node> children=new HashMap <String, Node> ();
		public String phrase = null;
		public String label=null;
		public Node parent=null;

		Node(Node fl) {
			phrase=null;
			label=null;
			children.put(FAILLINK,fl);
		}

		Node getChild(String key){
			return children.get(key);   
		}



		void addChild(String key, Node child){
			children.put(key, child);
			child.parent=this;
		}

		int getChildrenSize(){
			return children.size();
		}

		public String toString(){
			return "Phrase:"+phrase+" Label:"+label;
		}
	}


	public void put(String phr, String data){
		String[] tokens=phr.split(DELIMITER);
		put(phr, tokens, 0, data, root);
	}


	Node put(String phr, String[] tokens, int i, String data, Node node){

		if(i==tokens.length){
			if(node.phrase==null) {
				node.phrase=phr; 
				node.label=data;
			}
			else node.label+=SLDELIM+data;
			return node;
		}
		Node child=node.getChild(tokens[i]);
		if(child==null){
			child = new Node(root);
			node.addChild(tokens[i], child);
		}
		return put(phr, tokens, i+1, data, child);
	}

	public String find(String text){
		String[] tokens=text.split(DELIMITER);
		ArrayList<Vector<String>> tags=new ArrayList<Vector<String>>(tokens.length);
		for(int i=0; i<tokens.length; i++){
			tags.add(new Vector<String>());
		}
		find(tokens, 0, root, tags);
		String output=new String();
		for(int i=0; i<tokens.length; i++){
			output+=tokens[i]+"::";
			for(int j=0; j<((Vector<String>) tags.get(i)).size(); j++)
				output+=((Vector<String>) tags.get(i)).get(j)+DLDELIM;
			output+=" ";
		}
		return output;
	}

	String create_tag(int len, String data){
		return len+"::"+data;
	}

	public void find(String[] tokens, int i, Node node, ArrayList<Vector<String>> tags){

		if(node.phrase!=null) {
			String[] tks=node.phrase.split(DELIMITER);
			int len=tks.length;
			String tag=create_tag(len, node.label);
			Vector<String> currentTags=tags.get(i-len);
			currentTags.add(tag);
			tags.set(i-len, currentTags);
		}
		if(i>tokens.length) return;
		String token;
		if(i==tokens.length) token="DUMMYWORD";
		else token=tokens[i];
		Node child=node.getChild(token);
		if(child==null) {
			child=node.getChild(FAILLINK);
			if(node!=root) {
				find(tokens, i, child, tags);
				return;
			}
		}
		else{
			Node n=node.getChild(FAILLINK);
			while(n!=root){
				if(n.phrase!=null){
					String[] tks=n.phrase.split(DELIMITER);
					int len=tks.length;
					String tag=create_tag(len, n.label);
					Vector<String> currentTags;
					if(tags.get(i-len)==null) currentTags=new Vector<String>();
					else currentTags=tags.get(i-len);
					currentTags.add(tag);
					tags.set(i-len, currentTags);
				}
				n=n.getChild(FAILLINK);
			}
		}
		find(tokens, i+1, child, tags);
		return;
	}

	void faillinking(){
		LinkedList<Node> ll=new LinkedList<Node> ();
		ll.add(root);
		while(ll.size()>0){
			Node node=(Node)ll.getFirst();
			ll.remove(0);
			HashMap<String, Node> hm=node.children;
			Set<String> keys=hm.keySet();
			Iterator<String> it=keys.iterator();
			while(it.hasNext()){
				String key=(String) it.next();
				if(key==FAILLINK) continue;
				Node child=node.getChild(key);
				ll.add(child);
				if(node==root) continue;
				Node fail=node.getChild(FAILLINK);
				while(fail!=root && fail.getChild(key)==null) fail=fail.getChild(FAILLINK);
				if(fail.getChild(key)!=null) node.getChild(key).addChild(FAILLINK, fail.getChild(key));	    		          
			}
		}		
	}
}



