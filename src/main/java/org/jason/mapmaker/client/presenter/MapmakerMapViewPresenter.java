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
package org.jason.mapmaker.client.presenter;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.jason.mapmaker.client.event.EnableRedrawMapButtonEvent;
import org.jason.mapmaker.client.event.RedrawMapEvent;
import org.jason.mapmaker.client.event.RedrawMapHandler;
import org.jason.mapmaker.client.util.GoogleMapUtil;
import org.jason.mapmaker.client.view.MapPanelUiHandlers;
import org.jason.mapmaker.shared.action.GetMapDataByGeoIdAction;
import org.jason.mapmaker.shared.action.location.GetLocationMapByCoordinatesAction;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;
import org.jason.mapmaker.shared.result.GetMapDataByGeoIdResult;
import org.jason.mapmaker.shared.result.location.GetLocationMapByCoordinatesResult;

import java.util.HashMap;
import java.util.Map;

/**
 * GWTP Presenter for the MapView section of the AppShell Presenter
 *
 * @author Jason Ferguson
 * @since 0.1
 * @see org.jason.mapmaker.client.view.MapmakerMapViewImpl
 * @see org.jason.mapmaker.client.view.MapPanelUiHandlers
 */
@SuppressWarnings({"unused"})
public class MapmakerMapViewPresenter extends PresenterWidget<MapmakerMapViewPresenter.MyView>
        implements MapPanelUiHandlers {

    public interface MyView extends View, HasUiHandlers<MapPanelUiHandlers> {

        /**
         * Prepares and draws the map by sorting the borderpoint list by unique id, then passing it and the bounding box to
         * the JSNI method for rendering.
         *
         * @param location    Location object to be rendered
         * @param boundingBox Map containing the bounding box for the Location to be rendered
         * @param e           div element where the map will be drawn
         */
        void prepareAndInitializeMap(Location location, Map<String, Double> boundingBox, Element e);

        /**
         * Display a marker and an attached infowindow displaying what borders are available for the location
         * that the user clicked on
         *
         * @param map   JavaScriptObject representing the Google Maps API v3 Map object
         * @param lng   double representing the longitude clicked on
         * @param lat   double representing the latitude clicked on
         */
        void getLocationDescription(JavaScriptObject map, double lng, double lat);

        /**
         * Trigger a Javascript alert with the given message
         *
         * @param alertMessage      String with the message to display
         */
        void doAlertMessage(String alertMessage);

        /**
         * Add a given marker to a map
         * @param map       JavaScriptObject representing the Google Maps API v3 Map object
         * @param marker    JavaScriptObject representing the Google Maps API v3 Marker object
         */
        void addMarkerToMap(JavaScriptObject map, JavaScriptObject marker);

    }

    private DispatchAsync dispatch;

    @Inject
    public MapmakerMapViewPresenter(EventBus eventBus, MyView view, DispatchAsync dispatch) {
        super(eventBus, view);
        this.dispatch = dispatch;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {

        // show a default map view
        getView().prepareAndInitializeMap(null, null, getView().asWidget().getElement());

        // register the handler for map redraw events
        registerHandler(getEventBus().addHandler(RedrawMapEvent.TYPE, new RedrawMapHandler() {
            @Override
            public void onRedrawMap(RedrawMapEvent event) {
                doRedrawMap(event.getGeoId(), event.getMtfccCode(), event.getFeatureClassType());
            }
        }));
    }

    public void doGetLocationDescription(final JavaScriptObject gmap, final double lng, final double lat) {

        dispatch.execute(new GetLocationMapByCoordinatesAction(lng, lat), new AsyncCallback<GetLocationMapByCoordinatesResult>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(GetLocationMapByCoordinatesResult result) {
                Map<MTFCC, Location> resultMap = result.getResults();

                Map<String, Object> map = new HashMap<String, Object>();   // this is a non-generified Map, I need to store Strings and Doubles in it
                if (resultMap.size() == 0) {
                    StringBuffer message = new StringBuffer();
                    message.append("<p>No borders available for this location</p>");
                    map.put("TITLE", "Unknown Location");
                    map.put("LNG", lng);
                    map.put("LAT", lat);
                    map.put("CONTENTS", message.toString());
                } else {
                    StringBuffer message = new StringBuffer();
                    message.append("<table>\n");
                    for (MTFCC key : resultMap.keySet()) {
                        Location l = resultMap.get(key);
                        message.append("<tr>\n");
                        message.append("<td><b>").append(key.getFeatureClass()).append("</b></td>\n");
                        if (l == null) {
                            message.append("<td>Feature not available</td>\n");
                        } else {
                            message.append("<td>").append(resultMap.get(key).getName()).append("<td>\n");
                        }
                        message.append("</tr>\n");
                    }
                    message.append("</table>\n");

                    map.put("TITLE", "Location Details");
                    map.put("LNG", lng);
                    map.put("LAT", lat);
                    map.put("CONTENTS", message.toString());
                }

                JavaScriptObject marker = GoogleMapUtil.createMarker(map);
                getView().addMarkerToMap(gmap, marker);
            }
        });

    }

    private void doRedrawMap(String geoId, String mtfccCode, String featureClassName) {

        dispatch.execute(new GetMapDataByGeoIdAction(geoId, mtfccCode, featureClassName), new AsyncCallback<GetMapDataByGeoIdResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(GetMapDataByGeoIdResult result) {
                getView().prepareAndInitializeMap(result.getLocation(), result.getBoundingBox(), getView().asWidget().getElement());
                getEventBus().fireEvent(new EnableRedrawMapButtonEvent());
            }
        });
    }
}
