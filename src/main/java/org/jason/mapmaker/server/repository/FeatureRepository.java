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
package org.jason.mapmaker.server.repository;

import org.jason.mapmaker.shared.exceptions.RepositoryException;
import org.jason.mapmaker.shared.model.Feature;

import java.util.List;
import java.util.Map;

/**
 * Interface for FeatureRepository implementations
 *
 * @since 0.3
 * @author Jason Ferguson
 */
public interface FeatureRepository extends GenericRepository2<Feature> {

    /**
     * Return a List of just the feature class names
     * @return
     */
    List<String> getFeatureClasses();

    /**
     * Return a list of features of a given type within a bounding box defined by a Map<String, Double>.
     *
     * @param boundingBox       Map<String, Double> defining the bounding box to return the features for
     * @param featureClassName  String containing the name of the feature to return
     * @return      a List<Feature> of feature objects whose lat/lng are within the bounding box
     */
    List<Feature> getFeaturesByBoxAndFeatureClassName(Map<String, Double> boundingBox, String featureClassName);

    /**
     * Return feature class names mapped to a Integer containing how many instances of that Feature are persisted.
     * @return
     */
    Map<String, Long> getFeatureCounts();

    /**
     * Delete all features given a state geoid
     *
     * @param stateGeoId    String representing the state geoid
     */
    int deleteByStateGeoId(String stateGeoId) throws RepositoryException;
}
