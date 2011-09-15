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
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.shared.action.location.GetLocationsByStateAndMtfccAction;
import org.jason.mapmaker.shared.result.location.GetLocationsByStateAndMtfccResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GetLocationsByStateAndMtfccHandler.java
 *
 * @author Jason Ferguson
 */
public class GetLocationsByStateAndMtfccHandler extends AbstractActionHandler<GetLocationsByStateAndMtfccAction,GetLocationsByStateAndMtfccResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetLocationsByStateAndMtfccHandler() {
        super(GetLocationsByStateAndMtfccAction.class);
    }

    @Override
    public Class<GetLocationsByStateAndMtfccAction> getActionType() {
        return GetLocationsByStateAndMtfccAction.class;
    }

    @Override
    public GetLocationsByStateAndMtfccResult execute(GetLocationsByStateAndMtfccAction action, ExecutionContext executionContext) {
        Map<String, String> result = locationService.getLocationsByStateAndMtfcc(action.getStateFP(), action.getMtfcc());
        return new GetLocationsByStateAndMtfccResult(result);
    }

    @Override
    public void undo(GetLocationsByStateAndMtfccAction action, GetLocationsByStateAndMtfccResult result, ExecutionContext executionContext) {
        // no-op, no state change
    }
}
