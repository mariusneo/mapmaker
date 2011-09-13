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
package org.jason.mapmaker.client.view.help;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.help.DisplayHelpPresenter;

/**
 * GWTP PopupView for displaying the application help
 *
 * @since 0.1
 * @author Jason Ferguson
 */
public class DisplayHelpPopup extends PopupViewWithUiHandlers<DisplayHelpUiHandlers>
        implements DisplayHelpPresenter.MyView, IsWidget {

    interface Binder extends UiBinder<PopupPanel, DisplayHelpPopup> {}

    private static Binder binder = GWT.create(Binder.class);

    @UiField ScrollPanel scrollPanel;
    @UiField Button closeButton;

    private final PopupPanel widget;

    @Inject
    public DisplayHelpPopup(EventBus eventBus) {
        super(eventBus);
        widget = binder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @UiHandler("closeButton")
    public void onClose(ClickEvent event) {
        getUiHandlers().doClose();
    }
}
