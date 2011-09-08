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
package org.jason.mapmaker.client.ioc;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
//import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import org.jason.mapmaker.client.presenter.*;
import org.jason.mapmaker.client.presenter.help.DisplayHelpPresenter;
import org.jason.mapmaker.client.view.MapmakerAppShellView;
import org.jason.mapmaker.client.view.MapmakerStackPanelView;

/**
 * MapmakerGinjector.java
 *
 * @author Jason Ferguson
 */
@GinModules({MapmakerClientModule.class, DispatchAsyncModule.class})
@SuppressWarnings("unused")
public interface MapmakerGinjector extends Ginjector {

    PlaceManager getPlaceManager();
    
    EventBus getEventBus();

    Provider<MapmakerAppShellPresenter> getMapmakerAppShellPresenter();

    AsyncProvider<MapmakerDisclaimerViewPresenter> getMapmakerDisclaimerViewPresenter();

    AsyncProvider<MapmakerMapViewPresenter> getMapmakerMapViewPresenter();

    AsyncProvider<MapmakerStackPanelPresenter> getMapmakerStackPanelPresenter();

    AsyncProvider<DisplayHelpPresenter> getMapmakerPopupHelpPresenter();

    MapmakerAppShellView getMapmakerAppShellView();
    
    MapmakerStackPanelView getMapmakerStackPanelView();


}
