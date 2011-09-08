package org.jason.mapmaker.shared.action;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import org.jason.mapmaker.shared.result.GetFeatureCountResult;

/**
 * GWT-Platform action to return a Map of feature class names and count of how many features of that type have been
 * persisted.
 *
 * No arguments are required.
 *
 * @author Jason Ferguson
 */
public class GetFeatureCountAction extends UnsecuredActionImpl<GetFeatureCountResult> {

    public GetFeatureCountAction() {
    }
}
