/**
 * Copyright 2011 Jason Ferguson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jason.mapmaker.server.repository;

import org.jason.mapmaker.shared.model.MTFCC;

import java.util.List;
import java.util.Map;

/**
 * Interface for MTFCC Repository
 *
 * @since 0.1
 * @author Jason Ferguson
 */
public interface MtfccRepository extends GenericRepository2<MTFCC> {

    /**
     * A Map<String, String> of all MTFCC types mapped MTFCC Code<->Name
     *
     * @return
     */
    Map<String, String> getMtfccTypes();

    /**
     * Return a Map of MTFCC types (starting with G) and the number of Features of that type
     *
     * @return  a Map<MTFCC, Long>
     */
    Map<MTFCC, Long> getMtfccFeatureCount();

    /**
     * Overriden getAll()
     *
     * @return  List<MTFCC> of all mtfccs for Tabulation Areas
     */
    List<MTFCC> getAll();
}
