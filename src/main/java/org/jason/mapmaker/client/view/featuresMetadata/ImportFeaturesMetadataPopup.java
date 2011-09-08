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
package org.jason.mapmaker.client.view.featuresMetadata;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.featuresMetadata.ImportFeaturesMetadataPresenter;

/**
 * @author Jason Ferguson
 */
public class ImportFeaturesMetadataPopup extends PopupViewWithUiHandlers<ImportFeaturesMetadataUiHandler>
        implements ImportFeaturesMetadataPresenter.MyView {

    interface Binder extends UiBinder<PopupPanel, ImportFeaturesMetadataPopup> {}

    private static Binder binder = GWT.create(Binder.class);

    @UiField DateBox dateBox;
    @UiField PopupPanel popupPanel;
    @UiField Button importButton;

    private Widget widget;

    @Inject
    public ImportFeaturesMetadataPopup(EventBus eventBus) {
        super(eventBus);

        widget = binder.createAndBindUi(this);

        popupPanel.setWidth("600px");
        popupPanel.setHeight("400px");
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public DateBox getDateBox() {
        return dateBox;
    }

    @Override
    public Button getImportButton() {
        return importButton;
    }
}
