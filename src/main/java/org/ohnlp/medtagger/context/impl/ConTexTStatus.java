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

package org.ohnlp.medtagger.context.impl;

/**
 * Marks the status of the given context
 */
public class ConTexTStatus {
    /**
     * Indicates that this context status is pseudo'd, and should not be used for a trigger
     */
    public boolean isPseudo;

    /**
     * Positive vs. Negated
     */
    public boolean isPositive;
    /**
     * Asserted vs. Possible
     */
    public boolean isAsserted;
    /**
     * Present vs. Historical
     */
    public boolean isPresent;
    /**
     * Not experiencer vs experiencer
     */
    public boolean experiencerIsPatient;

    // Terminals
    public boolean isNegationTerminal;
    public boolean isPossibleTerminal;
    public boolean isHypotheticalTerminal;
    public boolean isHistoricalTerminal;
    public boolean isExperiencerTerminal;

    // Trigger terms
    public boolean isNegationTrigger;
    public boolean isPossibleTrigger;
    public boolean isHypotheticalTrigger;
    public boolean isHistoricalTrigger;
    public boolean isExperiencerTrigger;
    public boolean isHypothetical;

    public ConTexTStatus() {
        this.isPositive = true;
        this.isAsserted = true;
        this.isPresent = true;
        this.experiencerIsPatient = true;
        this.isHypothetical = false;
        this.isHistoricalTerminal = false;
        this.isHypotheticalTerminal = false;
        this.isNegationTerminal = false;
        this.isExperiencerTerminal = false;
        this.isNegationTrigger = false;
        this.isHypotheticalTrigger = false;
        this.isHistoricalTrigger = false;
        this.isExperiencerTrigger = false;
        this.isPseudo = false;
    }
}