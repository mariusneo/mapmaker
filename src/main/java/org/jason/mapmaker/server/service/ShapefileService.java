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
import org.jason.mapmaker.shared.model.ShapefileMetadata;

import java.util.Map;

/**
 * ShapefileService.java
 *
 * Defines operations for implementations of ShapefileService
 *
 * @author Jason Ferguson
 */
public interface ShapefileService {

    /**
     * Set the LocationRepository for the class. Needed in the interface for unit testing/Mock Object purposes
     *
     * @param repository    repository to set
     */
    void setLocationRepository(LocationRepository repository);

    /**
     * Process a Shapefile by converting it to Location objects containing a List of BorderPoints, then persisting it
     * via the LocationRepository
     *
     * @param shapefileName     String containing the name of the Shapefile
     * @throws ServiceException     thrown when something goes wrong
     */
    void processShapefile(String shapefileName) throws ServiceException;

    Map<String, Double> getBoundsByLocation(Location l) throws ServiceException;

    /**
     * Delete a Shapefile based on information contained in the ShapefileMetadata object
     *
     * @param sm        ShapefileMetadata containing information on what Location/BorderPoint set needs to be deleted
     * @throws ServiceException  wraps any exceptions thrown
     */
    void deleteByShapefileMetadata(ShapefileMetadata sm) throws ServiceException;
}
