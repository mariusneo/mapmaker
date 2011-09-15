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
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.jason.mapmaker.server.repository.GenericRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import java.util.List;

/**
 * Hibernate generic repository
 * <p/>
 * God, I was SO DAMN SICK of OpenJPA
 *
 * @author Jason Ferguson
 * @since 0.4
 */
public class HibernateGenericRepository<T> implements GenericRepository2<T> {

    protected SessionFactory sessionFactory;
    protected HibernateTransactionManager transactionManager;
    private Class<T> clazz;


    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setTransactionManager(HibernateTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public HibernateGenericRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public T get(Long id) {

        return (T) sessionFactory.getCurrentSession().get(clazz, id.intValue());
    }

    public List<T> getAll() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);

        return criteria.list();
    }

    public Long getCount() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();

    }

    public Long getCountByExample(T example) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria.add(Example.create(example));
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

    @SuppressWarnings("unchecked")
    public List<T> getByExample(T example) {

        Example ex = Example.create(example);
        ex.excludeZeroes();

        List<T> results = (List<T>) sessionFactory.getCurrentSession().createCriteria(clazz).add(ex).list();
        return results;
    }

    @SuppressWarnings("unchecked")
    public T save(T object) {
        Integer id = (Integer) sessionFactory.getCurrentSession().save(object);
        return get(id.longValue());
    }

    public void update(T object) {

        sessionFactory.getCurrentSession().update(object);
    }

    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    public void saveList(List<T> objectList) {

        for (T obj: objectList) {
            try {
                sessionFactory.getCurrentSession().save(obj);
            } catch (DataIntegrityViolationException ex) {
                // okay, so CVE extends RuntimeException, and you aren't supposed to catch RuntimeExceptions. Do
                // I want to roll back an entire save operation just because the USGS screwed up one particular
                // id number???
                System.out.println("Caught DataIntegrityViolationException");
            }
        }
//        Session session = sessionFactory.getCurrentSession();
//        Transaction tx = session.beginTransaction();
//        int i = 1;
//        for (T obj : objectList) {
//
//            session.save(obj);
//            //sessionFactory.getCurrentSession().save(obj);
//            i++;
//            if (i % 500 == 0) {
//                session.flush();
//                session.clear();
//                //sessionFactory.getCurrentSession().flush();
//            }
//        }
//        if (!tx.wasCommitted()) {
//            tx.commit();
//        }
        //sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public List<T> queryByExample(T example) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria.add(Example.create(example));

        List<T> resultList = criteria.list();
        Hibernate.initialize(resultList);

        return resultList;
        //return criteria.list();
    }

    public void deleteAll() {

        List<T> deleteList = getAll();
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        hibernateTemplate.deleteAll(deleteList);
    }
}
