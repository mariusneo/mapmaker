package org.jason.mapmaker.client.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwtplatform.mvp.client.UiHandlers;

/**
 * GWTP UiHandlers interface for the MapmakerMapViewPanel
 *
 * @since 0.4.4
 * @author Jason Ferguson
 * @see org.jason.mapmaker.client.view.MapmakerMapViewImpl
 * @see org.jason.mapmaker.client.presenter.MapmakerMapViewPresenter
 */
public interface MapPanelUiHandlers extends UiHandlers {

    /**
     * Display a marker with an infowindow on the given map at the given coordinates
     *
     * @param map   JavaScriptObject representing the Google Maps API v3 map object
     * @param lng   double representing the longitude
     * @param lat   double representing the latitude
     */
    void doGetLocationDescription(JavaScriptObject map, double lng, double lat);
}
