/**
 * Copyright 2011 Jason Ferguson.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jason.mapmaker.server.service;

import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.ShapefileMetadata;

import java.util.List;

/**
 * Interface for Shapefile metadata methods
 *
 * @author Jason Ferguson
 * @since 0.4
 */
public interface ShapefileMetadataService {

    /**
     * Generates the ShapefileMetadata repository given the year (version) of the TIGER/Line files
     *
     * @param year      String representing the year of the files (currently between 2008 and 2010)
     *
     * @throws ServiceException     thrown when something went wrong
     */
    void generateMetadata(String year) throws ServiceException;

    /**
     * Generate the ShapefileMetadata repository. The method determines what SHOULD exist then connects via
     * FTP to check that it actually DOES exist.
     */
    void generateMetadata2() throws ServiceException;

    /**
     * Get a count of all of the ShapefileMetada objects persisted to the repository
     *
     * @return an int representing the count of persisted ShapefileMetadata objects
     */
    long getCount();

    /**
     * Return a list of ShapefileMetadata objects with the given GeoId
     *
     * @param geoId
     * @return
     */
    List<ShapefileMetadata> getByGeoId(String geoId);

    /**
     * Update a transient ShapefileMetadata object
     *
     * @param sm    ShapefileMetadata object to persist
     * @return      updated object
     * @throws ServiceException   thrown when something went wrong
     */
    ShapefileMetadata save(ShapefileMetadata sm) throws ServiceException;

    /**
     * Update a ShapefileMetadata object
     *
     * @param sm
     * @throws ServiceException
     */
    void update(ShapefileMetadata sm) throws ServiceException;

    /**
     * Delete everything in the repository
     *
     * @throws ServiceException
     */
    void deleteAll() throws ServiceException;

}
