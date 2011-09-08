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
package org.jason.mapmaker.shared.result.feature;

import com.gwtplatform.dispatch.shared.Result;

/**
 * GWT-Platform Result class that acts as a message stating that the import of a single file containing Features has
 * been completed. The name is a bit of a misnomer; it's not possible to only import a single Feature. Instead, the
 * app interface allows you to import all of the features for a single state.
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportSingleFeatureResult implements Result {

    public ImportSingleFeatureResult() {
    }
}
