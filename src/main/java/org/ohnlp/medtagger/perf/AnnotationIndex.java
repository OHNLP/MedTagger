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

/**
 * The annotation index is a storage structure for {@link AnnotationFS} objects. As they are tied to individual
 * {@link org.apache.uima.cas.CAS} objects, which are in and of themselves not-threadsafe, implementations
 * of this class similarly need not be threadsafe.
 * <p>
 * The algorithmic implementation of collision and positional checking for the index is extremely similar to that of
 * an axis-aligned bounding box
 *
 * @author Andrew Wen
 */
public interface AnnotationIndex {

    /**
     * The size at which a leaf should be constructed in the tree as opposed to the node
     */
    int MIN_LEAF_SIZE = 20;

    /**
     * Adds a new Annotation to the index
     *
     * @param ann The annotation to add
     */
    void insert(AnnotationFS ann);

    /**
     * Removes an annotation from the index
     *
     * @param ann The annotation to remove
     */
    void remove(AnnotationFS ann);

    /**
     * @param start The starting bound character position
     * @param end   The end bound character position
     * @param clazz A class definition for the Annotation type to retrieve
     * @param <T>   An implementation of {@link AnnotationFS}
     * @return An ordered set of T in list form constrained by the given bounds ordered by starting position
     */
    <T extends AnnotationFS> List<T> getCovering(int start, int end, Class<T> clazz);

    /**
     * @param start The starting bound character position
     * @param end   The end bound character position
     * @param clazz A class definition for the Annotation type to retrieve
     * @param <T>   An implementation of {@link AnnotationFS}
     * @return An ordered set of T in list form constraining the given bounds ordered by starting position
     */
    <T extends AnnotationFS> List<T> getCovered(int start, int end, Class<T> clazz);

    /**
     * @param start The starting bound character position
     * @param end   The end bound character position
     * @param clazz A class definition for the Annotation type to retrieve
     * @param <T>   An implementation of {@link AnnotationFS}
     * @return An ordered set of T in list form that intersect with the supplied bounds ordered by starting position
     */
    <T extends AnnotationFS> List<T> getCollisions(int start, int end, Class<T> clazz);

//    /**
//     * See: {@link org.apache.uima.fit.util.JCasUtil#indexCovered(JCas, Class, Class)}
//     * @param type The type to create an index for
//     * @param coveredType The type of annotation covered to search for
//     * @param <T> The covering type
//     * @param <S> The covered type
//     * @return A map of T to a collection of S that they cover
//     */
//    <T extends AnnotationFS, S extends AnnotationFS> Map<T,Collection<S>> indexCovered(Class<? extends T> type, Class<? extends S> coveredType);
//
//    /**
//     * See: {@link org.apache.uima.fit.util.JCasUtil#indexCovering(JCas, Class, Class)}
//     * @param type The type to create an index for
//     * @param coveredType The type of annotation covered to search for
//     * @param <T> The covering type
//     * @param <S> The covered type
//     * @return A map of T to a collection of S that they cover
//     */
//    <T extends AnnotationFS, S extends AnnotationFS> Map<T,Collection<S>> indexCovering(Class<? extends T> type, Class<? extends S> coveredType);

    // TODO add other JCasUtil interceptions

    /**
     * Grows the index until it can contain a character count of a given size
     *
     * @param size The size to grow to (the actual end size may be larger)
     */
    void grow(int size);

    /**
     * Empties the index of its contents
     */
    void clear();
}
