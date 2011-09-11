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
import org.jason.mapmaker.shared.action.shapefileMetadata.GetShapefileMetadataForGeoIdAction;
import org.jason.mapmaker.shared.model.ShapefileMetadata;
import org.jason.mapmaker.shared.result.shapefileMetadata.GetShapefileMetadataForGeoIdResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * GWT-Platform ActionHandler class for retrieving the persisted ShapefileMetadata objects with a given
 * Geoid.
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class GetShapefileMetadataForGeoIdHandler extends
                AbstractActionHandler<GetShapefileMetadataForGeoIdAction, GetShapefileMetadataForGeoIdResult> {

    private ShapefileMetadataService shapefileMetadataService;

    @Autowired
    public void setShapefileMetadataService(ShapefileMetadataService shapefileMetadataService) {
        this.shapefileMetadataService = shapefileMetadataService;
    }

    public GetShapefileMetadataForGeoIdHandler() {
        super(GetShapefileMetadataForGeoIdAction.class);
    }

    @Override
    public GetShapefileMetadataForGeoIdResult execute(GetShapefileMetadataForGeoIdAction action, ExecutionContext executionContext) throws ActionException {

        List<ShapefileMetadata> smList = shapefileMetadataService.getByGeoId(action.getGeoId());
        if (!action.isIncludeLocationList()) {
            for (ShapefileMetadata sm: smList) {
                sm.setLocationList(null);
            }
        }
        return new GetShapefileMetadataForGeoIdResult(smList);

    }

    @Override
    public void undo(GetShapefileMetadataForGeoIdAction action, GetShapefileMetadataForGeoIdResult result, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
