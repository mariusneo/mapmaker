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
package org.jason.mapmaker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.View;
import org.jason.mapmaker.client.presenter.MapmakerAppShellPresenter;


/**
 * MapmakerAppShellView.java
 *
 * @author Jason Ferguson
 */
@Singleton
public class MapmakerAppShellView extends DockLayoutPanel implements MapmakerAppShellPresenter.MyView, View {

    @UiTemplate("MapmakerAppShellView.ui.xml")
    interface Binder extends UiBinder<DockLayoutPanel, MapmakerAppShellView> {
    }

    private static final Binder binder = GWT.create(Binder.class);

    @UiField DockLayoutPanel mapmakerAppShellView;
    @UiField(provided=true) Widget stackPanel;     // this is a Widget so that it can accept the StackPanelView.asWidget() results
    @UiField(provided=true) Widget mapPanel;

    @Inject
    public MapmakerAppShellView(MapmakerStackPanelView stackPanel, MapmakerMapViewImpl mapPanel) {
        super(Style.Unit.PX);
        this.stackPanel = stackPanel.asWidget();
        this.mapPanel = mapPanel.asWidget();
        
        add(binder.createAndBindUi(this));
    }

    @Override
    public Widget asWidget() {
        return mapmakerAppShellView.asWidget();
    }

    @Override
    public void addToSlot(Object o, Widget widget) {

    }

    @Override
    public void removeFromSlot(Object o, Widget widget) {

    }

    @Override
    public void setInSlot(Object o, Widget widget) {

    }
}
