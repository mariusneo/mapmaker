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
import org.jason.mapmaker.shared.action.location.GetLocationsByStateAndCountyAndMtfccAction;
import org.jason.mapmaker.shared.result.location.GetLocationsByStateAndCountyAndMtfccResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWTP ActionHandler class for accepting the stateFP, countyFP, and mtfccCode and returning a list of Locations based on
 * it
 *
 * @since unknown
 * @author Jason Ferguson
 */
public class GetLocationsByStateAndCountyAndMtfccHandler extends AbstractActionHandler<GetLocationsByStateAndCountyAndMtfccAction, GetLocationsByStateAndCountyAndMtfccResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetLocationsByStateAndCountyAndMtfccHandler() {
        super(GetLocationsByStateAndCountyAndMtfccAction.class);
    }

    @Override
    public Class<GetLocationsByStateAndCountyAndMtfccAction> getActionType() {
        return GetLocationsByStateAndCountyAndMtfccAction.class;
    }

    @Override
    public GetLocationsByStateAndCountyAndMtfccResult execute(GetLocationsByStateAndCountyAndMtfccAction action, ExecutionContext executionContext) throws ActionException {

        String mtfccCode = action.getMtfccCode();
        String stateFP = action.getStateFP();
        String countyFP = action.getCountyFP();

        Map<String, String> result = locationService.getCountyBasedLocations(mtfccCode, stateFP, countyFP);

        return new GetLocationsByStateAndCountyAndMtfccResult(result);
    }

    @Override
    public void undo(GetLocationsByStateAndCountyAndMtfccAction action, GetLocationsByStateAndCountyAndMtfccResult result, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
