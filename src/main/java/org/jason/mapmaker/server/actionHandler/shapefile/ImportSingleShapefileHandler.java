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
import org.jason.mapmaker.server.service.ShapefileMetadataService;
import org.jason.mapmaker.server.util.ShapefileUtil;
import org.jason.mapmaker.shared.action.shapefile.ImportSingleShapefileAction;
import org.jason.mapmaker.shared.exceptions.UtilityClassException;
import org.jason.mapmaker.shared.model.ShapefileMetadata;
import org.jason.mapmaker.shared.result.shapefile.ImportSingleShapefileResult;
import org.jason.mapmaker.shared.util.GeographyUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWT-Platform ActionHandler class for importing Shapefiles from the ShapefileMetadata class
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportSingleShapefileHandler
            extends AbstractActionHandler<ImportSingleShapefileAction, ImportSingleShapefileResult> {

    private ShapefileUtil shapefileUtil;
    private ShapefileMetadataService shapefileMetadataService;

    @Autowired
    public void setShapefileUtil(ShapefileUtil shapefileUtil) {
        this.shapefileUtil = shapefileUtil;
    }

    @Autowired
    public void setShapefileMetadataService(ShapefileMetadataService shapefileMetadataService) {
        this.shapefileMetadataService = shapefileMetadataService;
    }

    public ImportSingleShapefileHandler() {
        super(ImportSingleShapefileAction.class);
    }

    @Override
    public ImportSingleShapefileResult execute(ImportSingleShapefileAction action, ExecutionContext context) throws ActionException {
        try {

            ShapefileMetadata sm = action.getShapefileMetadata();

            try {
                shapefileUtil.processShapefile(sm);
            } catch (UtilityClassException e) {
                throw new ActionException(e);
            }

            sm.setCurrentStatus(GeographyUtils.Status.IMPORTED);
            shapefileMetadataService.update(sm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ImportSingleShapefileResult();
    }

    @Override
    public void undo(ImportSingleShapefileAction action, ImportSingleShapefileResult result, ExecutionContext context) throws ActionException {
        // no-op for now
    }
}
