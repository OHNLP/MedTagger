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

import java.util.List;

public final class AnnotationRoot implements AnnotationIndex {
    private int start;
    private int end;
    private AnnotationIndex child;

    public AnnotationRoot() {
        // This guarantees that we only need to grow to the right. While this does come at a potential hit on
        // performance in some cases (where there are no annotations in say the first half of the document,
        // testing of sample data suggests that the cases where such is the case is relatively rare
        // compared to the added cost of a start check and leftwards growth, especially as annotations
        // tend to be inserted in sequential order anyways
        start = 0;
        end = MIN_LEAF_SIZE;
        child = new AnnotationLeaf();
    }


    @Override
    public void insert(AnnotationFS ann) {
        if (ann.getEnd() > end) {
            grow(ann.getEnd());
        }
        child.insert(ann);
    }

    @Override
    public void remove(AnnotationFS ann) {
        child.remove(ann);
    }

    @Override
    public <T extends AnnotationFS> List<T> getCovering(int start, int end, Class<T> clazz) {
        return child.getCovering(start, end, clazz);
    }

    @Override
    public <T extends AnnotationFS> List<T> getCovered(int start, int end, Class<T> clazz) {
        return child.getCovered(start, end, clazz);
    }

    @Override
    public <T extends AnnotationFS> List<T> getCollisions(int start, int end, Class<T> clazz) {
        return child.getCollisions(start, end, clazz);
    }

    @Override
    public void grow(int size) {
        // We only grow to the right, since our starting index is always 0.
        while (size > end) {
            end = end * 2;
            AnnotationNode temp = new AnnotationNode(start, end);
            temp.setLeft(child);
            child = temp;
        }
    }

    @Override
    public void clear() {
        child.clear();
    }
}
