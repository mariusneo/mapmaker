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

import org.jason.mapmaker.server.repository.FeaturesMetadataRepository;
import org.jason.mapmaker.shared.exceptions.ServiceException;
import org.jason.mapmaker.shared.model.FeaturesMetadata;
import org.jason.mapmaker.shared.util.GeographyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jason Ferguson
 */
@Service("featuresMetadataService")
public class FeaturesMetadataServiceImpl implements FeaturesMetadataService {

    private FeaturesMetadataRepository featuresMetadataRepository;

    @Autowired
    public void setFeaturesMetadataRepository(FeaturesMetadataRepository featuresMetadataRepository) {
        this.featuresMetadataRepository = featuresMetadataRepository;
    }

    @Override
    public FeaturesMetadata get(long id) {
        return featuresMetadataRepository.get(id);
    }

    @Override
    public void save(FeaturesMetadata fm) throws ServiceException {
        featuresMetadataRepository.save(fm);
    }

    @Override
    public void getByStateGeoId(String stateGeoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateFeaturesMetadata(String date) throws ServiceException {

        for (String state : GeographyUtils.stateAbbreviations.keySet()) {

            String abbreviation = GeographyUtils.getAbbreviationForState(state);
            String filename = "http://geonames.usgs.gov/docs/stategaz/" + abbreviation + "_Features_" + date + ".zip";

            FeaturesMetadata fm = new FeaturesMetadata();
            fm.setState(state);
            fm.setFilename(filename);
            fm.setUsgsDate(date);
            fm.setStateAbbr(abbreviation);
            fm.setStateGeoId(GeographyUtils.getGeoIdForState(state));
            fm.setCurrentStatus(GeographyUtils.Status.NOT_IMPORTED);

            featuresMetadataRepository.save(fm);
        }
    }

    @Override
    public Long getCount() {
        return featuresMetadataRepository.getCount();
    }

    @Override
    public List<FeaturesMetadata> getAll() {
        return featuresMetadataRepository.getAll();
    }

    @Override
    public void update(FeaturesMetadata obj) {
        featuresMetadataRepository.update(obj);
    }

    @Override
    public void deleteAll() {
        featuresMetadataRepository.deleteAll();
    }
}
