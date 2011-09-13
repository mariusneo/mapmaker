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
package org.jason.mapmaker.server.service;

import org.jason.mapmaker.shared.model.MTFCC;

import java.util.List;
import java.util.Map;

/**
 * MtfccService.java
 *
 * Defines operations for the MTFCC Service
 *
 * @author Jason Ferguson
 */
public interface MtfccService {

    /**
     * Return an MTFCC object by its code (i.e. "G4020" for a county)
     *
     * @param code  code for the MTFCC object to return
     * @return      an MTFCC object with the given code, or null
     */
    MTFCC get(String code);

    /**
     * Return a Map<String, String> representing all MTFCC codes mapped to the given description
     *
     * @return  a Map<String, String> with all available MTFCC codes mapped to their description
     */
    Map<String, String> getMtfccTypes();

    Map<MTFCC, Long> getMtfccFeatureCount();

    List<MTFCC> getAll();
}
