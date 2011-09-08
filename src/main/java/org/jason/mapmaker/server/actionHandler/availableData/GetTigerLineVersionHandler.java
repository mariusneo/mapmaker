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
package org.jason.mapmaker.server.actionHandler.availableData;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.GenericSettingsService;
import org.jason.mapmaker.shared.action.availableData.GetTigerLineVersionAction;
import org.jason.mapmaker.shared.model.GenericSettings;
import org.jason.mapmaker.shared.result.availableData.GetTigerLineVersionResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWTP ActionHandler class for obtaining the current TIGER/Line version
 *
 * @since 0.4.2
 * @author Jason Ferguson
 */
public class GetTigerLineVersionHandler extends AbstractActionHandler<GetTigerLineVersionAction, GetTigerLineVersionResult> {

    private GenericSettingsService genericSettingsService;

    @Autowired
    public void setGenericSettingsService(GenericSettingsService genericSettingsService) {
        this.genericSettingsService = genericSettingsService;
    }

    public GetTigerLineVersionHandler() {
        super(GetTigerLineVersionAction.class);
    }

    @Override
    public GetTigerLineVersionResult execute(GetTigerLineVersionAction action, ExecutionContext context) throws ActionException {

        GenericSettings gs = genericSettingsService.get();

        return new GetTigerLineVersionResult(gs.getTigerVersion());
    }

    @Override
    public void undo(GetTigerLineVersionAction action, GetTigerLineVersionResult result, ExecutionContext context) throws ActionException {
        // no-op
    }
}
