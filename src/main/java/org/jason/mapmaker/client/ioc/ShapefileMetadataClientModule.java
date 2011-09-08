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
import org.jason.mapmaker.client.presenter.shapefileMetadata.ImportShapefileMetadataPresenter;
import org.jason.mapmaker.client.presenter.shapefileMetadata.ManageShapefileMetadataPresenter;
import org.jason.mapmaker.client.view.shapefileMetadata.ImportShapefileMetadataPopup;
import org.jason.mapmaker.client.view.shapefileMetadata.ManageShapefileMetadataPopup;

/**
 * GIN Client Module for Shapefile Metadata presenters and views
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ShapefileMetadataClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {

        bindSingletonPresenterWidget(ImportShapefileMetadataPresenter.class,
                ImportShapefileMetadataPresenter.MyView.class,
                ImportShapefileMetadataPopup.class);

        bindSingletonPresenterWidget(ManageShapefileMetadataPresenter.class,
                ManageShapefileMetadataPresenter.MyView.class,
                ManageShapefileMetadataPopup.class);
    }
}
