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
import org.jason.mapmaker.shared.action.shapefileMetadata.CountShapefileMetadataAction;
import org.jason.mapmaker.shared.result.shapefileMetadata.CountShapefileMetadataResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GWT-Platform ActionHandlers class to obtain the count of persisted ShapefileMetadata objects and return them
 * via a ShapefileMetadataResult object
 *
 * @author Jason Ferguson
 */
public class CountShapefileMetadataHandler extends AbstractActionHandler<CountShapefileMetadataAction, CountShapefileMetadataResult> {

    private ShapefileMetadataService shapefileMetadataService;

    @Autowired
    public void setShapefileMetadataService(ShapefileMetadataService shapefileMetadataService) {
        this.shapefileMetadataService = shapefileMetadataService;
    }

    public CountShapefileMetadataHandler() {
        super(CountShapefileMetadataAction.class);
    }

    @Override
    public CountShapefileMetadataResult execute(CountShapefileMetadataAction action, ExecutionContext context) throws ActionException {

        long count = shapefileMetadataService.getCount();

        return new CountShapefileMetadataResult(count);
    }

    @Override
    public void undo(CountShapefileMetadataAction action, CountShapefileMetadataResult result, ExecutionContext context) throws ActionException {
         // no-op
    }
}
