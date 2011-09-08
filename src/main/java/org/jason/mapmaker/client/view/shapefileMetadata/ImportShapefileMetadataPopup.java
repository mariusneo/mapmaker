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
package org.jason.mapmaker.client.view.shapefileMetadata;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.shapefileMetadata.ImportShapefileMetadataPresenter;

/**
 * GWT-Plaform view class for handling the import of the shapefile metadata from the Census Dept FTP site
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportShapefileMetadataPopup extends PopupViewWithUiHandlers<ImportShapefileMetadataUiHandlers>
        implements ImportShapefileMetadataPresenter.MyView {

    interface Binder extends UiBinder<PopupPanel, ImportShapefileMetadataPopup> {}

    private static Binder binder = GWT.create(Binder.class);

    @UiField PopupPanel popupPanel;
    @UiField Button importButton;

    private Widget widget;

    @Inject
    public ImportShapefileMetadataPopup(EventBus eventBus) {
        super(eventBus);

        widget = binder.createAndBindUi(this);

        popupPanel.setWidth("600px");
        popupPanel.setHeight("400px");
    }

    @Override
    public Button getImportButton() {
        return importButton;
    }

    @Override
    public Widget asWidget() {
        return widget;
    }
}
