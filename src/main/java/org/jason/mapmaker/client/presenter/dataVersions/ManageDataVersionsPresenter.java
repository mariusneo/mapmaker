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
package org.jason.mapmaker.client.presenter.dataVersions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import org.jason.mapmaker.client.view.dataVersions.ManageDataVersionsHandlers;
import org.jason.mapmaker.shared.action.availableData.GetTigerLineVersionAction;
import org.jason.mapmaker.shared.action.availableData.GetUsgsFeaturesVersionAction;
import org.jason.mapmaker.shared.action.availableData.UpdateTigerLineVersionAction;
import org.jason.mapmaker.shared.action.availableData.UpdateUsgsFeaturesVersionAction;
import org.jason.mapmaker.shared.result.availableData.GetTigerLineVersionResult;
import org.jason.mapmaker.shared.result.availableData.GetUsgsFeaturesVersionResult;
import org.jason.mapmaker.shared.result.availableData.UpdateTigerLineVersionResult;
import org.jason.mapmaker.shared.result.availableData.UpdateUsgsFeaturesVersionResult;

/**
 * GWTP Presenter for managing the data versions (TIGER/Line, USGS)
 *
 * @author Jason Ferguson
 * @since 0.4.2
 */
public class ManageDataVersionsPresenter extends PresenterWidget<ManageDataVersionsPresenter.MyView>
        implements ManageDataVersionsHandlers {

    public interface MyView extends PopupView, HasUiHandlers<ManageDataVersionsHandlers> {

        TextBox getTigerLineYear();

        TextBox getUsgsFeaturesDate();

        PopupPanel getPopupPanel();

        Button getUpdateTigerYearButton();

        Button getUpdateUsgsDateButton();

        Button getCloseButton();
    }


    private DispatchAsync dispatch;

    @Inject
    public ManageDataVersionsPresenter(EventBus eventBus, MyView view, DispatchAsync dispatch) {
        super(eventBus, view);

        this.dispatch = dispatch;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {

        getView().getUpdateTigerYearButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doUpdateTigerLineVersion();
            }
        });

        getView().getUpdateUsgsDateButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doUpdateUsgsFeaturesDate();
            }
        });

        getView().getCloseButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doClose();
            }
        });
    }

    @Override
    protected void onReveal() {

        dispatch.execute(new GetTigerLineVersionAction(), new AsyncCallback<GetTigerLineVersionResult>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(GetTigerLineVersionResult result) {
                getView().getTigerLineYear().setValue(result.getYear());
            }
        });

        dispatch.execute(new GetUsgsFeaturesVersionAction(), new AsyncCallback<GetUsgsFeaturesVersionResult>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void onSuccess(GetUsgsFeaturesVersionResult result) {
                getView().getUsgsFeaturesDate().setValue(result.getVersion());
            }
        });
    }

    @Override
    public void doUpdateTigerLineVersion() {

        boolean confirm = Window.confirm("Updating the version of the TIGER/Line version will cause all current imported locations " +
                "to be deleted. Are you sure you wish to proceed?");

        if (confirm) {
            String tigerLineVersion = getView().getTigerLineYear().getValue();
            getView().getUpdateTigerYearButton().setEnabled(false);
            // TODO: Validate!

            dispatch.execute(new UpdateTigerLineVersionAction(tigerLineVersion), new AsyncCallback<UpdateTigerLineVersionResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();
                    getView().getUpdateTigerYearButton().setEnabled(true);

                }

                @Override
                public void onSuccess(UpdateTigerLineVersionResult result) {
                    Window.alert("Tiger Version updated!");
                    getView().getUpdateTigerYearButton().setEnabled(true);
                }
            });
        }
    }

    @Override
    public void doUpdateUsgsFeaturesDate() {

        boolean confirm = Window.confirm("Updating the version of the features will cause all currently imported" +
                " features to be deleted. Are you sure you wish to process?");

        if (confirm) {
            String usgsVersion = getView().getUsgsFeaturesDate().getValue();
            getView().getUpdateUsgsDateButton().setEnabled(false);

            dispatch.execute(new UpdateUsgsFeaturesVersionAction(usgsVersion), new AsyncCallback<UpdateUsgsFeaturesVersionResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();
                    getView().getUpdateUsgsDateButton().setEnabled(true);
                }

                @Override
                public void onSuccess(UpdateUsgsFeaturesVersionResult result) {
                    Window.alert("USGS Features date updated!");
                    getView().getUpdateUsgsDateButton().setEnabled(true);
                }
            });
        }
    }

    @Override
    public void doClose() {
        getView().getPopupPanel().hide();
    }
}
