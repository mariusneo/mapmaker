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

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import org.jason.mapmaker.client.MapmakerPlaceManager;
import org.jason.mapmaker.client.presenter.*;
import org.jason.mapmaker.client.presenter.help.DisplayHelpPresenter;
import org.jason.mapmaker.client.view.*;
import org.jason.mapmaker.client.view.help.DisplayHelpPopup;

/**
 * Main GIN Client Module for the Mapmaker application.
 *
 * TODO: Clean this up by splitting into domain-specific client modules
 *
 * @author Jason Ferguson
 */
public class MapmakerClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {

        install(new DefaultModule(MapmakerPlaceManager.class));

        // install the domain-specific client modules, keeps this file cleaner and it's easier to find stuff
        install(new FeaturesMetadataClientModule());
        install(new ManageDataVersionsClientModule());
        install(new ShapefileMetadataClientModule());

        // presenter bindings
        bindPresenter(MapmakerAppShellPresenter.class,
                MapmakerAppShellPresenter.MyView.class,
                MapmakerAppShellView.class,
                MapmakerAppShellPresenter.MyProxy.class);

        // presenter widget bindings
        bindSingletonPresenterWidget(MapmakerDisclaimerViewPresenter.class,
                MapmakerDisclaimerViewPresenter.MyView.class,
                MapmakerDisclaimerViewImpl.class);

        bindSingletonPresenterWidget(MapmakerMapViewPresenter.class,
                MapmakerMapViewPresenter.MyView.class,
                MapmakerMapViewImpl.class);

        bindSingletonPresenterWidget(DisplayHelpPresenter.class,
                DisplayHelpPresenter.MyView.class,
                DisplayHelpPopup.class);

        bindSingletonPresenterWidget(MapmakerStackPanelPresenter.class,
                MapmakerStackPanelPresenter.MyView.class,
                MapmakerStackPanelView.class);


    }
}
