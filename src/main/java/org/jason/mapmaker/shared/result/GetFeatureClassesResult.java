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
package org.jason.mapmaker.shared.result;

import com.gwtplatform.dispatch.shared.Result;

import java.util.List;

/**
 *
 * @author Jason Ferguson
 */
public class GetFeatureClassesResult implements Result {

    private List<String> result;

    public GetFeatureClassesResult() {
    }

    public GetFeatureClassesResult(List<String> result) {
        this.result = result;
    }

    public List<String> getResult() {
        return result;
    }
}
