package org.jason.mapmaker.shared.result;

import com.gwtplatform.dispatch.shared.Result;

import java.util.Map;

/**
 * GWT-Platform result class for returning a Map of feature class names and the count of features with that feature
 * class name that have been persisted.
 * 
 * @author Jason Ferguson
 */
public class GetFeatureCountResult implements Result {

    private Map<String, Long> result;

    // for serialization only, do not use
    public GetFeatureCountResult() {
    }

    public GetFeatureCountResult(Map<String, Long> result) {
        this.result = result;
    }

    public Map<String, Long> getResult() {
        return result;
    }
}
