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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.featuresMetadata.ManageFeaturesMetadataPresenter;
import org.jason.mapmaker.shared.model.FeaturesMetadata;

/**
 * GWT-Platform PopupView class for managing FeaturesMetadata (aka importing features from the USGS)
 *
 * @author Jason Ferguson
 * @since 0.4
 */
public class ManageFeaturesMetadataPopup extends PopupViewWithUiHandlers<ManageFeaturesMetadataUiHandler>
        implements ManageFeaturesMetadataPresenter.MyView {

    interface Binder extends UiBinder<PopupPanel, ManageFeaturesMetadataPopup> {
    }

    private static Binder binder = GWT.create(Binder.class);

    @UiField PopupPanel popupPanel;
    @UiField ScrollPanel scrollPanel;
    @UiField FlexTable featuresFlexTable;
    @UiField Button closeButton;

    private Widget widget;

    @Inject
    public ManageFeaturesMetadataPopup(EventBus eventBus) {
        super(eventBus);

        widget = binder.createAndBindUi(this);
        popupPanel.setWidth("600px");
        popupPanel.setHeight("400px");

        scrollPanel.setHeight("300px");
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public PopupPanel getPopupPanel() {
        return popupPanel;
    }

    public FlexTable getFeaturesFlexTable() {
        return featuresFlexTable;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    @Override
    public Button createImportButton(final FeaturesMetadata fm, final int rowIndex) {

        Button result = new Button();
        result.setText("Import");
        result.setStyleName("{style.gwt-Button}");

        result.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().doImport(fm, rowIndex);
            }
        });

        return result;
    }

    @Override
    public Button createDeleteButton(final FeaturesMetadata fm, final int rowIndex) {

        Button result = new Button();
        result.setText("Delete");
        result.setStyleName("{style.gwt-Button}");

        result.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().doDelete(fm, rowIndex);
            }
        });

        return result;
    }
}
