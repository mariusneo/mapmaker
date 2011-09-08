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
import org.jason.mapmaker.server.service.FeaturesMetadataService;
import org.jason.mapmaker.shared.action.feature.ImportSingleFeatureAction;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.FeaturesMetadata;
import org.jason.mapmaker.shared.result.feature.ImportSingleFeatureResult;
import org.jason.mapmaker.shared.util.GeographyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWTP ActionHandler class to interact with backend and return the result
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportSingleFeatureHandler extends AbstractActionHandler<ImportSingleFeatureAction, ImportSingleFeatureResult> {

    private static Logger log = LoggerFactory.getLogger(ImportSingleFeatureHandler.class);

    private FeatureService featureService;
    private FeaturesMetadataService featuresMetadataService;

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    @Autowired
    public void setFeaturesMetadataService(FeaturesMetadataService featuresMetadataService) {
        this.featuresMetadataService = featuresMetadataService;
    }

    public ImportSingleFeatureHandler() {
        super(ImportSingleFeatureAction.class);
    }

    @Override
    public ImportSingleFeatureResult execute(ImportSingleFeatureAction action, ExecutionContext executionContext) throws ActionException {

        FeaturesMetadata fm = action.getFeaturesMetadata();
        try {
            featureService.importFromFeaturesMetadata(fm);
            fm.setCurrentStatus(GeographyUtils.Status.IMPORTED);
            featuresMetadataService.update(fm);
        } catch (ServiceException e) {
            log.debug("Exception: ", e.getMessage());
            throw new ActionException(e);
        }

        return new ImportSingleFeatureResult();
    }

    @Override
    public void undo(ImportSingleFeatureAction action, ImportSingleFeatureResult result, ExecutionContext executionContext) throws ActionException {
         // shouldn't really be a no-op
    }
}
