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
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.shared.action.GetLocationsAction;
import org.jason.mapmaker.shared.result.GetLocationsResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Handler class to return the specific details (i.e. which congressional district in the state, which
 * voting district in the county, etc).
 *  
 * @author Jason Ferguson
 */
public class GetLocationsHandler extends AbstractActionHandler<GetLocationsAction, GetLocationsResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetLocationsHandler() {
        super(GetLocationsAction.class);
    }

    @Override
    public Class<GetLocationsAction> getActionType() {
        return GetLocationsAction.class;
    }

    @Override
    public GetLocationsResult execute(GetLocationsAction action, ExecutionContext executionContext) throws ActionException {

        Map<String, String> result;

        if (action.getCountyFP() == null) {
            // get by mtfcc and state
            result = locationService.getLocationsByStateAndMtfcc(action.getStateFP(), action.getMtfccCode());
        } else {
            // get by mtfcc and state and county
            result = locationService.getLocationsByStateAndCountyAndMtfcc(action.getStateFP(), action.getCountyFP(), action.getMtfccCode());
        }

        return new GetLocationsResult(result);
    }

    @Override
    public void undo(GetLocationsAction getLocationsAction, GetLocationsResult getLocationsResult, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
