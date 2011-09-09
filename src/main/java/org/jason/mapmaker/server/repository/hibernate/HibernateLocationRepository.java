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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jason.mapmaker.server.repository.LocationRepository;
import org.jason.mapmaker.shared.model.Location;
import org.jason.mapmaker.shared.model.MTFCC;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Hibernate implementation of LocationRepository interface
 *
 * @author Jason Ferguson
 * @since 0.4
 */
@Repository("locationRepository")
public class HibernateLocationRepository extends HibernateGenericRepository<Location> implements LocationRepository {

    public HibernateLocationRepository() {
        super(Location.class);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Map<String, Long> getLocationCounts() {

        String strQuery = "select new map(L.mtfcc.mtfccCode, COUNT(DISTINCT L.mtfcc.mtfccCode)) from Location L";
        Query query = sessionFactory.getCurrentSession().createQuery(strQuery);
        List resultList = query.list();

        if (resultList.size() == 0) {
            return new LinkedHashMap<String, Long>();
        }

        Map<String, Long> resultMap = (Map<String, Long>) resultList.get(0);
        return new LinkedHashMap(resultMap);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Map<String, String>> getStateAndEquivalentListForMtfcc(MTFCC mtfcc) {

        // it doesn't seem possible to do this as a QBE due to the subquery. Eventually, it was just taking too much
        // time to figure out

        String strQuery = "SELECT new map(l.stateFP as stateFP, l.name as name) from Location l where l.geoId IN (" +
                "SELECT DISTINCT l.stateFP FROM Location l where l.mtfcc.mtfccCode=:code ORDER BY l.name) order by l.name";
        Query query = sessionFactory.getCurrentSession().createQuery(strQuery);
        query.setParameter("code", mtfcc.getMtfccCode());

        List resultList = query.list();
        return resultList;


    }

    @Transactional(readOnly = true)
    public Location getByCompleteGeoId(String geoId) {

        String hql1 = "from Location as L where L.geoId = :geoId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql1);
        query.setString("geoId", geoId);

        return (Location) query.list().get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationsByCoordinates(double lng, double lat) {

        String hql = "from Location L where L.minLat <= :lat AND L.maxLat >= :lat AND L.minLng <= :lng AND L.maxLng >= :lng";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble("lat", lat);
        query.setDouble("lng", lng);

        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocations(String mtfccCode, String... fpCodes) {

        Criteria locationCriteria = sessionFactory.getCurrentSession().createCriteria(Location.class);
        Criteria mtfccCriteria = locationCriteria.createCriteria("mtfcc");
        mtfccCriteria.add(Restrictions.eq("mtfccCode", mtfccCode));

        if (fpCodes.length == 1) {
            locationCriteria.add(Restrictions.eq("stateFP", fpCodes[0]));
        }

        if (fpCodes.length == 2) {
            locationCriteria.add(Restrictions.eq("stateFP", fpCodes[0]));
            locationCriteria.add(Restrictions.eq("countyFP", fpCodes[1]));
        }

        locationCriteria.addOrder(Order.asc("name"));

        return locationCriteria.list();
    }
}
