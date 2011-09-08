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
package org.jason.mapmaker.server.repository;

import java.util.List;

/**
 * Generic Repository interface
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public interface GenericRepository2<T> {

    T get(Long id);

    List<T> getAll();

    Long getCount();

    Long getCountByExample(T example);

    List<T> getByExample(T example);

    T save(T object);

    void update(T object);

    void delete(T object);

    void saveList(List<T> objectList);

    List<T> queryByExample(T example);

    void deleteAll();
}
