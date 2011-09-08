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
package org.jason.mapmaker.shared.result;


import com.gwtplatform.dispatch.shared.Result;

import java.util.List;
import java.util.Map;

/**
 * GWTP Result to return a List of states and their FIPS to the presenter/view
 * <p/>
 * @since 0.2
 * @author Jason Ferguson
 */
public class GetStatesByMtfccResult implements Result {

    private String mtfcc;
    private List<Map<String, String>> result;

    // for serialization only, do not use
    public GetStatesByMtfccResult() {
    }

    public GetStatesByMtfccResult(String mtfcc, List<Map<String, String>> result) {
        this.mtfcc = mtfcc;
        this.result = result;
    }

    public String getMtfcc() {
        return mtfcc;
    }

    public List<Map<String, String>> getResult() {
        return result;
    }
}
