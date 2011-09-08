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

import org.jason.mapmaker.server.repository.BorderPointRepository;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.BorderPoint;
import org.jason.mapmaker.shared.model.Location;

import java.util.List;
import java.util.Map;

/**
 * Interface for services related to borderpoints.
 *
 * (I had tried to avoid a service dedicated to BorderPoint objects but I guess I can't avoid it anymore
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public interface BorderPointService {

    void setBorderPointRepository(BorderPointRepository repository);

    void setLocationService(LocationService locationService);

    void saveList(List<BorderPoint> borderPointList) throws ServiceException;

    /**
     * Delete BorderPoint objects based on the geoId and mtfccCode fields of their Location component.
     * @param geoId
     * @param mtfccCode
     * @throws ServiceException
     */
    void deleteByGeoIdAndMtfcc(String geoId, String mtfccCode) throws ServiceException;

    Map<String, Double> getBoundsByLocation(Location l) throws ServiceException;
}
