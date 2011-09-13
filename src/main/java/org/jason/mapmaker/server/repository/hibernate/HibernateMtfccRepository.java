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
import org.jason.mapmaker.server.repository.MtfccRepository;
import org.jason.mapmaker.shared.model.MTFCC;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Hibernate implementation of MTFCC Repository
 *
 * @since 0.4
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
@Repository("mtfccRepository")
public class HibernateMtfccRepository extends HibernateGenericRepository<MTFCC> implements MtfccRepository{

    public HibernateMtfccRepository() {
        super(MTFCC.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getMtfccTypes() {

        // TODO: CONVERT THIS METHOD TO USE HIBERNATE MAP SYNTAX

        // doing this as hql instead of criteria because I'm not a huge fan of the unique result transformer, which
        // gets everything and then throws out the dupes. Why not let the DB server do the work?

        // get all the IN USE mtfcc codes
        String strQuery = "SELECT DISTINCT L.mtfcc.mtfccCode from Location L";
        Query query = sessionFactory.getCurrentSession().createQuery(strQuery);
        List<String> mtfccCodeList = (List<String>) query.list();

        // if the Location repository is empty (i.e. the first time the app is run w/ empty db tables), the app will
        // throw an exception when an empty collection is passed to the query below.
        if (mtfccCodeList.isEmpty()) {
            return new LinkedHashMap<String, String>();
        }

        // get the mtfcc code and feature class from the MTFCC repository based on the in-use MTFCCs
        String strQuery2 = "SELECT M.mtfccCode, M.featureClass from MTFCC M where M.superClass = 'Tabulation Area' and M.mtfccCode in :codeList";
        Query query2 = sessionFactory.getCurrentSession().createQuery(strQuery2);
        query2.setParameterList("codeList", mtfccCodeList);
        List resultList = query2.list();

        Map<String, String> resultMap = new LinkedHashMap<String, String>(); // want to preserve the insert order since its alpha
        for (Object o : resultList) {
            Object[] o2 = (Object[]) o;
            String key = (String) o2[1];     // map description to mtfcc code
            String value = (String) o2[0];
            resultMap.put(key, value); // dear god this is ugly
        }

        return resultMap;
    }

    @Override
    // TODO: have ehcache create a cache of this method call
    public List<MTFCC> getAll() {

        Criteria mtfccCriteria = sessionFactory.getCurrentSession().createCriteria(MTFCC.class);
        mtfccCriteria.add(Restrictions.eq("superclass","Tabulation Area"));
        mtfccCriteria.addOrder(Order.asc("mtfccCode"));

        return mtfccCriteria.list();
    }

    @Override
    public Map<MTFCC, Long> getMtfccFeatureCount() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
