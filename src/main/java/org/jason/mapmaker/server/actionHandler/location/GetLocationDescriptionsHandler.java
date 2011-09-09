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
package org.jason.mapmaker.server.actionHandler.location;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.shared.action.location.GetLocationDescriptionsAction;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.result.location.GetLocationDescriptionsResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWTP ActionHandler for getting location descriptions for a given point.
 *
 * @since 0.5.0
 * @author Jason Ferguson
 */
public class GetLocationDescriptionsHandler extends AbstractActionHandler<GetLocationDescriptionsAction, GetLocationDescriptionsResult>{

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetLocationDescriptionsHandler() {
        super(GetLocationDescriptionsAction.class);
    }

    @Override
    public GetLocationDescriptionsResult execute(GetLocationDescriptionsAction action, ExecutionContext context) throws ActionException {

        Map<String, Location> resultMap = locationService.getLocationDescriptionsForCoordinates(action.getLng(), action.getLat());

        return new GetLocationDescriptionsResult(resultMap);
    }

    @Override
    public void undo(GetLocationDescriptionsAction action, GetLocationDescriptionsResult result, ExecutionContext context) throws ActionException {
        // no-op
    }
}
