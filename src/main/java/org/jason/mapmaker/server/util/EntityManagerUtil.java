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
package org.jason.mapmaker.server.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * EntityManagerUtil.java
 *
 *  Copyright 2011 Jason Ferguson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Simple Factory class to get the EntityManagerFactory as a Singleton, etc
 * 
 * @author TSgt Jason Ferguson
 */
public final class EntityManagerUtil {

    private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

    private EntityManagerUtil() {

    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emfInstance;
    }
}
