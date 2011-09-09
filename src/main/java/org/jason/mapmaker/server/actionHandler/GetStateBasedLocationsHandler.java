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
import org.jason.mapmaker.shared.action.GetStateBasedLocationsAction;
import org.jason.mapmaker.shared.result.GetStateBasedLocationsResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWTP ActionHandler class for returning a list of location types for a state and returning it as a Result object
 *
 * @since 0.3
 * @author Jason Ferguson
 */
public class GetStateBasedLocationsHandler extends AbstractActionHandler<GetStateBasedLocationsAction, GetStateBasedLocationsResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetStateBasedLocationsHandler() {
        super(GetStateBasedLocationsAction.class);
    }

    @Override
    public Class<GetStateBasedLocationsAction> getActionType() {
        return GetStateBasedLocationsAction.class;
    }

    @Override
    public GetStateBasedLocationsResult execute(GetStateBasedLocationsAction action, ExecutionContext context) throws ActionException {
        String mtfcc = action.getMtfcc();
        String stateFP = action.getStateFP();

        Map<String, String> result = locationService.getLocationsByStateAndMtfcc(stateFP, mtfcc);

        return new GetStateBasedLocationsResult(result);
    }

    @Override
    public void undo(GetStateBasedLocationsAction action, GetStateBasedLocationsResult result, ExecutionContext context) throws ActionException {
        // no-op
    }
}
