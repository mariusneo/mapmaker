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
import org.jason.mapmaker.shared.action.featuresMetadata.GetFeaturesMetadataListAction;
import org.jason.mapmaker.shared.model.FeaturesMetadata;
import org.jason.mapmaker.shared.result.featuresMetadata.GetFeaturesMetadataListResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * GWT-Platform ActionHandler class for retrieving and returning a List of FeaturesMetadata objects
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class GetFeaturesMetadataListHandler extends AbstractActionHandler<GetFeaturesMetadataListAction, GetFeaturesMetadataListResult> {

    private FeaturesMetadataService featuresMetadataService;

    @Autowired
    public void setFeaturesMetadataService(FeaturesMetadataService featuresMetadataService) {
        this.featuresMetadataService = featuresMetadataService;
    }

    public GetFeaturesMetadataListHandler() {
        super(GetFeaturesMetadataListAction.class);
    }

    @Override
    public GetFeaturesMetadataListResult execute(GetFeaturesMetadataListAction getFeaturesMetadataListAction, ExecutionContext executionContext) throws ActionException {

        List<FeaturesMetadata> resultList = featuresMetadataService.getAll();

        return new GetFeaturesMetadataListResult(resultList);
    }

    @Override
    public void undo(GetFeaturesMetadataListAction getFeaturesMetadataListAction, GetFeaturesMetadataListResult getFeaturesMetadataListResult, ExecutionContext executionContext) throws ActionException {
         // no op
    }
}
