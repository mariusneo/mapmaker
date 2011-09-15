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

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<MTFCC> getImportedMtfccs() {

        String hql = "SELECT DISTINCT L.mtfcc from Location L where L.mtfcc.superClass='Tabulation Area' ORDER BY L.mtfcc.mtfccCode";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<MTFCC> mtfccList = query.list();

        return mtfccList;
    }


    @Override
    // TODO: have ehcache create a cache of this method call
    public List<MTFCC> getAll() {

        Criteria mtfccCriteria = sessionFactory.getCurrentSession().createCriteria(MTFCC.class);
        mtfccCriteria.add(Restrictions.eq("superClass","Tabulation Area"));
        mtfccCriteria.addOrder(Order.asc("mtfccCode"));

        return mtfccCriteria.list();
    }

}
