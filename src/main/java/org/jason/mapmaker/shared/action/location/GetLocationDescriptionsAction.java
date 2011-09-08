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
import org.jason.mapmaker.shared.result.location.GetLocationDescriptionsResult;

/**
 * @since 0.4.3
 * @author Jason Ferguson
 */
public class GetLocationDescriptionsAction extends UnsecuredActionImpl<GetLocationDescriptionsResult> {

    private double lng;
    private double lat;

    public GetLocationDescriptionsAction() {
    }

    public GetLocationDescriptionsAction(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }
}
