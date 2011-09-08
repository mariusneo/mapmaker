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
import org.jason.mapmaker.server.service.MtfccService;
import org.jason.mapmaker.shared.action.GetMtfccTypesAction;
import org.jason.mapmaker.shared.result.GetMtfccTypesResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWTP ActionHandler class for getting the MTFCC Types
 *
 * @author Jason Ferguson
 */
public class GetMtfccTypesHandler extends AbstractActionHandler<GetMtfccTypesAction, GetMtfccTypesResult> {

    private MtfccService mtfccService;

    @Autowired
    public void setMtfccService(MtfccService mtfccService) {
        this.mtfccService = mtfccService;
    }

    public GetMtfccTypesHandler() {
        super(GetMtfccTypesAction.class);
    }

    @Override
    public Class<GetMtfccTypesAction> getActionType() {
        return GetMtfccTypesAction.class;
    }

    @Override
    public GetMtfccTypesResult execute(GetMtfccTypesAction getMtfccTypesAction, ExecutionContext executionContext) throws ActionException {
        Map<String, String> result = mtfccService.getMtfccTypes();
        return new GetMtfccTypesResult(result);
    }

    @Override
    public void undo(GetMtfccTypesAction getMtfccTypesAction, GetMtfccTypesResult getMtfccTypesResult, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
