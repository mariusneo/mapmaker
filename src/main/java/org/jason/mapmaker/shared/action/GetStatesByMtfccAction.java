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
package org.jason.mapmaker.shared.action;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import org.jason.mapmaker.shared.result.GetStatesByMtfccResult;

/**
 * GetStatesByMtfccAction.java
 *
 * Originally based on code from:
 * http://code.google.com/docreader/#p=gwt-dispatch&s=gwt-dispatch&t=GettingStarted
 * http://code.google.com/p/gwt-dispatch-spring-ext/wiki/GettingStarted
 * 
 * @author Jason Ferguson
 */
public class GetStatesByMtfccAction extends UnsecuredActionImpl<GetStatesByMtfccResult> {

    private String mtfcc;

    public GetStatesByMtfccAction() {
    }

    public GetStatesByMtfccAction(String mtfcc) {
        this.mtfcc = mtfcc;
    }

    public String getMtfcc() {
        return mtfcc;
    }
}
