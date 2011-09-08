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
package org.jason.mapmaker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import org.jason.mapmaker.client.ioc.MapmakerGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mapmaker implements EntryPoint {

    public final MapmakerGinjector ginjector = GWT.create(MapmakerGinjector.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        DelayedBindRegistry.bind(ginjector);

        ginjector.getPlaceManager().revealDefaultPlace();
    }


}
