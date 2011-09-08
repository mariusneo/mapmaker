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

import org.jason.mapmaker.server.repository.GenericSettingsRepository;
import org.jason.mapmaker.shared.model.GenericSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of GenericSettingsService interface
 *
 * @since 0.4.2
 * @author Jason Ferguson
 */
@Service("genericSettingsService")
public class GenericSettingsServiceImpl implements GenericSettingsService {

    GenericSettingsRepository genericSettingsRepository;

    @Autowired
    public void setGenericSettingsRepository(GenericSettingsRepository genericSettingsRepository) {
        this.genericSettingsRepository = genericSettingsRepository;
    }

    @Override
    public GenericSettings get() {
        return genericSettingsRepository.get((long) 1000);
    }

    @Override
    public void update(GenericSettings obj) {
        genericSettingsRepository.update(obj);
    }
}
