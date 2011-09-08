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
package org.jason.mapmaker.client.view.featuresMetadata;

import com.gwtplatform.mvp.client.UiHandlers;
import org.jason.mapmaker.shared.model.FeaturesMetadata;

/**
 * @author Jason Ferguson
 */
public interface ManageFeaturesMetadataUiHandler extends UiHandlers {

    void doClose();

    void doShowDetails();

    void doImport(FeaturesMetadata fm, int rowIndex);

    void doDelete(FeaturesMetadata fm, int rowIndex);
}
