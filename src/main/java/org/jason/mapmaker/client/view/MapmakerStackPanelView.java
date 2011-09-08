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
package org.jason.mapmaker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.jason.mapmaker.client.presenter.MapmakerStackPanelPresenter;

/**
 * @since 0.1
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
@Singleton
public class MapmakerStackPanelView extends ViewWithUiHandlers<StackPanelUiHandlers>
                                    implements MapmakerStackPanelPresenter.MyView, IsWidget {

    @UiTemplate("MapmakerStackPanelView.ui.xml")
    interface Binder extends UiBinder<Widget, MapmakerStackPanelView> {
    }

    private static Binder binder = GWT.create(Binder.class);


    @UiField StackLayoutPanel mapmakerStackPanelView;
    @UiField FormPanel stackFeaturesPanelView;
    @UiField VerticalPanel stackFeaturesAlignmentPanel;
    @UiField ListBox borderTypeListBox;
    @UiField VerticalPanel borderSelectors;
    @UiField ListBox primaryListBox;
    @UiField ListBox secondaryListBox;
    @UiField ListBox tertiaryListBox;
    @UiField ListBox featuresListBox;
    @UiField Button redrawMapButton;
    @UiField FormPanel stackExportPanelView;
    @UiField Button exportKMLButton;
    @UiField Button exportShapefileButton;
    @UiField FormPanel stackAdminPanelView;
    @UiField Button showBordersManagementButton;
    @UiField Button showFeaturesManagementButton;
    @UiField Button showManageAvailableDataButton;
    @UiField FormPanel stackHelpPanelView;
    @UiField Button displayHelpButton;

    private final Widget widget;

    public MapmakerStackPanelView() {
        widget = binder.createAndBindUi(this);

        borderTypeListBox.setVisibleItemCount(4);
        featuresListBox.setVisibleItemCount(4);

        primaryListBox.addItem("-- Select State/County Based Border --");
        secondaryListBox.addItem("-- Select County Based Border --");

        secondaryListBox.setEnabled(false);
        tertiaryListBox.setEnabled(false);

    }

    /** Implementation of MyView interface **/

    @Override
    public ListBox getBorderTypeListBox() {
        return this.borderTypeListBox;
    }

    public ListBox getPrimaryListBox() {
        return primaryListBox;
    }

    public ListBox getSecondaryListBox() {
        return secondaryListBox;
    }

    public ListBox getTertiaryListBox() {
        return tertiaryListBox;
    }

    public Button getRedrawMapButton() {
        return redrawMapButton;
    }

    @Override
    public ListBox getFeaturesListBox() {
        return featuresListBox;
    }

    /** Implementation of IsWidget **/

    @Override
    public Widget asWidget() {
        return widget;
    }

    @UiHandler("displayHelpButton")
    void onDisplayHelp(ClickEvent event) {
        getUiHandlers().onDisplayHelp();
    }


    @UiHandler("showBordersManagementButton")
    void OnDisplayManageBordersPopup(ClickEvent event) {
        getUiHandlers().onDisplayManageBorders();
    }

    @UiHandler("showFeaturesManagementButton")
    void onDisplayManageFeaturesPopup(ClickEvent event) {
        getUiHandlers().onDisplayManageFeatures();
    }

    @UiHandler("showManageAvailableDataButton")
    void onDisplayManageAvailableDataPopup(ClickEvent event) {
        getUiHandlers().onDisplayManageAvailableData();
    }

    @UiHandler("redrawMapButton")
    void onRedrawMap(ClickEvent event) {
        getUiHandlers().onRedrawMap();
    }

    public void displayAngryMessage(String msg) {
        Window.alert(msg);
    }


}
