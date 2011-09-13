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
import org.jason.mapmaker.shared.action.location.GetLocationMapByCoordinatesAction;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;
import org.jason.mapmaker.shared.result.location.GetLocationMapByCoordinatesResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWTP Action Handler for obtaining and returning a Map<MTFCC, Location>
 *
 * @since 0.5.1
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
public class GetLocationMapByCoordinatesHandler extends AbstractActionHandler<GetLocationMapByCoordinatesAction, GetLocationMapByCoordinatesResult>{

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetLocationMapByCoordinatesHandler() {
        super(GetLocationMapByCoordinatesAction.class);
    }

    @Override
    public GetLocationMapByCoordinatesResult execute(GetLocationMapByCoordinatesAction action, ExecutionContext context) throws ActionException {

        Map<MTFCC, Location> resultMap = locationService.getLocationMapForCoordinates(action.getLongitude(), action.getLatitude());

        // clean up the Locations: we don't need the borderpoints or the shapefile
        for (MTFCC m: resultMap.keySet()) {
            Location l = resultMap.get(m);
            l.setShapefileMetadata(null);
            l.setBorderPointList(null);
        }
        return new GetLocationMapByCoordinatesResult(resultMap);
    }

    @Override
    public void undo(GetLocationMapByCoordinatesAction action, GetLocationMapByCoordinatesResult result, ExecutionContext context) throws ActionException {
        // no-op
    }
}
