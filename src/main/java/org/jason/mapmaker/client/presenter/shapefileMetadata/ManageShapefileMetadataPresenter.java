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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import org.jason.mapmaker.client.event.RepopulateBorderTypeListboxEvent;
import org.jason.mapmaker.client.event.RepopuplateShapefileMetadataFlextableEvent;
import org.jason.mapmaker.client.event.RepopuplateShapefileMetadataFlextableHandler;
import org.jason.mapmaker.client.view.shapefileMetadata.ManageShapefileMetadataUiHandlers;
import org.jason.mapmaker.shared.action.shapefile.DeleteSingleShapefileAction;
import org.jason.mapmaker.shared.action.shapefile.ImportSingleShapefileAction;
import org.jason.mapmaker.shared.action.shapefileMetadata.GetShapefileMetadataForGeoIdAction;
import org.jason.mapmaker.shared.model.ShapefileMetadata;
import org.jason.mapmaker.shared.result.shapefile.DeleteSingleShapefileResult;
import org.jason.mapmaker.shared.result.shapefile.ImportSingleShapefileResult;
import org.jason.mapmaker.shared.result.shapefileMetadata.GetShapefileMetadataForGeoIdResult;
import org.jason.mapmaker.shared.util.GeographyUtils;

import java.util.List;


