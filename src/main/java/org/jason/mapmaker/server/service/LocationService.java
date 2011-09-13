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

import org.jason.mapmaker.server.repository.LocationRepository;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.Map;

/**
 * Defines operations for implementations of the LocationService
 *
 * @author Jason Ferguson
 * @since 0.1
 */
@SuppressWarnings("unused")
public interface LocationService {

    void setLocationRepository(LocationRepository locationRepository);

    /* Persistence Methods */

    /**
     * Delete all items in the Location repository
     *
     * @throws ServiceException something went wrong...
     */
    void deleteAll() throws ServiceException;

    /**
     * Update a Location object
     * <p/>
     * TODO: Do we need this???
     *
     * @param l Location object to update
     */
    void update(Location l);

    /**
     * Save (persist) an entire List of Location objects
     *
     * @param locationList List of location objects to persist
     * @throws ServiceException something went wrong...
     */
    void saveList(List<Location> locationList) throws ServiceException;

    /**
     * Remove a Location
     * <p/>
     * TODO: Switch usages over to generic repo delete method
     *
     * @param l Location to remove
     * @throws ServiceException
     */
    @Deprecated
    void remove(Location l) throws ServiceException;

    /**
     * Delete locations by a given MTFCC code
     *
     * @param mtfccCode String representing the mtfccCode code for the location
     */
    void deleteByMtfcc(String mtfccCode);

    /**
     * Remove locations with a given state geoid code and mtfcc code
     *
     * @param stateGeoId String representing the state geoid (usually the same as the FIPS55 code)
     * @param mtfcc      String representing the mtfcc code for the location type to remove
     * @return int representing number of items removed
     * @throws ServiceException
     */
    int removeByStateGeoIdAndMtfcc(String stateGeoId, String mtfcc) throws ServiceException;

    /* Non-persistence methods */

    /**
     * Get number of Locations for each MTFCC type. Returns a Map in MTFCC Code->Count format.
     *
     * @return a Map<String,Integer> containing the counts for each Location type
     */
    Map<String, Long> getLocationCounts();

    /**
     * Get the list of states/equivalents that have a given MTFCC type. For example, Nebraska does not have a
     * lower state congressional district
     *
     * @param mtfccCode a String representing the MTFCC to hunt for (i.e. "G4020")
     * @return a List<Map<String, String>> containing a list of maps containing stateFP->(state FIPS55 code),
     *         name->(state name)
     */
    List<Map<String, String>> getStateAndEquivalentListForMtfcc(String mtfccCode);

    /**
     * Get the locations available for a State and MTFCC
     *
     * @param stateFP a String representing the state FIPS55 code
     * @param mtfcc   a String representing the MTFCC to hunt for (i.e. "G4020")
     * @return a Map<String, String> in MTFCC->Name format
     */
    Map<String, String> getLocationsByStateAndMtfcc(String stateFP, String mtfcc);

    /**
     * get the locations available for a state, county, and MTFCC
     *
     * @param stateFP  a string representing the state FIPS55 code
     * @param countyFP a string representing the county FIPS 55 code
     * @param mtfcc    a String representing the MTFCC to hunt for (i.e. "G4020")
     * @return a Map<String, String> in MTFCC->Name format
     */
    Map<String, String> getLocationsByStateAndCountyAndMtfcc(String stateFP, String countyFP, String mtfcc);

    /**
     * Get a Map of county-based locations in MTFCC->Name format
     *
     * @param mtfccCode MTFCC code
     * @param stateFP   State FIPS55 code
     * @param countyFP  County FIPS55 code
     * @return A Map<String, String>
     */
    Map<String, String> getCountyBasedLocations(String mtfccCode, String stateFP, String countyFP);

    /**
     * Get a location by its geoId and mtfccCode code
     *
     * @param geoId     String representing geoId of Location
     * @param mtfccCode String representing mtfccCode code of Location
     * @return a Location with the given GeoId and MTFCC code
     */
    Location getByGeoIdAndMtfcc(String geoId, String mtfccCode);

    /**
     * Populate a Location object with information from a simpleFeature taken from the TIGER/Line download site
     *
     * @param location      Location object to populate
     * @param simpleFeature SimpleFeature object with information to populate the Location object
     */
    void populateLocationFromFeature(Location location, SimpleFeature simpleFeature);

    /**
     * Return a Map<String, String> containing the FP->Name mappings for counties with a given state geoid
     *
     * @param stateGeoId String representing the State GeoID (usually the same as the FIPS55 code)
     * @return
     */
    Map<String, String> getCountiesForState(String stateGeoId);

    /**
     * Return a Map<String, Location> containing the String MTFCC mapped to the Location for that MTFCC
     * (or null if no locations are in the repository for that MTFCC)
     *
     * @param lng       double representing the longitude
     * @param lat       double representing the latitude
     * @return          Map<String, Location>
     */
    Map<String, Location> getLocationDescriptionsForCoordinates(double lng, double lat);

    Map<MTFCC, Location> getLocationMapForCoordinates(double lng, double lat);
}
