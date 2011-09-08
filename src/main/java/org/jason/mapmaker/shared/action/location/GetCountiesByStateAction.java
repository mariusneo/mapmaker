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
package org.jason.mapmaker.shared.action.location;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import org.jason.mapmaker.shared.result.location.GetCountiesByStateResult;

/**
 * GWTP Action class for obtaining a Map<String, String> of county FIPS ids mapped to their name for a given state
 * FIPS ID
 *
 * While it's possible to wrap this into a more generic action to get all feature names for any state-based MTFCC,
 * I'm going to call county a special case since it has its own set of subfeatures.
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class GetCountiesByStateAction extends UnsecuredActionImpl<GetCountiesByStateResult> {

    private String stateFP;

    // for serialization only; do not use
    public GetCountiesByStateAction() {
    }

    public GetCountiesByStateAction(String stateFP) {
        this.stateFP = stateFP;
    }

    public String getStateFP() {
        return stateFP;
    }
}
