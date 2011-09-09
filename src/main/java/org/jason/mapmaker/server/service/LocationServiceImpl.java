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

import org.apache.commons.lang.StringUtils;
import org.jason.mapmaker.server.repository.LocationRepository;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;
import org.jason.mapmaker.shared.util.GeographyUtils;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the LocationService interface
 *
 * @author Jason Ferguson
 * @since 0.1
 */
@Service("locationService")
public class LocationServiceImpl implements LocationService, PersistenceService<Location> {

    private MtfccService mtfccService;

    @Autowired
    public void setMtfccService(MtfccService mtfccService) {
        this.mtfccService = mtfccService;
    }

    private LocationRepository locationRepository;

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void persist(Location object) throws ServiceException {
        try {
            locationRepository.save(object);
        } catch (Exception e) {
            throw new ServiceException("persist() threw an Exception", e);
        }
    }

    public void remove(Location object) throws ServiceException {
        try {
            locationRepository.delete(object);
        } catch (Exception e) {
            throw new ServiceException("remove() threw an Exception", e);
        }
    }

    public int removeByStateGeoIdAndMtfcc(String stateGeoId, String mtfcc) throws ServiceException {

        MTFCC m = mtfccService.get(mtfcc);
        Location example = new Location();
        example.setGeoId(StringUtils.left(stateGeoId, 2)); // the first two digits are always equal to the state's geoid
        example.setMtfcc(m);

        List<Location> resultList = locationRepository.getByExample(example);
        int affectedRows = resultList.size();
        for (Location l:resultList) {
            locationRepository.delete(l);
        }

        return affectedRows;
    }

