package org.jason.mapmaker.client.view;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author Jason Ferguson
 */
public interface ManageFeaturesUiHandlers extends UiHandlers {

    /**
     * Close the popup view
     */
    void doClose();

    /**
     * Delete features by feature class name
     *
     * @param featureClassName  String representing the feature class name to use in the delete query
     */
    void doDeleteFeatures(String featureClassName);
}
