/*
 * Copyright: (c)  2019  Mayo Foundation for Medical Education and
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ohnlp.medtagger.context.impl.structs;


import java.util.HashMap;

/**
 * Implementation of a trie datastructure to compress terms lists.
 * TODO: note: as of initial release usage of this trie is not compatible with regexp statements, they will be simply treated as terms!
 */
public class KeywordTrie {
    private TrieNode rootCaseInsensitive;

    public KeywordTrie() {
        rootCaseInsensitive = new TrieNode("");
    }

    public void insert(String word) {
        TrieNode current = rootCaseInsensitive;
        for (int i = 0; i < word.length(); i++) {
            current = current.getChildren()
                    .computeIfAbsent(word.charAt(i), c -> c == ' ' || c == '-' ? new WhitespaceEquivalentTrieNode() : new TrieNode(c + ""));
        }
        current.setTerminal(true);
    }

    /**
     * Compresses the trie into a regular expression compatible alternation
     *
     * @return
     */
    public String compress() {
        boolean hasCaseInsensitive = rootCaseInsensitive.getChildren().size() > 0;
        if (hasCaseInsensitive) {
            return "(?:" + rootCaseInsensitive.compress() + ")";
        } else {
            return "";
        }
    }

    private static class TrieNode {
        protected HashMap<Character, TrieNode> children;
        private String content;
        private boolean terminal;

        public TrieNode(String content) {
            this.content = content.replaceAll("([(\\\\.)\\[\\]^$+?*])", "\\\\$1");
            this.children = new HashMap<>();
            this.terminal = false;
        }

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

        public void setChildren(HashMap<Character, TrieNode> children) {
            this.children = children;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isTerminal() {
            return terminal;
        }

        public void setTerminal(boolean isTerminal) {
            this.terminal = isTerminal;
        }

        /**
         * Compresses this node and it's children into a regex-compatible alternation
         *
         * @return A regexp compatible alternation
         */
        public String compress() {
            StringBuilder regexpBuilder = new StringBuilder();
            if (children.isEmpty() && terminal) {
                regexpBuilder.append(content);
            }
            if (!children.isEmpty()) {
                boolean first = true;
                StringBuilder childBuilder = new StringBuilder();
                boolean multipleAppends = false;
                for (TrieNode child : children.values()) {
                    String compressedChild = child.compress();
                    if (compressedChild.length() > 0) {
                        if (first) {
                            first = false;
                        } else {
                            childBuilder.append("|");
                            multipleAppends = true;
                        }
                        childBuilder.append(compressedChild);
                    }
                }
                // At least one item was written
                if (!first) {
                    // This can be a terminal node (i.e. word ends here), so add the item and repeat w/ children later as an alternation
                    if (terminal) {
                        regexpBuilder.append("(?:");
                    }
                    regexpBuilder.append(content);
                    if (multipleAppends) {
                        regexpBuilder.append("(?:").append(childBuilder.toString()).append(")");
                    } else {
                        regexpBuilder.append(childBuilder.toString());
                    }
                    if (terminal) {
                        regexpBuilder.append("|").append(content).append(")");
                    }
                }
            }
            return regexpBuilder.toString();
        }
    }

    private static class WhitespaceEquivalentTrieNode extends TrieNode {

        public WhitespaceEquivalentTrieNode() {
            super(" "); // Whitespaces cannot be terminal
        }

        /**
         * Compresses this node and it's children into a regex-compatible alternation
         *
         * @return A regexp compatible alternation
         */
        @Override
        public String compress() {
            StringBuilder regexpBuilder = new StringBuilder();
            if (!children.isEmpty()) {
                boolean first = true;
                StringBuilder childBuilder = new StringBuilder();
                boolean multipleAppends = false;
                for (TrieNode child : children.values()) {
                    String compressedChild = child.compress();
                    if (compressedChild.length() > 0) {
                        if (first) {
                            first = false;
                        } else {
                            childBuilder.append("|");
                            multipleAppends = true;
                        }
                        childBuilder.append(compressedChild);
                    }
                }
                // At least one item was written
                if (!first) {
                    // This can be a terminal node (i.e. word ends here), so add the item and repeat w/ children later as an alternation
                    regexpBuilder.append("[\\s\\\\/-]+");
                    if (multipleAppends) {
                        regexpBuilder.append("(?:").append(childBuilder.toString()).append(")");
                    } else {
                        regexpBuilder.append(childBuilder.toString());
                    }
                }
            }
            return regexpBuilder.toString();
        }
    }
}