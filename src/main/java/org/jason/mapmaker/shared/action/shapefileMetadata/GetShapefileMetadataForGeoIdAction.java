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
package org.jason.mapmaker.shared.action.shapefileMetadata;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import org.jason.mapmaker.shared.result.shapefileMetadata.GetShapefileMetadataForGeoIdResult;

/**
 * @author Jason Ferguson
 */
public class GetShapefileMetadataForGeoIdAction extends UnsecuredActionImpl<GetShapefileMetadataForGeoIdResult> {

    private String geoId;

    // for serialization only, do not use
    public GetShapefileMetadataForGeoIdAction() {
    }

    public GetShapefileMetadataForGeoIdAction(String geoId) {
        this.geoId = geoId;
    }

    public String getGeoId() {
        return geoId;
    }
}
