package org.jason.mapmaker.client.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwtplatform.mvp.client.UiHandlers;

/**
 * GWTP UiHandlers interface for the MapmakerMapViewPanel
 *
 * @since 0.4.4
 * @author Jason Ferguson
 */
public interface MapPanelUiHandlers extends UiHandlers {

    void doGetLocationDescriptions(JavaScriptObject map, double lng, double lat);
}
