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

import org.jason.mapmaker.server.repository.GenericSettingsRepository;
import org.jason.mapmaker.shared.model.GenericSettings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Hibernate implementation of the GenericSettings repository
 *
 * I've overloaded alot of items here to ensure that the settings aren't deleted by accident/intention
 *
 * @since 0.4.2
 * @author Jason Ferguson
 */
@Repository("genericSettingsRepository")
public class HibernateGenericSettingsRepository extends HibernateGenericRepository<GenericSettings>
    implements GenericSettingsRepository {

    public HibernateGenericSettingsRepository() {
        super(GenericSettings.class);
    }

    @Override
    public Long getCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getCountByExample(GenericSettings example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<GenericSettings> getByExample(GenericSettings example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(GenericSettings object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveList(List<GenericSettings> objectList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<GenericSettings> queryByExample(GenericSettings example) {
        throw new UnsupportedOperationException();
    }
}
