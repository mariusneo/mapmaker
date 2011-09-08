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
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.server.service.ShapefileMetadataService;
import org.jason.mapmaker.shared.action.availableData.UpdateTigerLineVersionAction;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.GenericSettings;
import org.jason.mapmaker.shared.result.availableData.UpdateTigerLineVersionResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWTP ActionHandler for upding the TIGER/Line version
 *
 * @author Jason Ferguson
 * @since 0.4.2
 */
public class UpdateTigerLineVersionHandler extends AbstractActionHandler<UpdateTigerLineVersionAction, UpdateTigerLineVersionResult> {

    private GenericSettingsService genericSettingsService;

    @Autowired
    public void setGenericSettingsService(GenericSettingsService genericSettingsService) {
        this.genericSettingsService = genericSettingsService;
    }

    private ShapefileMetadataService shapefileMetadataService;

    @Autowired
    public void setShapefileMetadataService(ShapefileMetadataService shapefileMetadataService) {
        this.shapefileMetadataService = shapefileMetadataService;
    }

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public UpdateTigerLineVersionHandler() {
        super(UpdateTigerLineVersionAction.class);
    }

    @Override
    public UpdateTigerLineVersionResult execute(UpdateTigerLineVersionAction action, ExecutionContext context) throws ActionException {

        GenericSettings gs = genericSettingsService.get();
        gs.setTigerVersion(action.getYear());
        genericSettingsService.update(gs);


        try {
            // before we update the shapefile metadata, all of the location data based on that has to be dumped. The idea is
            // that this will cause a cascade delete of all of the relationships that the location has
            locationService.deleteAll();

            // next, empty out the shapefilemetadata repo
            shapefileMetadataService.deleteAll();

            // next, regeneration the shapefilemetadata repo with the new info
            shapefileMetadataService.generateMetadata(action.getYear());

        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        return new UpdateTigerLineVersionResult();
    }

    @Override
    public void undo(UpdateTigerLineVersionAction action, UpdateTigerLineVersionResult result, ExecutionContext context) throws ActionException {
        // no-op
    }
}
