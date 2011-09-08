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
package org.jason.mapmaker.service;

import org.jason.mapmaker.server.service.FeatureService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Jason Ferguson
 */
@RunWith(JUnit4.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestFeatureService {

    private FeatureService featureService;

    @Autowired
    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }

    @Test
    public void testGenerateUrl() {

        String url = featureService.generateUrl("01","20110613");

        Assert.assertTrue(url.equals("http://geonames.usgs.gov/docs/stategaz/AL_FEATURES_20110613.zip"));
    }
}
