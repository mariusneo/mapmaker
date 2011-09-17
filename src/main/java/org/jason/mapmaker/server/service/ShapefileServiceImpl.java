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
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.jason.mapmaker.server.repository.BorderPointRepository;
import org.jason.mapmaker.server.repository.LocationRepository;
import org.jason.mapmaker.shared.exceptions.RepositoryException;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.BorderPoint;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.ShapefileMetadata;
import org.opengis.feature.simple.SimpleFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * ShapefileServiceImpl.java
 * <p/>
 * Implementation of ShapefileService interface
 *
 * @author Jason Ferguson
 */
@Service("shapefileService")
public class ShapefileServiceImpl implements ShapefileService {

    private static Logger log = LoggerFactory.getLogger(ShapefileServiceImpl.class);

    private LocationService locationService;
    private LocationRepository locationRepository;
    private BorderPointRepository borderPointRepository;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Autowired
    public void setBorderPointRepository(BorderPointRepository borderPointRepository) {
        this.borderPointRepository = borderPointRepository;
    }

    @Override
    public void processShapefile(String shapefileName) throws ServiceException {
        String tmpFileDir = System.getProperty("java.io.tmpdir");

        File shapeFile = new File(tmpFileDir, shapefileName);

        // TODO: Clean these next few lines up, kind of dumb looking to have a combo of if's and try's
        if (!shapeFile.exists()) {
            throw new ServiceException("Shapefile " + shapefileName + " not found!");
        }

        FileDataStore dataStore = null;
        try {
            dataStore = FileDataStoreFinder.getDataStore(shapeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dataStore == null) {
            throw new ServiceException("ShapefileDataStore was null");
        }

        String[] typeNames = new String[0];
        try {
            typeNames = dataStore.getTypeNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String typeName : typeNames) {

            FeatureSource featureSource;
            FeatureCollection featureCollection;

            try {
                featureSource = dataStore.getFeatureSource(typeName);
                featureCollection = featureSource.getFeatures();

                saveLocations(featureCollection);
                saveBorderPoints(featureCollection);

            } catch (IOException e) {
                throw new ServiceException("processShapefile() threw IOException", e);
            }

        }
    }

    /**
     * Cycle through and save the locations defined in the shapefile
     * <p/>
     * TODO: make this method synchronized
     *
     * @param featureCollection
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private void saveLocations(FeatureCollection featureCollection) throws ServiceException {

        log.trace("Executing saveLocations()");
        List<Location> locationList = new ArrayList<Location>(featureCollection.size());

        FeatureIterator<SimpleFeature> iterator = featureCollection.features();
        log.info("featureCollection contains " + featureCollection.size() + " elements.");
        while (iterator.hasNext()) {

            SimpleFeature feature = iterator.next();
            Location location = new Location();
            locationService.populateLocationFromFeature(location, feature);
            locationList.add(location);

        }
        try {
            locationRepository.saveList(locationList);
        } catch (Exception e) {
            log.debug("saveLocations() threw RepositoryException", e);
            throw new ServiceException("saveLocations() threw RepositoryException", e);
        }

        iterator.close();
    }

    /**
     * Cycle through and save the borderpoints saved in the Shapefile's geometry
     *
     * @param featureCollection
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private void saveBorderPoints(FeatureCollection featureCollection) throws ServiceException {

        log.trace("Executing saveBorderPoints()");
        FeatureIterator<SimpleFeature> iterator = featureCollection.features();

        while (iterator.hasNext()) {

            SimpleFeature feature = iterator.next();

            String geoId = (String) feature.getAttribute("GEOID10");
            String mtfcc = (String) feature.getAttribute("MTFCC10");

            Location location = locationService.getByGeoIdAndMtfcc(geoId, mtfcc);

            if (location == null) {
                log.debug("saveBorderPoints() threw ServiceException due to null Location");
                throw new ServiceException("saveBorderPoints() threw ServiceException due to null Location");
            }
            MultiPolygon multiPolygon = (MultiPolygon) feature.getDefaultGeometry();
            Geometry geometry = multiPolygon.getBoundary();

            // Use the geotools TopologyPreservingSimplifier to create a simplified version of the geometry
            Geometry simplifiedGeometry = TopologyPreservingSimplifier.simplify(multiPolygon, 0.0001);
            log.info("Geometry for " + feature.getAttribute("NAME10") + " simplified from " + multiPolygon.getBoundary().getNumPoints()
                    + " points to " + simplifiedGeometry.getNumPoints() + " points.");

            // Create the result list. Set initial capacity size to number of actual points in the geometry to avoid some
            // overhead when dealing with the list. Use LinkedList since we're going to be removing items from
            // various points inside the list during our duplicate check
            //List<BorderPoint> borderPointList = new LinkedList<BorderPoint>();
            Set<BorderPoint> borderPointSet = new LinkedHashSet<BorderPoint>(geometry.getNumPoints());

            // cycle through the coordinates to create the border points
            Coordinate[] coordinates = simplifiedGeometry.getCoordinates();

            // stick everything into the list
            for (Coordinate c : coordinates) {
                BorderPoint bp = new BorderPoint();
                bp.setLocation(location);
                bp.setLat(c.y);
                bp.setLng(c.x);
                borderPointSet.add(bp);
            }


            try {
                borderPointRepository.saveList(new ArrayList<BorderPoint>(borderPointSet));
            } catch (Exception e) {
                throw new ServiceException("saveBorderPoints() threw RepositoryException", e);
            }
        }

        iterator.close();
    }



    public Map<String, Double> getBoundsByLocation(Location l) throws ServiceException {

        try {
            Map<String, Double> result = borderPointRepository.getBoundsByLocation(l);
            return result;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteByShapefileMetadata(ShapefileMetadata sm) throws ServiceException {

        Location l = locationService.getByGeoIdAndMtfcc(sm.getGeoId(), sm.getMtfccCode());
        locationService.remove(l);
        //borderPointService.deleteByGeoIdAndMtfcc(sm.getGeoId(), sm.getMtfccCode());
    }
}
