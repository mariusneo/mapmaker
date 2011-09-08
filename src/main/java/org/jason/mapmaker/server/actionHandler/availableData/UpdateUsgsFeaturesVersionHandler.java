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
import org.jason.mapmaker.server.service.FeatureService;
import org.jason.mapmaker.server.service.FeaturesMetadataService;
import org.jason.mapmaker.server.service.GenericSettingsService;
import org.jason.mapmaker.shared.action.availableData.UpdateUsgsFeaturesVersionAction;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.GenericSettings;
import org.jason.mapmaker.shared.result.availableData.UpdateUsgsFeaturesVersionResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWTP ActionHandler class for updating the USGS features version
 *
 * @since 0.4.2
 * @author Jason Ferguson
 */
public class UpdateUsgsFeaturesVersionHandler extends AbstractActionHandler<UpdateUsgsFeaturesVersionAction, UpdateUsgsFeaturesVersionResult> {

    private GenericSettingsService genericSettingsService;
    private FeaturesMetadataService featuresMetadataService;
    private FeatureService featureService;

    @Autowired
    public void setGenericSettingsService(GenericSettingsService genericSettingsService) {
        this.genericSettingsService = genericSettingsService;
    }

    @Autowired
    public void setFeaturesMetadataService(FeaturesMetadataService featuresMetadataService) {
        this.featuresMetadataService = featuresMetadataService;
    }

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    public UpdateUsgsFeaturesVersionHandler() {
        super(UpdateUsgsFeaturesVersionAction.class);
    }

    @Override
    public UpdateUsgsFeaturesVersionResult execute(UpdateUsgsFeaturesVersionAction action, ExecutionContext context) throws ActionException {

        GenericSettings gs = genericSettingsService.get();

        gs.setUsgsVersion(action.getVersion());
        genericSettingsService.update(gs);

        try {
            featureService.deleteAll();
            featuresMetadataService.deleteAll();
            featuresMetadataService.generateFeaturesMetadata(action.getVersion());
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
        return new UpdateUsgsFeaturesVersionResult();
    }

    @Override
    public void undo(UpdateUsgsFeaturesVersionAction action, UpdateUsgsFeaturesVersionResult result, ExecutionContext context) throws ActionException {
        // shouldn't be a no-op
    }
}
