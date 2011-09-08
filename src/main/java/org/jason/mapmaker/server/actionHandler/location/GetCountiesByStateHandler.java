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
import org.jason.mapmaker.shared.action.location.GetCountiesByStateAction;
import org.jason.mapmaker.shared.result.location.GetCountiesByStateResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWTP ActionHandler class to return a list of counties (or equivalent) for a given state (or equivalent)
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class GetCountiesByStateHandler extends AbstractActionHandler<GetCountiesByStateAction, GetCountiesByStateResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetCountiesByStateHandler() {
        super(GetCountiesByStateAction.class);
    }

    public GetCountiesByStateResult execute(GetCountiesByStateAction action, ExecutionContext executionContext) throws ActionException {

        Map<String, String> resultMap = locationService.getCountiesForState(action.getStateFP());

        return new GetCountiesByStateResult(resultMap);
    }

    public void undo(GetCountiesByStateAction action, GetCountiesByStateResult result, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
