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
package org.jason.mapmaker.client.presenter.featuresMetadata;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import org.jason.mapmaker.client.event.RepopulateFeaturesMetadataFlextableEvent;
import org.jason.mapmaker.client.view.featuresMetadata.ImportFeaturesMetadataUiHandler;
import org.jason.mapmaker.shared.action.featuresMetadata.ImportFeaturesMetadataAction;
import org.jason.mapmaker.shared.result.featuresMetadata.ImportFeaturesMetadataResult;


/**
 * GWTP PresenterWidget for managing the ImportFeaturesPopup
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportFeaturesMetadataPresenter extends PresenterWidget<ImportFeaturesMetadataPresenter.MyView>
        implements ImportFeaturesMetadataUiHandler {

    public interface MyView extends PopupView, HasUiHandlers<ImportFeaturesMetadataUiHandler> {

        /**
         * Get the DateBox that contains the date of the most recent Features update to the USGS geonames features
         *
         * @return  DateBox
         */
        DateBox getDateBox();

        /**
         * Get the Button used to trigger the Import process
         *
         * @return  Button
         */
        Button getImportButton();
    }

    private DispatchAsync dispatch;

    @Inject
    public ImportFeaturesMetadataPresenter(EventBus eventBus, MyView view, DispatchAsync dispatch) {
        super(eventBus, view);

        this.dispatch = dispatch;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {

        getView().getImportButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doImport();
            }
        });
    }

    @Override
    public void doImport() {

        DateTimeFormat fmt = DateTimeFormat.getFormat("yyyyMMdd");
        String date = fmt.format(getView().getDateBox().getValue());

        getView().getImportButton().setEnabled(false);
        dispatch.execute(new ImportFeaturesMetadataAction(date), new AsyncCallback<ImportFeaturesMetadataResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                getView().getImportButton().setEnabled(true);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(ImportFeaturesMetadataResult result) {
                getView().getImportButton().setEnabled(true);

                getEventBus().fireEvent(new RepopulateFeaturesMetadataFlextableEvent());
                Window.alert("Features metadata generation complete!");
                getView().hide();
            }
        });
    }
}
