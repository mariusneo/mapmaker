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
import org.jason.mapmaker.shared.action.GetStatesByMtfccAction;
import org.jason.mapmaker.shared.result.GetStatesByMtfccResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * GWTP ActionHandler class to retrieve the list of states and their MTFCCs for use by the presenter and view
 *
 * @since 0.2
 * @author Jason Ferguson
 */
public class GetStatesByMtfccHandler extends AbstractActionHandler<GetStatesByMtfccAction,GetStatesByMtfccResult> {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public GetStatesByMtfccHandler() {
        super(GetStatesByMtfccAction.class);
    }

    public Class<GetStatesByMtfccAction> getActionType() {
        return GetStatesByMtfccAction.class;
    }

    public synchronized GetStatesByMtfccResult execute(GetStatesByMtfccAction action, ExecutionContext context) throws ActionException {
        List<Map<String, String>> result = locationService.getStateAndEquivalentListForMtfcc(action.getMtfcc());
        return new GetStatesByMtfccResult(action.getMtfcc(), result);
    }

    public synchronized void undo(GetStatesByMtfccAction action, GetStatesByMtfccResult result, ExecutionContext context) throws ActionException {
        // no-op, we haven't changed the state of anything
    }
}
