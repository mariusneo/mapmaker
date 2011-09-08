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
package org.jason.mapmaker.server.actionHandler.shapefileMetadata;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.ShapefileMetadataService;
import org.jason.mapmaker.shared.action.shapefileMetadata.ImportShapefileMetadataAction;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.result.shapefileMetadata.ImportShapefileMetadataResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWT-Platform ActionHandler for triggering the import/generation of the shapefile metadata from the census department
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportShapefileMetadataHandler extends AbstractActionHandler<ImportShapefileMetadataAction, ImportShapefileMetadataResult> {

    private ShapefileMetadataService shapefileMetadataService;

    @Autowired
    public void setShapefileMetadataService(ShapefileMetadataService shapefileMetadataService) {
        this.shapefileMetadataService = shapefileMetadataService;
    }

    public ImportShapefileMetadataHandler() {
        super(ImportShapefileMetadataAction.class);
    }

    @Override
    public ImportShapefileMetadataResult execute(ImportShapefileMetadataAction action, ExecutionContext executionContext) throws ActionException {
        try {
            shapefileMetadataService.generateMetadata2(); // 2 is the ftp method, 3 is the http method. FTP is much faster

        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        return new ImportShapefileMetadataResult();
    }

    @Override
    public void undo(ImportShapefileMetadataAction action, ImportShapefileMetadataResult result, ExecutionContext executionContext) throws ActionException {

    }
}
