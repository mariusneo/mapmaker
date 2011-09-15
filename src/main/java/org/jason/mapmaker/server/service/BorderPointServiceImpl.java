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
import org.jason.mapmaker.shared.exceptions.RepositoryException;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.BorderPoint;
import org.jason.mapmaker.shared.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Implementation of BorderPointService interface
 *
 * @since 0.4
 * @author Jason Ferguson
 */
@Service("borderPointService")
public class BorderPointServiceImpl implements BorderPointService {

    Logger log = LoggerFactory.getLogger(BorderPointServiceImpl.class);

    private BorderPointRepository borderPointRepository;
    private LocationService locationService;

    @Autowired
    public void setBorderPointRepository(BorderPointRepository borderPointRepository) {
        this.borderPointRepository = borderPointRepository;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    @Transactional
    public void saveList(List<BorderPoint> borderPointList) throws ServiceException {

        try {
            borderPointRepository.saveList(borderPointList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Map<String, Double> getBoundsByLocation(Location l) throws ServiceException {
        try {
            return borderPointRepository.getBoundsByLocation(l);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
