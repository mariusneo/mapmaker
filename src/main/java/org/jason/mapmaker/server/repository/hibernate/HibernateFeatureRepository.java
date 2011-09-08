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
package org.jason.mapmaker.server.repository.hibernate;

import org.hibernate.Query;
import org.jason.mapmaker.server.repository.FeatureRepository;
import org.jason.mapmaker.shared.exceptions.RepositoryException;
import org.jason.mapmaker.shared.model.Feature;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Hibernate implementation of FeatureRepository interface
 *
 * @author Jason Ferguson
 * @since 0.4
 */
@Repository("featureRepository")
public class HibernateFeatureRepository extends HibernateGenericRepository<Feature> implements FeatureRepository {

    public HibernateFeatureRepository() {
        super(Feature.class);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<String> getFeatureClasses() {

        String strQuery = "SELECT DISTINCT F.featureClass FROM Feature F ORDER BY F.featureClass";
        Query query = sessionFactory.getCurrentSession().createQuery(strQuery);

        return (List<String>) query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature> getFeaturesByBoxAndFeatureClassName(Map<String, Double> boundingBox, String featureClassName) {

        String strQuery = "SELECT F FROM Feature F WHERE F.lat > :minLat AND F.lat < :maxLat " +
                "AND F.lng > :minLng AND F.lng < :maxLng " +
                "AND F.featureClass = :featureClass";
        Query query = sessionFactory.getCurrentSession().createQuery(strQuery);

        query.setParameter("minLat", boundingBox.get("MINLAT"));
        query.setParameter("maxLat", boundingBox.get("MAXLAT"));
        query.setParameter("minLng", boundingBox.get("MINLNG"));
        query.setParameter("maxLng", boundingBox.get("MAXLNG"));
        query.setParameter("featureClass", featureClassName);

        List<Feature> results = query.list();

        return results;
    }

    @Override
    public Map<String, Long> getFeatureCounts() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public int deleteByStateGeoId(String stateGeoId) throws RepositoryException {

        // the delete needs a subselect since bulk ops can't have any joins, explicit or implicit
        String hql = "delete from Feature F where F.featuresMetadata IN (select FM from FeaturesMetadata FM where FM.stateGeoId=:stateGeoId)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("stateGeoId", stateGeoId);

        return query.executeUpdate();
    }
}
