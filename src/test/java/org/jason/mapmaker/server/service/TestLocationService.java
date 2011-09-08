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

import org.easymock.EasyMock;
import org.jason.mapmaker.server.repository.LocationRepository;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;
import org.jason.mapmaker.shared.util.GeographyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for the LocationService
 *
 * @since 0.4
 * @author Jason Ferguson
 */
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestLocationService {

    LocationRepository locationRepository; // will be mocked. Mercilessly.
    LocationService locationService = new LocationServiceImpl();

    @Before
    public void setUp() {
        // create a mock LocationRepository to do my bidding
        locationRepository = EasyMock.createMock(LocationRepository.class);

        // locationlist1 is a slightly complex case where we want county FIPS55 codes mapped to the county name
        // we'll say we are looking for Alabama counties, but just return generic names
        MTFCC state = new MTFCC();
        state.setMtfccCode(GeographyUtils.MTFCC.STATE);
        state.setFeatureClass("State");

        MTFCC county = new MTFCC();
        county.setMtfccCode(GeographyUtils.MTFCC.COUNTY);
        county.setFeatureClass("County");

        List<Location> locationList1 = new ArrayList<Location>();
        locationList1.add(new Location("01001","County 1", county));
        locationList1.add(new Location("01002", "County 2", county));


        // locationlist2 is a slightly more complex case where we want county FIPS55 codes mapped to the county name
        Location exampleAlabama = new Location();
        exampleAlabama.setGeoId("01");
        MTFCC mtfccExampleState = new MTFCC();
        mtfccExampleState.setMtfccCode(GeographyUtils.MTFCC.STATE);
        exampleAlabama.setMtfcc(mtfccExampleState);

        EasyMock.expect(locationRepository.queryByExample(exampleAlabama)).andReturn(locationList1).anyTimes();

        EasyMock.replay();

        // inject the repository into the service.
        locationService.setLocationRepository(locationRepository);
    }

    // pass in a state and mtfcc code, ensure that we get a List of locations back
    @Test
    public void testGetLocationsByStateAndMtfcc() {

        // check that I get a Map<String, String> back
        Assert.assertNotNull(locationService.getLocationsByStateAndMtfcc("01", GeographyUtils.MTFCC.COUNTY));

        Map<String, String> result = locationService.getLocationsByStateAndMtfcc("01", GeographyUtils.MTFCC.COUNTY);
        Assert.assertTrue(result.size() == 2);
    }
}
