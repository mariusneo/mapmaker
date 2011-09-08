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
package org.jason.mapmaker.server.actionHandler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.FeatureService;
import org.jason.mapmaker.shared.action.GetFeatureClassesAction;
import org.jason.mapmaker.shared.result.GetFeatureClassesResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jason Ferguson
 */
public class GetFeatureClassesHandler extends AbstractActionHandler<GetFeatureClassesAction, GetFeatureClassesResult> {

    private FeatureService featureService;

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    public GetFeatureClassesHandler() {
        super(GetFeatureClassesAction.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public GetFeatureClassesResult execute(GetFeatureClassesAction action, ExecutionContext context) throws ActionException {

        // convert the openjpa DistinctResultList to a regular ArrayList which can be serialized and passed back down
        // the wire
        List<String> result = new ArrayList(featureService.getFeatureClasses());
        Collections.sort(result);
        return new GetFeatureClassesResult(result);
    }

    @Override
    public void undo(GetFeatureClassesAction action, GetFeatureClassesResult result, ExecutionContext context) throws ActionException {
        // no op
    }
}