/**
 * GWTP PresenterWidget for the ManageShapefileMetadata popup.
 *
 * Notes:
 * - Deactivating the import buttons would require special Actions to determine if the state and/or county associated
 *   with it had been imported. Then, the flextable would have to be processed to deactivate those buttons based on
 *   the results of those actions
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class ManageShapefileMetadataPresenter extends PresenterWidget<ManageShapefileMetadataPresenter.MyView>
        implements ManageShapefileMetadataUiHandlers {

    public interface MyView extends PopupView, HasUiHandlers<ManageShapefileMetadataUiHandlers> {

        /**
         * Return the button used to request the importable borders for a given state
         *
         * @return      Button
         */
        Button getDetailsButton();

        /**
         * Get the listbox used to contain the names of the states. Values are state geoId, label is the state name
         * @return
         */
        ListBox getStateListBox();

        /**
         * Get FlexTable used to contain the ShapefileMetadata object
         *
         * @return  FlexTable
         */
        FlexTable getBordersFlexTable();

        /**
         * Return button used to close the popup window
         *
         * @return  Button
         */
        Button getCloseButton();

        /**
         * Create the button used to import given border type
         *
         * @param sm    ShapefileMetadata
         * @param rowIndex  index of the row number of the flextable that the Button will be put in
         * @return      Button
         */
        Button createImportButton(ShapefileMetadata sm, int rowIndex);

        /**
         * Create the button used to delete a given border type
         *
         * @param sm        ShapefileMetadata
         * @param rowIndex  index of the row number of the FlexTable that the Button will be put in
         * @return      Button
         */
        Button createDeleteButton(ShapefileMetadata sm, int rowIndex);
    }

    private DispatchAsync dispatchAsync;

    @Inject
    public ManageShapefileMetadataPresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync) {
        super(eventBus, view);

        this.dispatchAsync = dispatchAsync;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {

        // populate the state listbox from a state/geoid map
        for (String geoId : GeographyUtils.stateGeoIdMap.keySet()) {
            getView().getStateListBox().addItem(GeographyUtils.getStateForGeoId(geoId), geoId);
        }

        // bind the details button
        getView().getDetailsButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doShowDetails();
            }
        });


        // bind the close button
        getView().getCloseButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getView().hide();
            }
        });

        // bind the doShowDetails() method to the RepopulateShapefileMetadataFlextableEvent
        getEventBus().addHandler(RepopuplateShapefileMetadataFlextableEvent.TYPE, new RepopuplateShapefileMetadataFlextableHandler() {
            @Override
            public void doRepopulateFlextable(RepopuplateShapefileMetadataFlextableEvent event) {
                getView().getBordersFlexTable().clear();
                doShowDetails();
            }
        });
    }

    @Override
    public void doShowDetails() {
        // get zee geoId!
        int index = getView().getStateListBox().getSelectedIndex();
        String geoId = getView().getStateListBox().getValue(index);

        dispatchAsync.execute(new GetShapefileMetadataForGeoIdAction(geoId, false), new AsyncCallback<GetShapefileMetadataForGeoIdResult>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(GetShapefileMetadataForGeoIdResult result) {
                createTable(result.getResultList());
            }
        });

    }

    @Override
    public void doClose() {
        getView().hide();
    }

    private void createTable(List<ShapefileMetadata> shapefileMetadataList) {

        // create the flextable header
        getView().getBordersFlexTable().setText(0, 0, "Name");
        getView().getBordersFlexTable().setText(0, 1, "MTFCC");
        getView().getBordersFlexTable().setText(0, 2, "Filename");
        getView().getBordersFlexTable().setText(0, 3, "Status");
        getView().getBordersFlexTable().setText(0, 4, "Action");

        // loop through the list of shapefilemetadata objects and create rows. Need the counter for the FlexTable rows
        for (int i = 0; i < shapefileMetadataList.size(); i++) {

            ShapefileMetadata sm = shapefileMetadataList.get(i);
            getView().getBordersFlexTable().setText(i + 1, 0, GeographyUtils.getNameForMtfcc(sm.getMtfccCode()));
            getView().getBordersFlexTable().setText(i + 1, 1, sm.getMtfccCode());
            getView().getBordersFlexTable().setText(i + 1, 2, sm.getUrl());
            getView().getBordersFlexTable().setText(i + 1, 3, sm.getCurrentStatus());

            if (sm.getCurrentStatus().equals(GeographyUtils.Status.NOT_IMPORTED)) {
                getView().getBordersFlexTable().setWidget(i + 1, 4, getView().createImportButton(sm, i + 1));
            } else if (sm.getCurrentStatus().equals(GeographyUtils.Status.IMPORTED)) {
                getView().getBordersFlexTable().setWidget(i + 1, 4, getView().createDeleteButton(sm, i + 1));
            } else {
                getView().getBordersFlexTable().setText(i + 1, 4, "N/A");
            }
        }

    }

    @Override
    public void doImport(final ShapefileMetadata sm, final int rowIndex) {

        // change the text to importing
        getView().getBordersFlexTable().setText(rowIndex, 3, GeographyUtils.Status.IMPORTING);

        // disable the import button
        ((Button) getView().getBordersFlexTable().getWidget(rowIndex, 4)).setEnabled(false);

        // disable the close button
        getView().getCloseButton().setEnabled(false);

        // fire the dispatcher off
        dispatchAsync.execute(new ImportSingleShapefileAction(sm), new AsyncCallback<ImportSingleShapefileResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                getView().getBordersFlexTable().setText(rowIndex, 3, GeographyUtils.Status.NOT_IMPORTED);
                ((Button) getView().getBordersFlexTable().getWidget(rowIndex, 4)).setEnabled(true);
                getView().getCloseButton().setEnabled(true);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(ImportSingleShapefileResult importSingleShapefileResult) {
                getView().getBordersFlexTable().setText(rowIndex, 3, GeographyUtils.Status.IMPORTED);
                getView().getBordersFlexTable().setWidget(rowIndex, 4, getView().createDeleteButton(sm, rowIndex));
                ((Button) getView().getBordersFlexTable().getWidget(rowIndex, 4)).setEnabled(true);

                getView().getCloseButton().setEnabled(true);

                getEventBus().fireEvent(new RepopulateBorderTypeListboxEvent());
            }
        });

    }

    @Override
    public void doDelete(final ShapefileMetadata sm, final int rowIndex) {

        getView().getBordersFlexTable().setText(rowIndex, 3, GeographyUtils.Status.DELETING);

        ((Button) getView().getBordersFlexTable().getWidget(rowIndex, 4)).setEnabled(false);

        dispatchAsync.execute(new DeleteSingleShapefileAction(sm), new AsyncCallback<DeleteSingleShapefileResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                getView().getBordersFlexTable().setText(rowIndex, 3, GeographyUtils.Status.IMPORTED);
                ((Button) getView().getBordersFlexTable().getWidget(rowIndex, 4)).setEnabled(true);

                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(DeleteSingleShapefileResult deleteSingleShapefileResult) {
                getView().getBordersFlexTable().setText(rowIndex, 3, GeographyUtils.Status.NOT_IMPORTED);
                getView().getBordersFlexTable().setWidget(rowIndex, 4, getView().createImportButton(sm, rowIndex));
                ((Button) getView().getBordersFlexTable().getWidget(rowIndex, 4)).setEnabled(true);

                getEventBus().fireEvent(new RepopulateBorderTypeListboxEvent());
            }
        });
    }
}
