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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import org.jason.mapmaker.client.event.RepopulateFeatureTypeListboxEvent;
import org.jason.mapmaker.client.event.RepopulateFeaturesMetadataFlextableEvent;
import org.jason.mapmaker.client.event.RepopulateFeaturesMetadataFlextableHandler;
import org.jason.mapmaker.client.view.featuresMetadata.ManageFeaturesMetadataUiHandler;
import org.jason.mapmaker.shared.action.feature.DeleteSingleFeatureAction;
import org.jason.mapmaker.shared.action.feature.ImportSingleFeatureAction;
import org.jason.mapmaker.shared.action.featuresMetadata.GetFeaturesMetadataListAction;
import org.jason.mapmaker.shared.model.FeaturesMetadata;
import org.jason.mapmaker.shared.result.feature.DeleteSingleFeatureResult;
import org.jason.mapmaker.shared.result.feature.ImportSingleFeatureResult;
import org.jason.mapmaker.shared.result.featuresMetadata.GetFeaturesMetadataListResult;
import org.jason.mapmaker.shared.util.GeographyUtils;

import java.util.List;

/**
 * GWTP PresenterWidget class for managing interaction between the ManageFeaturesMetadataPopup and the backend.
 *
 * @since 0.3
 * @author Jason Ferguson
 */
