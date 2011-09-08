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
package org.jason.mapmaker.server.actionHandler.featuresMetadata;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.FeaturesMetadataService;
import org.jason.mapmaker.shared.action.featuresMetadata.CountFeaturesMetadataAction;
import org.jason.mapmaker.shared.result.featuresMetadata.CountFeaturesMetadataResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWT-Platform ActionHandler class to get the count of persisted FeaturesMetadata objects and return them via a
 * CountFeaturesMetadataResult object
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class CountFeaturesMetadataHandler extends AbstractActionHandler<CountFeaturesMetadataAction, CountFeaturesMetadataResult> {

    private FeaturesMetadataService featuresMetadataService;

    @Autowired
    public void setFeaturesMetadataService(FeaturesMetadataService featuresMetadataService) {
        this.featuresMetadataService = featuresMetadataService;
    }

    public CountFeaturesMetadataHandler() {
        super(CountFeaturesMetadataAction.class);
    }

    @Override
    public CountFeaturesMetadataResult execute(CountFeaturesMetadataAction action, ExecutionContext context) throws ActionException {

        Long count = featuresMetadataService.getCount();

        return new CountFeaturesMetadataResult(count);
    }

    @Override
    public void undo(CountFeaturesMetadataAction action, CountFeaturesMetadataResult result, ExecutionContext context) throws ActionException {
        // no op
    }
}
