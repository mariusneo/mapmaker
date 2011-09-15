/*
 * <p/>
 * Copyright 2011 Jason Ferguson
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jason.mapmaker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.MapmakerMapViewPresenter;
import org.jason.mapmaker.client.util.GoogleMapUtil;
import org.jason.mapmaker.shared.model.BorderPoint;
import org.jason.mapmaker.shared.model.Feature;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.comparator.BorderPointIdComparator;

import java.util.*;

/**
 * Implementation of the Map View section of the application
 *
 * @author Jason Ferguson
 * @since 0.1
 */
@Singleton
public class MapmakerMapViewImpl extends ViewWithUiHandlers<MapPanelUiHandlers>
        implements MapmakerMapViewPresenter.MyView, IsWidget {

    @UiTemplate("MapmakerMapView.ui.xml")
    interface Binder extends UiBinder<HTMLPanel, MapmakerMapViewImpl> {
    }

    private static Binder binder = GWT.create(Binder.class);

    @UiField HTMLPanel mapmakerMapView;

    private Widget widget;

    public MapmakerMapViewImpl() {

        widget = binder.createAndBindUi(this);

    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    /**
     * Prepares and draws the map by sorting the borderpoint list by unique id, then passing it and the bounding box to
     * the JSNI method for rendering.
     *
     * @param location    Location object to be rendered
     * @param boundingBox Map containing the bounding box for the Location to be rendered
     * @param e           div element where the map will be drawn
     */
    @Override
    public void prepareAndInitializeMap(Location location, Map<String, Double> boundingBox, Element e) {

        // should create a default map
        if (location == null) {
            JavaScriptObject mapOptions = GoogleMapUtil.createMapOptions(null, null);
            initMap(mapOptions, null, null, null, e);
            return;
        }

        // create the basic map
        JavaScriptObject mapOptions = GoogleMapUtil.createMapOptions(location.getInternalLat(), location.getInternalLng());
        JavaScriptObject mapBounds = GoogleMapUtil.createMapBounds(boundingBox);

        List<BorderPoint> borderPointList = location.getBorderPointList();

        // borderPointList needs to be sorted by ID to render correctly
        Collections.sort(borderPointList, new BorderPointIdComparator());

        // reformat the BorderPoint list into List<Map<String, Double>>
        List<Map<String, Double>> mapList = new ArrayList<Map<String, Double>>();
        for (BorderPoint bp : borderPointList) {
            Map<String, Double> myMap = new HashMap<String, Double>();
            myMap.put("LNG", bp.getLng());
            myMap.put("LAT", bp.getLat());
            mapList.add(myMap);
        }

        // create the polygon that will serve as the border
        JavaScriptObject borderPolygon = GoogleMapUtil.createBorderPolygon(mapList);

        // convert the features into markers
        List<Feature> featureList = location.getFeatureList();
        List<Map> featureMapList = new ArrayList<Map>();
        Map<String, Object> feature = new HashMap<String, Object>();

        if (featureList == null || featureList.isEmpty()) {
            feature.put("CONTENTS", "<p>No features are currently loaded for the selected location</p>");
            featureMapList.add(feature);
        } else {
            for (Feature f : featureList) {
                // This needs to be a Map w/o parameters!
                feature.put("TITLE", f.getName());
                feature.put("LAT", f.getLat());
                feature.put("LNG", f.getLng());

                //var feature = f.@java.util.List::get(I)(i);
                StringBuffer contents = new StringBuffer();
                contents.append("<p><b>").append(f.getName()).append("</b></p>");
                contents.append("<p><b>Type:</b> ").append(f.getFeatureClass()).append("</p>");
                contents.append("<p><b>Coordinates (Lat, Lng): </b>(").append(f.getLat()).append(", ").append(f.getLng()).append(")</p>");

                feature.put("CONTENTS", contents.toString());

                featureMapList.add(feature);
            }
        }
        JsArray markerArray = GoogleMapUtil.createMarkerArray(featureMapList);

        initMap(mapOptions, mapBounds, borderPolygon, markerArray, e);

    }

    public native void initMap(JavaScriptObject mapOptions, JavaScriptObject bounds, JavaScriptObject border, JsArray markerArray, Element e) /*-{

        // create the map and fit it within the given bounds
        map = new $wnd.google.maps.Map(e, mapOptions);
        if (bounds != null) {
            map.fitBounds(bounds);
        }

        // set the polygon for the borders
        if (border != null) {
            border.setMap(map);
        }

        if (markerArray != null && markerArray.length > 0) {
            var infoWindow = new $wnd.google.maps.InfoWindow({
                content:"InfoWindow Content Goes Here"
            });

            for (var i = 0; i < markerArray.length; i++) {
                var marker = markerArray[i];
                marker.setMap(map);
                $wnd.google.maps.event.addListener(marker, 'click', function() {
                    infoWindow.setContent(marker.content);
                    infoWindow.open(map, this);
                });
            }
        }

        var that = this;

        $wnd.getLocationDescriptions =function(map, lng, lat) {
            that.@org.jason.mapmaker.client.view.MapmakerMapViewImpl::getLocationDescriptions(Lcom/google/gwt/core/client/JavaScriptObject;DD)(map, lng, lat);
        };

        $wnd.google.maps.event.addListener(map, 'click', function(event) {
            var lat = event.latLng.lat();
            var lng = event.latLng.lng();
            $wnd.getLocationDescriptions(map, lng, lat);
        });
    }-*/;

    @Override
    public native void addMarkerToMap(JavaScriptObject map, JavaScriptObject marker) /*-{

        marker.setMap(map);

        var infoWindow = new $wnd.google.maps.InfoWindow({
                content:marker.contents
            });

        $wnd.google.maps.event.addListener(marker, 'click', function() {
            infoWindow.setContent(marker.content);
            infoWindow.open(map, marker);
        });

    }-*/;

    @Override
    public void getLocationDescriptions(JavaScriptObject map, double lng, double lat) {
        GWT.log("Executing MapmakerMapViewImpl.getLocationDescriptions()");
        getUiHandlers().doGetLocationDescriptions(map, lng, lat);
    }

    public native void doAlertMessage(String alertMessage) /*-{
        alert(alertMessage);
    }-*/;
}
