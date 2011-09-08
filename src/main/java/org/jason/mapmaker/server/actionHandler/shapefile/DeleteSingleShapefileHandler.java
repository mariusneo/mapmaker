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
package org.jason.mapmaker.server.actionHandler.shapefile;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.apache.commons.lang.StringUtils;
import org.jason.mapmaker.server.service.LocationService;
import org.jason.mapmaker.server.service.ShapefileMetadataService;
import org.jason.mapmaker.shared.action.shapefile.DeleteSingleShapefileAction;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.ShapefileMetadata;
import org.jason.mapmaker.shared.result.shapefile.DeleteSingleShapefileResult;
import org.jason.mapmaker.shared.util.GeographyUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWT-Platform ActionHandler class for deleting data from a single Shapefile
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class DeleteSingleShapefileHandler extends AbstractActionHandler<DeleteSingleShapefileAction, DeleteSingleShapefileResult> {

    private ShapefileMetadataService shapefileMetadataService;
    private LocationService locationService;

    @Autowired
    public void setShapefileMetadataService(ShapefileMetadataService shapefileMetadataService) {
        this.shapefileMetadataService = shapefileMetadataService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public DeleteSingleShapefileHandler() {
        super(DeleteSingleShapefileAction.class);
    }

    @Override
    public DeleteSingleShapefileResult execute(DeleteSingleShapefileAction action, ExecutionContext executionContext) throws ActionException {

        ShapefileMetadata sm = action.getShapefileMetadata();
        int affectedRows;

        try {
            // delete the borderpoints
            String stateGeoId = StringUtils.left(sm.getGeoId(), 2); // get the state portion of the geoid
            affectedRows = locationService.removeByStateGeoIdAndMtfcc(stateGeoId, sm.getMtfccCode());
            //shapefileService.deleteByShapefileMetadata(sm);

            // update the sm object status
            sm.setCurrentStatus(GeographyUtils.Status.NOT_IMPORTED);
            shapefileMetadataService.update(sm);

        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        return new DeleteSingleShapefileResult(affectedRows);
    }

    @Override
    public void undo(DeleteSingleShapefileAction action, DeleteSingleShapefileResult deleteSingleShapefileResult, ExecutionContext executionContext) throws ActionException {

        // shouldn't be a no-op...
    }
}
