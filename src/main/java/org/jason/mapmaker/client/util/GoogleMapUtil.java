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
package org.jason.mapmaker.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import java.util.List;
import java.util.Map;

/**
 * Utility class for integrating GWT and Google Maps API v3.
 * <p/>
 * This class provides static native JSNI methods for simplifying working with GWT and v3 of the Google Maps API.
 * Some glue code is still necessary, as the static methods only output GWT JavaScriptObject or JsArray objects
 * which need to be manually integrated with each other.
 * <p/>
 * An example of some glue code is as follows:
 * <p/>
 * <pre>
 *
 *     public native void initMap(JavaScriptObject mapOptions, JavaScriptObject bounds, JavaScriptObject border, JsArray markerArray, Element e) /*-{
 *
 *       // create the map and fit it within the given bounds
 *       map = new $wnd.google.maps.Map(e, mapOptions);
 *       if (bounds != null) {
 *           map.fitBounds(bounds);
 *       }
 *
 *       // set the polgon for the borders
 *       if (border != null) {
 *           border.setMap(map);
 *       }
 *
 *       if (markerArray != null && markerArray.length > 0) {
 *           var infoWindow = new $wnd.google.maps.InfoWindow({
 *               content:"InfoWindow Content Goes Here"
 *           });
 *
 *           for (var i = 0; i < markerArray.length; i++) {
 *               var marker = markerArray[i];
 *               marker.setMap(map);
 *               $wnd.google.maps.event.addListener(marker, 'click', function() {
 *                   infoWindow.setContent(this.html);
 *                   infoWindow.open(map, this);
 *               });
 *           }
 *       }
 *
 *   (JSNI Closure, putting it in screws up the javadoc)
 *
 * </pre>
 *
 * @author Jason Ferguson
 * @since 0.4.4
 */
@SuppressWarnings("unused")
public class GoogleMapUtil {

    // change these values if you want the default map to show somewhere besides St Louis
    public static double defaultLat = 38.530;
    public static double defaultLng = -89.84;

    public static double getDefaultLat() {
        return defaultLat;
    }

    public static double getDefaultLng() {
        return defaultLng;
    }

    /**
     * Create a basic Google Map object centered on a particular point and return it as a GWT
     * JavaScript object. Map type will be ROADMAP.
     *
     * @param lat double representing the latitude to center on
     * @param lng double representing the longitude to center on
     * @return a GWT JavaScriptObject representing options for a google maps api map object (NOT THE MAP)
     */
    public native static JavaScriptObject createMapOptions(Double lat, Double lng) /*-{

        // call the static getters of this method to set the default values
        if (lat == null) {
            lat = @org.jason.mapmaker.client.util.GoogleMapUtil::getDefaultLat()();
        }

        if (lng == null) {
            lng = @org.jason.mapmaker.client.util.GoogleMapUtil::getDefaultLng()();
        }

        // create the point to center the map on
        var internalPoint = new $wnd.google.maps.LatLng(lat, lng);

        var mapOptions = {
            zoom: 8,
            center: internalPoint,
            mapTypeId: $wnd.google.maps.MapTypeId.ROADMAP
        };

        return mapOptions;
    }-*/;

    /**
     * Create the bounds for a Google MAPS API v3 map
     * <p/>
     * The method requires that a Map<String, Double> containing the minimum and maximum latitudes and longitudes
     * be passed in in order to create a LatLngBounds object.
     * <p/>
     * The Map must have the following keys set:
     * <p/>
     * - MINLAT
     * - MAXLAT
     * - MINLNG
     * - MAXLNG
     *
     * @param boundingBox a Map<String, Double> containing the latitude and longitude to create a
     *                    bounds for the map
     * @return a JavaScript object representing a Google Maps API v3 LatLngBounds object or null
     */
    public native static JavaScriptObject createMapBounds(Map<String, Double> boundingBox) /*-{

        if (boundingBox == null) {
            return null;
        }

        var minLat = boundingBox.@java.util.Map::get(Ljava/lang/Object;)('MINLAT');
        var minLng = boundingBox.@java.util.Map::get(Ljava/lang/Object;)('MINLNG');
        var maxLat = boundingBox.@java.util.Map::get(Ljava/lang/Object;)('MAXLAT');
        var maxLng = boundingBox.@java.util.Map::get(Ljava/lang/Object;)('MAXLNG');

        if (minLat == null || minLng == null || maxLat == null || maxLng == null) {
            return null;
        }

        var bounds = new $wnd.google.maps.LatLngBounds(new $wnd.google.maps.LatLng(minLat, minLng), new $wnd.google.maps.LatLng(maxLat, maxLng));

        return bounds;
    }-*/;

