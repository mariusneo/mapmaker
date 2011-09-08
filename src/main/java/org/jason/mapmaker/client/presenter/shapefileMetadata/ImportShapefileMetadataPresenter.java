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
package org.jason.mapmaker.client.presenter.shapefileMetadata;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import org.jason.mapmaker.client.view.shapefileMetadata.ImportShapefileMetadataUiHandlers;
import org.jason.mapmaker.shared.action.shapefileMetadata.ImportShapefileMetadataAction;
import org.jason.mapmaker.shared.result.shapefileMetadata.ImportShapefileMetadataResult;

/**
 * GWTP PresenterWidget for managing the ImportShapefileMetadataPopup
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ImportShapefileMetadataPresenter extends PresenterWidget<ImportShapefileMetadataPresenter.MyView>
        implements ImportShapefileMetadataUiHandlers {

    public interface MyView extends PopupView, HasUiHandlers<ImportShapefileMetadataUiHandlers> {

        /**
         * Get the Button that triggers the import of the ShapefileMetadata
         *
         * @return  Button
         */
        Button getImportButton();

    }

    private DispatchAsync dispatch;

    @Inject
    public ImportShapefileMetadataPresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync) {
        super(eventBus, view);
        this.dispatch = dispatchAsync;

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

        getView().getImportButton().setEnabled(false);
        dispatch.execute(new ImportShapefileMetadataAction(), new AsyncCallback<ImportShapefileMetadataResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                getView().getImportButton().setEnabled(true);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(ImportShapefileMetadataResult result) {
                Window.alert("Shapefile information import complete! Click OK to close the window.");
                getView().getImportButton().setEnabled(true);
                getView().hide();
            }
        });
    }
}