    public void saveList(List<Location> locationList) throws ServiceException {
        try {
            locationRepository.saveList(locationList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Map<String, Long> getLocationCounts() {
        return locationRepository.getLocationCounts();
    }

    /**
     * Takes a List<Object> that is assumed to contain >= 1 Object[2] arrays and converts the values into a Map.
     * <p/>
     * The [0] element will be the key, the [1] element will be the value. Obviously, this method can be a bit fragile.
     *
     * @param resultList
     * @return
     */
    private Map<String, String> createKeyValueMap(List resultList) {

        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        for (Object o : resultList) {
            Object[] o2 = (Object[]) o;
            String key = (String) o2[0];
            String value = (String) o2[1];

            resultMap.put(key, value);
        }

        return resultMap;
    }

    public List<Map<String, String>> getStateAndEquivalentListForMtfcc(String mtfccCode) {

        MTFCC m = mtfccService.get(mtfccCode);
        return locationRepository.getStateAndEquivalentListForMtfcc(m);

    }

    public Map<String, String> getLocationsByStateAndMtfcc(String stateFP, String mtfcc) {

        List<Location> locationList = locationRepository.getLocations(mtfcc, stateFP);
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        for (Location l: locationList) {
            resultMap.put(l.getGeoId(), l.getName());
        }

        return resultMap;
    }

    public Map<String, String> getLocationsByStateAndCountyAndMtfcc(String stateFP, String countyFP, String mtfcc) {

        MTFCC m = mtfccService.get(mtfcc);
        Location example = new Location();
        example.setStateFP(stateFP);
        example.setCountyFP(countyFP);
        example.setMtfcc(m);

        List<Location> resultList = locationRepository.getByExample(example);
        return createKeyValueMap(resultList);
    }


    public Map<String, String> getCountyBasedLocations(String mtfccCode, String stateFP, String countyFP) {

        MTFCC mtfcc = mtfccService.get(mtfccCode);

        Location example = new Location();
        example.setStateFP(stateFP);
        example.setCountyFP(countyFP);
        example.setMtfcc(mtfcc);

        List<Location> resultList = locationRepository.getByExample(example);
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        for (Location l : resultList) {
            resultMap.put(l.getGeoId(), l.getName());
        }

        resultMap.remove(stateFP.concat(countyFP)); // bugfix, the county is showing up in the list of its subfeatures
                                                    // quickfix is to do a quick and dirty generation of the county
                                                    // geoid and remove it
        return resultMap;

    }

    public void deleteByMtfcc(String mtfccCode) {

        MTFCC m = mtfccService.get(mtfccCode);
        Location example = new Location();
        example.setMtfcc(m);

        List<Location> locationList = locationRepository.getByExample(example);
        for (Location l: locationList) {
            locationRepository.delete(l);
        }
    }

    @Transactional(readOnly = true)
    public Location getByCompleteGeoId(String geoId) {

        return locationRepository.getByCompleteGeoId(geoId);

    }

    @Transactional(readOnly = true)
    public Location getByGeoIdAndMtfcc(String geoId, String mtfccCode) {

        MTFCC m = mtfccService.get(mtfccCode);
        Location example = new Location();
        example.setGeoId(geoId);
        example.setMtfcc(m);

        List<Location> resultList = locationRepository.getByExample(example);
        if (resultList.size() == 0) {
            return null;
        }

        return resultList.get(0);
    }

    public void populateLocationFromFeature(Location location, SimpleFeature feature) {

        location.setGeoId((String) feature.getAttribute("GEOID10"));
        location.setStateFP((String) feature.getAttribute("STATEFP10")); // always gonna have a statefp

        // these ones are more conditional, but it will either set the result or null
        location.setCountyFP((String) feature.getAttribute("COUNTYFP10"));
        location.setTractFP((String) feature.getAttribute("TRACTFP10"));
        location.setCd111FP((String) feature.getAttribute("CD111FP"));
        location.setConsolidatedCityFP((String) feature.getAttribute("CONCTYFP10"));
        location.setCountySubdivisionFP((String) feature.getAttribute("COUSUBFP10"));
        location.setElementaryDistrictFP((String) feature.getAttribute("ELSDLEA10"));
        location.setSecondaryDistrictFP((String) feature.getAttribute("SCSDLEA10"));
        location.setUnifiedDistrictFP((String) feature.getAttribute("UNSDLEA10"));
        location.setSubMinorFP((String) feature.getAttribute("SUBMCDFP10"));
        location.setPlaceFP((String) feature.getAttribute("PLACEFP10"));
        location.setStateUpperDistrictFP((String) feature.getAttribute("SLDUST10"));
        location.setStateLowerDistrictFP((String) feature.getAttribute("SLDLST10"));
        location.setVotingDistrictFP((String) feature.getAttribute("VTDST10"));
        location.setClassFP((String) feature.getAttribute("CLASSFP10"));

        // set the basic information

        // name is conditional, some feature types don't have a NAME10 attribute so we have to use NAMELSAD10
        if (feature.getAttribute("NAME10") != null) {
            location.setName((String) feature.getAttribute("NAME10"));
        } else {
            location.setName((String) feature.getAttribute("NAMELSAD10"));
        }

        location.setLsad((String) feature.getAttribute("LSAD10"));
        location.setUrbanRural((String) feature.getAttribute("UR10"));
        location.setFunctionalStatus((String) feature.getAttribute("FUNCSTAT10"));

        // numeric values
        location.setLandArea((Long) feature.getAttribute("ALAND10"));
        location.setWaterArea((Long) feature.getAttribute("AWATER10"));
        location.setInternalLat(new Double((String) feature.getAttribute("INTPTLAT10")));
        location.setInternalLng(new Double((String) feature.getAttribute("INTPTLON10")));

        // Convert to from the MTFCC String to the actual object
        String featureMtfcc = (String) feature.getAttribute("MTFCC10");
        MTFCC mtfcc = mtfccService.get(featureMtfcc);
        location.setMtfcc(mtfcc);
    }

    public Map<String, String> getCountiesForState(String stateGeoId) {

        MTFCC m = mtfccService.get(GeographyUtils.MTFCC.COUNTY);

        Location example = new Location();
        example.setGeoId(stateGeoId);
        example.setMtfcc(m);

        List<Location> resultList = locationRepository.getByExample(example);
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        for (Location l: resultList) {
            resultMap.put(l.getCountyFP(), l.getName());
        }

        return resultMap;
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            locationRepository.deleteAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Location l) {
        locationRepository.update(l);
    }

    @Override
    public Map<String, Location> getLocationDescriptionsForCoordinates(double lng, double lat) {

        List<Location> locationList = locationRepository.getLocationDescriptionsForCoordinates(lng, lat);

        // set up the empty map
        Map<String, Location> resultMap = new HashMap<String, Location>();
        for (String key: GeographyUtils.nameToMtfccMap.inverse().keySet()) {
            resultMap.put(key, null);
        }

        for (Location l: locationList) {
            String mtfccCode = l.getMtfcc().getMtfccCode();
            resultMap.put(mtfccCode, l);
        }

        return resultMap;
    }
}
