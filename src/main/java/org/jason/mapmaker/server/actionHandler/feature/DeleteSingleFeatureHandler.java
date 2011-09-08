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
package org.jason.mapmaker.server.actionHandler.feature;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.FeatureService;
import org.jason.mapmaker.shared.action.feature.DeleteSingleFeatureAction;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.result.feature.DeleteSingleFeatureResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWTP ActionHandler class to delete a single feature. This is actually a misnomer; you can only delete the features
 * belonging to a given state.
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class DeleteSingleFeatureHandler extends AbstractActionHandler<DeleteSingleFeatureAction, DeleteSingleFeatureResult> {

    private FeatureService featureService;

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    public DeleteSingleFeatureHandler() {
        super(DeleteSingleFeatureAction.class);
    }

    @Override
    public DeleteSingleFeatureResult execute(DeleteSingleFeatureAction action, ExecutionContext executionContext) throws ActionException {

        try {
            featureService.deleteByFeaturesMetadata(action.getFm());
        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        return new DeleteSingleFeatureResult();

    }

    @Override
    public void undo(DeleteSingleFeatureAction action, DeleteSingleFeatureResult result, ExecutionContext executionContext) throws ActionException {
         // shouldn't be a no-op, I guess
    }
}
