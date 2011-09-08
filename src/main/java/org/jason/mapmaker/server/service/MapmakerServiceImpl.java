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
package org.jason.mapmaker.server.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import org.apache.commons.compress.archivers.ArchiveException;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureImpl;
import org.jason.mapmaker.server.util.ZipUtil;
import org.jason.mapmaker.shared.model.BorderPoint;
import org.jason.mapmaker.shared.model.Location;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * MapmakerServiceImpl.java
 *
 * Implementation of the MapmakerService interface
 *
 * @author Jason Ferguson
 */
@Service("mapmakerService")
public class MapmakerServiceImpl implements MapmakerService {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public boolean importShapefileFromURL(String url) {

        URL remoteURL;
        try {
            remoteURL = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        // get and unzip the remote directory
        List<File> contents;
        try {
            contents = ZipUtil.decompress(remoteURL);
        } catch (ArchiveException e) {
            return false;
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            return false;
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        File shpFile = null;
        for (File file : contents) {
            if (file.getName().indexOf(".shp") > -1) {
                shpFile = file;
                break;
            }
        }

        if (shpFile == null) {
            return false;
        }

        ShapefileDataStore dataStore = null;
        try {
            dataStore = new ShapefileDataStore(shpFile.toURL());
            String[] typenames = dataStore.getTypeNames();
            for (String name : typenames) {
                FeatureSource featureSource = dataStore.getFeatureSource(name);
                FeatureCollection featureCollection = featureSource.getFeatures();
                FeatureIterator iterator = featureCollection.features();

                while(iterator.hasNext()) {
                    Location location = new Location();

                    SimpleFeature simpleFeature = (SimpleFeatureImpl) iterator.next();

                    // set all the FP fields
                    location.setStateFP((String) simpleFeature.getAttribute("STATE"));

                    // save it

                    // create the border points
                    MultiPolygon mp = (MultiPolygon) simpleFeature.getDefaultGeometry();
                    Geometry g = mp.getBoundary();
                    Coordinate[] coordinates = g.getCoordinates();

                    List<BorderPoint> bpList = new ArrayList<BorderPoint>();
                    for (Coordinate c : coordinates) {

                        Float lng = new Float(c.x);
                        Float lat = new Float(c.y);

                        if (lat != null && lng != null) {
                            BorderPoint bp = new BorderPoint();
                            bpList.add(bp);

                        }
                    }

                    // save it again

                }
            }
        } catch (MalformedURLException e) {

        } catch (IOException ex) {

        }

        return true;    // TODO: really?
    }
}
