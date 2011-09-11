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
import org.jason.mapmaker.shared.model.Feature;
import org.jason.mapmaker.shared.model.FeaturesMetadata;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.server.repository.FeatureRepository;

import java.util.List;
import java.util.Map;

/**
 * This class is the interface for any implementations of the Feature Service (regular java or mock object)
 *
 * @author Jason Ferguson
 * @since 0.3
 */
public interface FeatureService extends PersistenceService<Feature> {

    /**
     * Set the feature repository, either Autowired or to inject a Mock for unit testing
     *
     * @param repository FeatureRepository to use
     */
    void setFeatureRepository(FeatureRepository repository);

    /* Persistence Methods */

    /**
     * Delete all Feature objects related to a given FeaturesMetadata object
     *
     * @param fm FeaturesMetadata object
     * @throws ServiceException thrown when something goes wrong
     */
    void deleteByFeaturesMetadata(FeaturesMetadata fm) throws ServiceException;

    /**
     * Import all features from an external USGS file based on the FeaturesMetadata object
     *
     * @param fm    FeaturesMetadata object which determines what to import
     * @throws ServiceException     something went wrong...
     */
    void importFromFeaturesMetadata(FeaturesMetadata fm) throws ServiceException;

    /**
     * Import features from the URL pointing to the USGS features for the state
     *
     * @param url URL of the state's USGS features
     * @param fm  FeaturesMetadata object "owning" the feature
     * @throws ServiceException
     */
    void importFromUrl(String url, FeaturesMetadata fm) throws ServiceException;

    /**
     * Update a Feature object
     * TODO: Is this still necessary?
     *
     * @param obj   Location object to update
     */
    void update(Feature obj);

    /**
     * Delete all Feature objects from the repository
     */
    void deleteAll();

    /**
     * Persist a list of Entity objects
     *
     * @param featureList List<Feature> of objects that need to be persisted to the datastore
     * @throws ServiceException Thrown if anything goes wrong
     */
    void saveList(List<Feature> featureList) throws ServiceException;

    /* Other methods */

    /**
     * Return a distinct list of Feature Class names (i.e. "Airport","Church",etc).
     *
     * @return a List<String> of Feature Class Names
     */
    List<String> getFeatureClasses();

    /**
     * Get features given a location, a bounding box, and a feature type
     * <p/>
     * The bounding box is used to narrow the results, then the service is expected to throw out the ones not inside the
     * borderpoints of the location
     *
     * @param location         Location object to get the features for
     * @param featureClassName what Feature Class name to display ("airport","church", etc)
     * @return a List<Feature> of the Feature objects that are within the Location's borders
     */
    List<Feature> getFeatures(Location location, String featureClassName);

    /**
     * Return a Map of feature class names and the count of Feature objects with that particular feature class name
     *
     * @return
     */
    Map<String, Long> getFeatureCounts();

    /**
     * Generate a URL
     *
     * @param geoId
     * @param dateUpdated
     * @return
     */
    String generateUrl(String geoId, String dateUpdated);


}
