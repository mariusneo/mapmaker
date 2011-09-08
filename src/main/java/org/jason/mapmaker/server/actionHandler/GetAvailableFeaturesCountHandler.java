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
import org.jason.mapmaker.shared.action.GetAvailableLocationsCountAction;
import org.jason.mapmaker.shared.result.GetAvailableLocationsCountResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Jason Ferguson
 */
public class GetAvailableFeaturesCountHandler extends AbstractActionHandler<GetAvailableLocationsCountAction, GetAvailableLocationsCountResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }


    public GetAvailableFeaturesCountHandler() {
        super(GetAvailableLocationsCountAction.class);
    }

    @Override
    public Class<GetAvailableLocationsCountAction> getActionType() {
        return GetAvailableLocationsCountAction.class;
    }

    @Override
    public GetAvailableLocationsCountResult execute(GetAvailableLocationsCountAction getAvailableLocationsCountAction, ExecutionContext executionContext) throws ActionException {

        Map<String, Long> resultMap = locationService.getLocationCounts();
        return new GetAvailableLocationsCountResult(resultMap);

    }

    @Override
    public void undo(GetAvailableLocationsCountAction getAvailableLocationsCountAction, GetAvailableLocationsCountResult getAvailableLocationsCountResult, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
