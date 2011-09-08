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

import org.jason.mapmaker.shared.exceptions.RepositoryException;
import org.jason.mapmaker.shared.model.BorderPoint;
import org.jason.mapmaker.shared.model.Location;

import java.util.Map;

/**
 * Interface for BorderPoint repository
 *
 * @since 0.1
 * @author Jason Ferguson
 */
public interface BorderPointRepository extends GenericRepository2<BorderPoint> {

    /**
     * Return the bounds of a given location.
     *
     * @param l Location to return the bounds for
     * @return a Map<String, Double> with the bounding box
     */
    Map<String, Double> getBoundsByLocation(Location l) throws RepositoryException;

    /**
     * Delete all BorderPoint objects for a given Location (in case a cascade doesn't handle this...)
     *
     * @param L
     * @throws RepositoryException
     */
    void deleteByLocation(Location L) throws RepositoryException;
}
