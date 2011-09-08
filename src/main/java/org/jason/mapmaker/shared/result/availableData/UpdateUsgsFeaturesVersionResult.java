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
package org.jason.mapmaker.shared.result.availableData;

import com.gwtplatform.dispatch.shared.Result;

/**
 * GWTP Result class for returning the result of the update operation for updating the USGS features date.
 *
 * This class is just a message, no data is returned. Returning the number of rows affected is probably overkill since
 * there should be only one GenericSettings object in the repository
 *
 * @since 0.4.2
 * @author Jason Ferguson
 */
public class UpdateUsgsFeaturesVersionResult implements Result {
}
