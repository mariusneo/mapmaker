package org.jason.mapmaker.server.actionHandler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import org.jason.mapmaker.server.service.FeatureService;
import org.jason.mapmaker.shared.action.GetFeatureCountAction;
import org.jason.mapmaker.shared.result.GetFeatureCountResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * GWT-Platform action handler for returning a Map of feature class names and the count of features with that feature
 * class name that have been persisted.
 *
 * @author Jason Ferguson
 */
public class GetFeatureCountHandler extends AbstractActionHandler<GetFeatureCountAction, GetFeatureCountResult> {

    private FeatureService featureService;

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    public GetFeatureCountHandler() {
        super(GetFeatureCountAction.class);
    }

    @Override
    public GetFeatureCountResult execute(GetFeatureCountAction getFeatureCountAction, ExecutionContext executionContext) throws ActionException {

        Map<String, Long> result = featureService.getFeatureCounts();

        return new GetFeatureCountResult(result);
    }

    @Override
    public void undo(GetFeatureCountAction getFeatureCountAction, GetFeatureCountResult getFeatureCountResult, ExecutionContext executionContext) throws ActionException {
        // no-op
    }
}
