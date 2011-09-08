/**
 * Copyright 2011 Jason Ferguson.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jason.mapmaker.client.ioc;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.jason.mapmaker.client.presenter.featuresMetadata.ImportFeaturesMetadataPresenter;
import org.jason.mapmaker.client.presenter.featuresMetadata.ManageFeaturesMetadataPresenter;
import org.jason.mapmaker.client.view.featuresMetadata.ImportFeaturesMetadataPopup;
import org.jason.mapmaker.client.view.featuresMetadata.ManageFeaturesMetadataPopup;

/**
 * GIN Client Module for FeaturesMetadata presenters and views
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class FeaturesMetadataClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {

        bindSingletonPresenterWidget(ImportFeaturesMetadataPresenter.class,
                ImportFeaturesMetadataPresenter.MyView.class,
                ImportFeaturesMetadataPopup.class);

        bindSingletonPresenterWidget(ManageFeaturesMetadataPresenter.class,
                ManageFeaturesMetadataPresenter.MyView.class,
                ManageFeaturesMetadataPopup.class);
    }
}
