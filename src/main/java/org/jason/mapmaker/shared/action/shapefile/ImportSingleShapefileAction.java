/**
 * Copyright 2011 Jason Ferguson.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jason.mapmaker.shared.action.shapefile;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import org.jason.mapmaker.shared.model.ShapefileMetadata;
import org.jason.mapmaker.shared.result.shapefile.ImportSingleShapefileResult;

/**
 * GWT-Platform Action class for importing a Shapefile based on its associated ShapefileMetadata object
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportSingleShapefileAction extends UnsecuredActionImpl<ImportSingleShapefileResult> {

    private ShapefileMetadata shapefileMetadata;

    // for serialization only; do not use
    public ImportSingleShapefileAction() {
    }

    public ImportSingleShapefileAction(ShapefileMetadata shapefileMetadata) {
        this.shapefileMetadata = shapefileMetadata;
    }

    public ShapefileMetadata getShapefileMetadata() {
        return shapefileMetadata;
    }
}
