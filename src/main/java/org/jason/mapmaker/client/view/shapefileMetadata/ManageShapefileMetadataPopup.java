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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.shapefileMetadata.ManageShapefileMetadataPresenter;
import org.jason.mapmaker.shared.model.ShapefileMetadata;

/**
 * GWT-Platform view class for the manage Shapefile metadata (aka Borders) popup window
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ManageShapefileMetadataPopup extends PopupViewWithUiHandlers<ManageShapefileMetadataUiHandlers>
        implements ManageShapefileMetadataPresenter.MyView {

    interface Binder extends UiBinder<PopupPanel, ManageShapefileMetadataPopup> {}

    private static Binder binder = GWT.create(Binder.class);

    @UiField PopupPanel popupPanel;
    @UiField Button detailsButton;
    @UiField ListBox stateListBox;
    @UiField FlexTable bordersFlexTable;
    @UiField Button closeButton;

    private Widget widget;

    @Inject
    public ManageShapefileMetadataPopup(EventBus eventBus) {
        super(eventBus);

        widget = binder.createAndBindUi(this);

        popupPanel.setWidth("600px");
        popupPanel.setHeight("400px");
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }

    public ListBox getStateListBox() {
        return stateListBox;
    }

    public FlexTable getBordersFlexTable() {
        return bordersFlexTable;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public Button createImportButton(final ShapefileMetadata sm, final int rowIndex) {

        Button result = new Button();
        result.setText("Import");
        result.setStyleName("{style.gwt-Button}");

        result.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().doImport(sm, rowIndex);
            }
        });
        return result;
    }

    public Button createDeleteButton(final ShapefileMetadata sm, final int rowIndex) {

        Button result = new Button();
        result.setText("Delete");
        result.setStyleName("{style.gwt-Button}");

        result.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().doDelete(sm, rowIndex);
            }
        });

        return result;
    }
}
