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
import org.jason.mapmaker.shared.result.location.GetLocationMapByCoordinatesResult;

/**
 * GWTP Action class for requesting a Map<MTFCC, Location> by longitude and latitude
 *
 * @since 0.5.1
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
public class GetLocationMapByCoordinatesAction extends UnsecuredActionImpl<GetLocationMapByCoordinatesResult> {

    private double longitude;
    private double latitude;

    // for serialization only; do not use
    public GetLocationMapByCoordinatesAction() {
    }

    public GetLocationMapByCoordinatesAction(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
