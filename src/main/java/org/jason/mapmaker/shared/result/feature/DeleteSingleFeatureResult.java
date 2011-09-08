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
 * GWT-Platform Result class that acts as a message sent when a group of features for a state has been deleted. The name
 * is a bit confusing; it's not possible (via the app interface anyways) to delete a single feature. You have to delete
 * all the features for a given state.
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class DeleteSingleFeatureResult implements Result {

    public DeleteSingleFeatureResult() {
    }
}
