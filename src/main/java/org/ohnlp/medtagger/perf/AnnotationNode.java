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

package org.ohnlp.medtagger.perf;


import org.apache.uima.cas.text.AnnotationFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public final class AnnotationNode implements AnnotationIndex {

    private AnnotationIndex left;
    private AnnotationIndex right;
    private int split;

    AnnotationNode(int start, int end) {
        split = (start + end) / 2;
        if ((split - start) > MIN_LEAF_SIZE) {
            left = new AnnotationNode(start, split);
            right = new AnnotationNode(split + 1, end);
        } else {
            left = new AnnotationLeaf();
            right = new AnnotationLeaf();
        }
    }

    @Override
    public void insert(AnnotationFS ann) {
        if (ann.getBegin() <= split) {
            left.insert(ann);
        }
        if (ann.getEnd() > split) {
            right.insert(ann);
        }
    }

    @Override
    public void remove(AnnotationFS ann) {
        if (ann.getBegin() <= split) {
            left.remove(ann);
        }
        if (ann.getEnd() > split) {
            right.remove(ann);
        }
    }

    @Override
    public <T extends AnnotationFS> List<T> getCovering(int start, int end, Class<T> clazz) {
        LinkedList<T> build = new LinkedList<T>();
        HashSet<T> set = new HashSet<T>();
        if (start <= split) {
            for (T ann : left.getCovering(start, end, clazz)) {
                if (set.add(ann)) {
                    build.add(ann);
                }
            }
        }
        if (end > split) {
            for (T ann : right.getCovering(start, end, clazz)) {
                if (set.add(ann)) {
                    build.add(ann);
                }
            }
        }
        return build;
    }

    @Override
    public <T extends AnnotationFS> List<T> getCovered(int start, int end, Class<T> clazz) {
        LinkedList<T> build = new LinkedList<T>();
        HashSet<T> set = new HashSet<T>();
        if (start <= split) {
            for (T ann : left.getCovered(start, end, clazz)) {
                if (set.add(ann)) {
                    build.add(ann);
                }
            }
        }
        if (end > split) {
            for (T ann : right.getCovered(start, end, clazz)) {
                if (set.add(ann)) {
                    build.add(ann);
                }
            }
        }
        return build;
    }

    @Override
    public <T extends AnnotationFS> List<T> getCollisions(int start, int end, Class<T> clazz) {
        LinkedList<T> build = new LinkedList<T>();
        HashSet<T> set = new HashSet<T>();
        if (start <= split) {
            for (T ann : left.getCollisions(start, end, clazz)) {
                if (set.add(ann)) {
                    build.add(ann);
                }
            }
        }
        if (end > split) {
            for (T ann : right.getCollisions(start, end, clazz)) {
                if (set.add(ann)) {
                    build.add(ann);
                }
            }
        }
        return build;
    }

    @Override
    public void grow(int size) {
        throw new UnsupportedOperationException("Growth of an annotation index should be accomplished through the AnnotationRoot");
    }

    // Accessor method to allow for growth operations
    void setLeft(AnnotationIndex left) {
        this.left = left;
    }

    @Override
    public void clear() {
        this.left.clear();
        this.right.clear();
        this.left = null;
        this.right = null;
    }
}