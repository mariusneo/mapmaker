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
package org.jason.mapmaker.client.view.dataVersions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.dataVersions.ManageDataVersionsPresenter;

/**
 * GWTP View for managing the version data versions
 *
 * @since 0.4.2
 * @author Jason Ferguson
 */
public class ManageDataVersionsPopup extends PopupViewWithUiHandlers<ManageDataVersionsHandlers>
    implements ManageDataVersionsPresenter.MyView {

    interface Binder extends UiBinder<PopupPanel, ManageDataVersionsPopup> {}

    private static Binder binder = GWT.create(Binder.class);

    @UiField PopupPanel popupPanel;
    @UiField TextBox tigerLineYear;
    @UiField TextBox usgsFeaturesDate;
    @UiField Button updateTigerYearButton;
    @UiField Button updateUsgsDateButton;
    @UiField Button closeButton;

    private Widget widget;

    @Inject
    public ManageDataVersionsPopup(EventBus eventBus) {
        super(eventBus);

        widget = binder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public PopupPanel getPopupPanel() {
        return popupPanel;
    }

    public TextBox getTigerLineYear() {
        return tigerLineYear;
    }

    public TextBox getUsgsFeaturesDate() {
        return usgsFeaturesDate;
    }

    public Button getUpdateTigerYearButton() {
        return updateTigerYearButton;
    }

    public Button getUpdateUsgsDateButton() {
        return updateUsgsDateButton;
    }

    public Button getCloseButton() {
        return closeButton;
    }
}
