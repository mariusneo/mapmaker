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

import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.FeaturesMetadata;

import java.util.List;

/**
 * Interface for FeaturesMetadata related functions
 *
 * @author Jason Ferguson
 */
public interface FeaturesMetadataService {

    FeaturesMetadata get(long id);

    void save(FeaturesMetadata fm) throws ServiceException;

    void getByStateGeoId(String stateGeoId);

    /**
     * Generate the FeaturesMetadata
     *
     * @param date most current date of usgs data file (get from the USGS geonames site)
     * @throws ServiceException when something goes wrong
     */
    void generateFeaturesMetadata(String date) throws ServiceException;

    /**
     * Get a count of all FeaturesMeatadata objects in the repository
     *
     * @return  int representing count of FM objects in the repository
     */
    Long getCount();

    /**
     * Return all FM objects in the repository
     *
     * @return  a List containing all FM objects in the Repository
     */
    List<FeaturesMetadata> getAll();

    void update(FeaturesMetadata obj);

    void deleteAll();
}