    /**
     * Create a border polygon for a Google Maps API v3 map object. Does NOT attach the border to the map.
     * <p/>
     * The method accepts a List of Map<String, Double> objects. The list MUST be in the order that the
     * lines for the border will be drawn in
     * <p/>
     * The map must have two keys:
     * - LNG (longitude)
     * - LAT (latitude)
     * <p/>
     * These keys are mapped to Double values. (I am aware of potential inaccuracy of the Double type, but using
     * BigDecimal would be difficult in conjuction with a JSNI method).
     * <p/>
     * Client code is responsible for setting the map that the border belongs to:
     * <p/>
     * border.setMap(mapObject);
     *
     * @param borderPoints A List of LinkedHashMap objects.
     * @return a JavaScript object representing the border polygon for a map object
     */
    public native static JavaScriptObject createBorderPolygon(List<Map<String, Double>> borderPoints) /*-{

        var borderArray = new $wnd.google.maps.MVCArray();

        var numberOfPoints = borderPoints.@java.util.List::size()();
        for (i = 0; i < numberOfPoints; i++) {
            var pointMap = borderPoints.@java.util.List::get(I)(i);
            var lat = pointMap.@java.util.Map::get(Ljava/lang/Object;)('LAT');
            var lng = pointMap.@java.util.Map::get(Ljava/lang/Object;)('LNG');

            borderArray.setAt(i, new $wnd.google.maps.LatLng(lat, lng));
        }

        if (borderArray != null) {

            var borders = new $wnd.google.maps.Polygon({
                path: borderArray,
                strokeColor: "#FF0000",
                strokeOpacity: 0.8,
                strokeWeight: 2
            });

            return borders;
        } else {
            return null;
        }
    }-*/;

    /**
     * Create and return a GWT JsArray of Google Maps API v3 InfoMarker objects
     * <p/>
     * The method accepts a List of Map objects which must must contain certain mandatory keys:
     * <p/>
     * - TITLE (String)
     * - LAT (Double)
     * - LNG (Double)
     * - CONTENTS (String)
     * <p/>
     * Client code will still have to loop through the array and set the map:
     * <p/>
     * markerArray[i].setMap(mapObject);
     *
     * @param markerData a List of Map objects containing information to put in the InfoMarker
     * @return a JsArray of Google Maps API v3 InfoMarker objects
     */
    public native static JsArray createMarkerArray(List<Map> markerData) /*-{

        var numberOfMarkers = markerData.@java.util.List::size()();
        var markerArray = new Array();
        markerArray.length = numberOfMarkers;

        for (var i = 0; i < numberOfMarkers; i++) {
            var markerMap = markerData.@java.util.List::get(I)(i);
            var markerTitle = markerMap.@java.util.Map::get(Ljava/lang/Object;)('TITLE');
            var markerLat = markerMap.@java.util.Map::get(Ljava/lang/Object;)('LAT');
            var markerLng = markerMap.@java.util.Map::get(Ljava/lang/Object;)('LNG');
            var contents = markerMap.@java.util.Map::get(Ljava/lang/Object;)('CONTENTS');
            var latLng = new $wnd.google.maps.LatLng(markerLat, markerLng);

            markerArray[i] = new $wnd.google.maps.Marker();
            markerArray[i].setPosition(latLng);
            markerArray[i].setTitle(markerTitle);
            markerArray[i].content = contents;   // this is a cheat to make the marker carry the infowindow content
        }

        return markerArray;

    }-*/;

    /**
     * Create and return a single Google Maps marker
     *
     * The method accepts a List of Map objects which must must contain certain mandatory keys:
     * <p/>
     * - TITLE (String)
     * - LAT (Double)
     * - LNG (Double)
     * - CONTENTS (String)
     *
     * @param marker    Map object containing data to popuplate Marker object with
     * @return      JavaScriptObject representing the Marker to display on the map
     */
    public native static JavaScriptObject createMarker(Map marker) /*-{

        var markerTitle = markerMap.@java.util.Map::get(Ljava/lang/Object;)('TITLE');
        var markerLat = markerMap.@java.util.Map::get(Ljava/lang/Object;)('LAT');
        var markerLng = markerMap.@java.util.Map::get(Ljava/lang/Object;)('LNG');
        var contents = markerMap.@java.util.Map::get(Ljava/lang/Object;)('CONTENTS');

        var latLng = new $wnd.google.maps.LatLng(markerLat, markerLng);

        var marker = new $wnd.google.maps.Marker();
        marker.setPosition(latLng);

        return marker;
    }-*/;
}