public class ManageFeaturesMetadataPresenter extends PresenterWidget<ManageFeaturesMetadataPresenter.MyView>
        implements ManageFeaturesMetadataUiHandler {

    public interface MyView extends PopupView, HasUiHandlers<ManageFeaturesMetadataUiHandler> {

        /**
         * Return a reference to the features flex table
         *
         * @return   FlexTable used to contain the FeaturesMetadata information
         */
        FlexTable getFeaturesFlexTable();

        /**
         * Return the button used to close the popup window
         *
         * @return  Button used to close the popup
         */
        Button getCloseButton();

        /**
         * Create the button used to import a State's features
         *
         * @param fm        FeaturesMetadata object used to import the related Features
         * @param rowIndex  index of the row of the FlexTable that the information to be imported is displayed in
         *
         * @return      Button used to import Features belonging to a state
         */
        Button createImportButton(final FeaturesMetadata fm, final int rowIndex);

        /**
         * Create the button used to delete a State's features
         *
         * @param fm        FeaturesMetadata object containing information to delete
         * @param rowIndex  index of the row of the FlexTable that the information to be deleted is displayed in
         * @return      Button used to delete Features belonging to a state
         */
        Button createDeleteButton(final FeaturesMetadata fm, final int rowIndex);
    }

    private DispatchAsync dispatchAsync;

    @Inject
    public ManageFeaturesMetadataPresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync) {
        super(eventBus, view);
        this.dispatchAsync = dispatchAsync;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {

        // bind the close button
        getView().getCloseButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doClose();
            }
        });

        // bind the event handler for the ReloadEvent
        getEventBus().addHandler(RepopulateFeaturesMetadataFlextableEvent.TYPE, new RepopulateFeaturesMetadataFlextableHandler() {
            @Override
            public void doRepopulateFlextable(RepopulateFeaturesMetadataFlextableEvent event) {
                getView().getFeaturesFlexTable().clear();
                doShowDetails();
            }
        });

    }

    @Override
    protected void onReveal() {

        // by putting this call in onReveal() instead of onBind(), the next time I update the usgs features date,
        // it will be reflected
        doShowDetails();
    }

    @Override
    public void doClose() {
        getView().hide();
    }

    @Override
    public void doShowDetails() {

        dispatchAsync.execute(new GetFeaturesMetadataListAction(), new AsyncCallback<GetFeaturesMetadataListResult>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(GetFeaturesMetadataListResult result) {
                createTable(result.getResultList());
            }
        });

    }

    public void doImport(final FeaturesMetadata fm, final int rowIndex) {

        // change the value of the status field to "importing", and disable the button to prevent hammering
        getView().getFeaturesFlexTable().setText(rowIndex, 3, GeographyUtils.Status.IMPORTING);
        ((Button) getView().getFeaturesFlexTable().getWidget(rowIndex, 4)).setEnabled(false);
        getView().getCloseButton().setEnabled(false);
        // do the import
        dispatchAsync.execute(new ImportSingleFeatureAction(fm), new AsyncCallback<ImportSingleFeatureResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                getView().getFeaturesFlexTable().setText(rowIndex, 3, GeographyUtils.Status.NOT_IMPORTED);
                ((Button) getView().getFeaturesFlexTable().getWidget(rowIndex, 4)).setEnabled(true);
                getView().getCloseButton().setEnabled(true);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(ImportSingleFeatureResult result) {
                // swap the button
                getView().getFeaturesFlexTable().setWidget(rowIndex, 4, getView().createDeleteButton(fm, rowIndex));
                getView().getFeaturesFlexTable().setText(rowIndex, 3, GeographyUtils.Status.IMPORTED);
                ((Button) getView().getFeaturesFlexTable().getWidget(rowIndex, 4)).setEnabled(true);

                getView().getCloseButton().setEnabled(true);
                getEventBus().fireEvent(new RepopulateFeatureTypeListboxEvent());
            }
        });
    }

    @Override
    public void doDelete(final FeaturesMetadata fm, final int rowIndex) {

        getView().getFeaturesFlexTable().setText(rowIndex, 3, GeographyUtils.Status.DELETING);
        ((Button) getView().getFeaturesFlexTable().getWidget(rowIndex, 4)).setEnabled(false);

        dispatchAsync.execute(new DeleteSingleFeatureAction(fm), new AsyncCallback<DeleteSingleFeatureResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                ((Button) getView().getFeaturesFlexTable().getWidget(rowIndex, 4)).setEnabled(true);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(DeleteSingleFeatureResult deleteSingleFeatureResult) {

                // swap the button
                getView().getFeaturesFlexTable().setWidget(rowIndex, 4, getView().createImportButton(fm, rowIndex));
                getView().getFeaturesFlexTable().setText(rowIndex, 3, GeographyUtils.Status.NOT_IMPORTED);

                getEventBus().fireEvent(new RepopulateFeatureTypeListboxEvent());
            }
        });
    }


    private void createTable(List<FeaturesMetadata> featuresMetadataList) {

        getView().getFeaturesFlexTable().setText(0, 0, "State");
        getView().getFeaturesFlexTable().setText(0, 1, "Filename");
        getView().getFeaturesFlexTable().setText(0, 2, "Date");
        getView().getFeaturesFlexTable().setText(0, 3, "Status");
        getView().getFeaturesFlexTable().setText(0, 4, "Action");

        for (int i = 0; i < featuresMetadataList.size(); i++) {
            FeaturesMetadata fm = featuresMetadataList.get(i);
            getView().getFeaturesFlexTable().setText(i + 1, 0, fm.getState());
            getView().getFeaturesFlexTable().setText(i + 1, 1, fm.getFilename());
            getView().getFeaturesFlexTable().setText(i + 1, 2, fm.getUsgsDate());
            getView().getFeaturesFlexTable().setText(i+1, 3, fm.getCurrentStatus());

            if (fm.getCurrentStatus().equals(GeographyUtils.Status.IMPORTED)) {
                getView().getFeaturesFlexTable().setWidget(i + 1, 4, getView().createDeleteButton(fm, i+1));
            } else if (fm.getCurrentStatus().equals(GeographyUtils.Status.NOT_IMPORTED)) {
                getView().getFeaturesFlexTable().setWidget(i + 1, 4, getView().createImportButton(fm, i+1));
            } else {
                getView().getFeaturesFlexTable().setText(i + 1, 4, "Not Available");
            }

        }

    }
}
