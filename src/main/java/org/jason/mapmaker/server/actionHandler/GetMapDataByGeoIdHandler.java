/**
 * Copyright 2011 Jason Ferguson.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jason.mapmaker.server.actionHandler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.FeatureService;
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.shared.action.GetMapDataByGeoIdAction;
import org.jason.mapmaker.shared.model.Feature;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.result.GetMapDataByGeoIdResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * GWTP ActionHandler class to retrieve the data needed to draw a map by providing the geoid of the feature to create
 * the map for.
 *
 * @since 0.2
 * @author Jason Ferguson
 */
public class GetMapDataByGeoIdHandler extends AbstractActionHandler<GetMapDataByGeoIdAction, GetMapDataByGeoIdResult> {

    private LocationService locationService;
    private FeatureService featureService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    public GetMapDataByGeoIdHandler() {
        super(GetMapDataByGeoIdAction.class);
    }

    @Override
    public GetMapDataByGeoIdResult execute(GetMapDataByGeoIdAction action, ExecutionContext context) throws ActionException {

        Location location = locationService.getByCompleteGeoId(action.getGeoId());
        if (location == null) {
            throw new ActionException("Location is null");
        }

        // now that we have the bounding box, we can get the features associated with it
        List<Feature> featureList = featureService.getFeatures(location, action.getFeatureClassName());
        location.setFeatureList(featureList);

        return new GetMapDataByGeoIdResult(location.getBoundingBox(), location);
    }

    @Override
    public void undo(GetMapDataByGeoIdAction action, GetMapDataByGeoIdResult result, ExecutionContext context) throws ActionException {
        // no-op
    }
}
