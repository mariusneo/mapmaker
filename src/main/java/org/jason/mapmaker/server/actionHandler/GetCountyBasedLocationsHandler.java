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
package org.jason.mapmaker.server.actionHandler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.shared.action.GetCountyBasedLocationsAction;
import org.jason.mapmaker.shared.result.GetCountyBasedLocationsResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GetCountyBasedLocationsHandler.java
 *
 * @author Jason Ferguson
 */
public class GetCountyBasedLocationsHandler extends AbstractActionHandler<GetCountyBasedLocationsAction, GetCountyBasedLocationsResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetCountyBasedLocationsHandler() {
        super(GetCountyBasedLocationsAction.class);
    }

    @Override
    public Class<GetCountyBasedLocationsAction> getActionType() {
        return GetCountyBasedLocationsAction.class;
    }

    @Override
    public GetCountyBasedLocationsResult execute(GetCountyBasedLocationsAction getCountyBasedFeaturesAction, ExecutionContext executionContext) throws ActionException {

        String mtfccCode = getCountyBasedFeaturesAction.getMtfccCode();
        String stateFP = getCountyBasedFeaturesAction.getStateFP();
        String countyFP = getCountyBasedFeaturesAction.getCountyFP();

        Map<String, String> result = locationService.getCountyBasedLocations(mtfccCode, stateFP, countyFP);

        return new GetCountyBasedLocationsResult(result);
    }

    @Override
    public void undo(GetCountyBasedLocationsAction getCountyBasedFeaturesAction, GetCountyBasedLocationsResult getCountyBasedFeaturesResult, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
