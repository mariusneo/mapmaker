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
package org.jason.mapmaker.server.repository;

import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;

import java.util.List;
import java.util.Map;

/**
 * Interface for LocationRepository classes
 *
 * @since 0.1
 * @author Jason Ferguson
 */
public interface LocationRepository extends GenericRepository2<Location> {

    /**
     * Return a count of all locations by MTFCC code (Mapping is MTFCC->Count). This method is an exception to the
     * guideline of not putting specialized methods into the repository if it can be avoided; the select new map()
     * syntax in the Hibernate Query Language was made for this.
     *
     * @return a Map<String, Long> containing the MTFCC Code and the count of Location objects with that code
     */
    Map<String, Long> getLocationCounts();


    /**
     * Convenience method to return a Map of county ids mapped to the county name for a given state
     *
     * @param stateGeoId
     * @return
     */
    Map<String, String> getCountiesForState(String stateGeoId);

    /**
     * Return a list of Map<String, String> results containing maps of State->(state name) and StateFP->(state FIPS55)
     *
     * @param mtfcc
     * @return
     */
    List<Map<String, String>> getStateAndEquivalentListForMtfcc(MTFCC mtfcc);

    Location getByCompleteGeoId(String geoId);

    List<Location> getLocationDescriptionsForCoordinates(double lng, double lat);

    /**
     * Return a list of locations with a given mtfcc code and a given state and (optional) county FP code
     *
     * @param mtfccCode     MTFCC code for the Locations to return
     * @param fpCodes       String varargs. First needs to be stateFP, next is countyFP (if necessary)
     * @return  List of locations with the given FP code(s)
     */
    List<Location> getLocations(String mtfccCode, String... fpCodes);
}
