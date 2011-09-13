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
package org.jason.mapmaker.shared.result.location;

import com.gwtplatform.dispatch.shared.Result;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;

import java.util.Map;

/**
 * GWTP Result class for returning a Map<MTFCC, Location> which was initially requested by longitude and latitude
 *
 * @since 0.5.1
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
public class GetLocationMapByCoordinatesResult implements Result {

    private Map<MTFCC, Location> results;

    // for serialization only; do not use
    public GetLocationMapByCoordinatesResult() {
    }

    public GetLocationMapByCoordinatesResult(Map<MTFCC, Location> results) {
        this.results = results;
    }

    public Map<MTFCC, Location> getResults() {
        return results;
    }
}
