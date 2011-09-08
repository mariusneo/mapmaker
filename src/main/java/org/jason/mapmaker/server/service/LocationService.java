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
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.Map;

/**
 * LocationService.java
 *
 * Defines operations for implementations of the LocationService
 *
 * @since 0.1
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
public interface LocationService {

    void setLocationRepository(LocationRepository locationRepository);

    void saveList(List<Location> locationList) throws ServiceException;

    /**
     * Get the number of Locations stored by the application
     *
     * @return  Integer
     */
    Long getLocationCount();

    /**
     * Get number of Locations for a given MTFCC code
     *
     * @param mtfccCode `String representing the MTFCC code (i.e. 'G4000' for a State/Equivalent)
     * @return  an Integer representing the count of Location object for the given MTFCC code
     */
    Long getLocationCountByMtfccCode(String mtfccCode);

    /**
     * Get number of Locations for each MTFCC type. Returns a Map in MTFCC Code->Count format.
     *
     * @return  a Map<String,Integer> containing the counts for each Location type
     */
    Map<String, Long> getLocationCounts();

    /**
     * Get the list of states/equivalents that have a given MTFCC type. For example, Nebraska does not have a
     * lower state congressional district
     *
     * @param mtfccCode     a String representing the MTFCC to hunt for (i.e. "G4020")
     * @return a List<Map<String, String>> containing a list of maps containing stateFP->(state FIPS55 code),
     *          name->(state name)
     */
    List<Map<String, String>> getStateAndEquivalentListForMtfcc(String mtfccCode);

    /**
     * Get the locations available for a State and MTFCC
     *
     * @param stateFP   a String representing the state FIPS55 code
     * @param mtfcc     a String representing the MTFCC to hunt for (i.e. "G4020")
     * @return          a Map<String, String> in MTFCC->Name format
     */
    Map<String, String> getLocationsByStateAndMtfcc(String stateFP, String mtfcc);

    /**
     * get the locations available for a state, county, and MTFCC
     *
     * @param stateFP   a string representing the state FIPS55 code
     * @param countyFP  a string representing the county FIPS 55 code
     * @param mtfcc     a String representing the MTFCC to hunt for (i.e. "G4020")
     * @return          a Map<String, String> in MTFCC->Name format
     */
    Map<String, String> getLocationsByStateAndCountyAndMtfcc(String stateFP, String countyFP, String mtfcc);

    /**
     * Get a Map of county-based locations in MTFCC->Name format
     *
     * @param mtfccCode     MTFCC code
     * @param stateFP       State FIPS55 code
     * @param countyFP      County FIPS55 code
     * @return              A Map<String, String>
     */
    Map<String, String> getCountyBasedLocations(String mtfccCode, String stateFP, String countyFP);

    /**
     * Delete locations by a given MTFCC code
     *
     * @param mtfccCode     String representing the mtfccCode code for the location
     */
    void deleteByMtfcc(String mtfccCode);

    /**
     * Get a location by its COMPLETE geoid. This differs from getByGeoIdAndMtfcc because in that case, only a
     * partial (state or state+county) may be available. Do not use this method to retrieve a State or County!!!
     *
     * @param geoId     String representing the COMPLETE geoId
     * @return      a Location object with the given geoid
     */
    Location getByCompleteGeoId(String geoId);

    /**
     * Get a location by its geoId and mtfccCode code
     *
     * @param geoId     String representing geoId of Location
     * @param mtfccCode String representing mtfccCode code of Location
     * @return  a Location with the given GeoId and MTFCC code
     */
    Location getByGeoIdAndMtfcc(String geoId, String mtfccCode);

    /**
     * Populate a Location object with information from a simpleFeature taken from the TIGER/Line download site
     *
     * @param location          Location object to populate
     * @param simpleFeature     SimpleFeature object with information to populate the Location object
     */
    void populateLocationFromFeature(Location location, SimpleFeature simpleFeature);

    /**
     * Return a Map<String, String> containing the FP->Name mappings for counties with a given state geoid
     * @param stateGeoId    String representing the State GeoID (usually the same as the FIPS55 code)
     * @return
     */
    Map<String, String> getCountiesForState(String stateGeoId);

    /**
     * Remove a Location
     *
     * @param l     Location to remove
     * @throws ServiceException
     */
    void remove(Location l) throws ServiceException;

    /**
     * Remove locations with a given state geoid code and mtfcc code
     *
     * @param stateGeoId    String representing the state geoid (usually the same as the FIPS55 code)
     * @param mtfcc     String representing the mtfcc code for the location type to remove
     * @return      int representing number of items removed
     * @throws ServiceException
     */
    int removeByStateGeoIdAndMtfcc(String stateGeoId, String mtfcc) throws ServiceException;

    void deleteAll() throws ServiceException;

    void update(Location l);

    Map<String, Location> getLocationDescriptionsForCoordinates(double lng, double lat);
}
