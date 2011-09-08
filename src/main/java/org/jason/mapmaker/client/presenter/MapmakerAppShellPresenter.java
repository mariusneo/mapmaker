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
package org.jason.mapmaker.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;
import org.jason.mapmaker.client.presenter.featuresMetadata.ImportFeaturesMetadataPresenter;
import org.jason.mapmaker.client.presenter.shapefileMetadata.ImportShapefileMetadataPresenter;
import org.jason.mapmaker.shared.action.featuresMetadata.CountFeaturesMetadataAction;
import org.jason.mapmaker.shared.action.shapefileMetadata.CountShapefileMetadataAction;
import org.jason.mapmaker.shared.result.featuresMetadata.CountFeaturesMetadataResult;
import org.jason.mapmaker.shared.result.shapefileMetadata.CountShapefileMetadataResult;


/**
 * MapmakerAppShellPresenter.java
 * <p/>
 * Main Presenter class for the entire application. This should be the only class that has a proxy; all other
 * presenters, which necessary, should extend PresenterWidget<View> instead.
 *
 * @author Jason Ferguson
 */
public class MapmakerAppShellPresenter extends Presenter<MapmakerAppShellPresenter.MyView, MapmakerAppShellPresenter.MyProxy> {

    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken("main")
    public interface MyProxy extends ProxyPlace<MapmakerAppShellPresenter> {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> StackPanelPresenterSlot = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> MapPanelPresenterSlot = new Type<RevealContentHandler<?>>();

    private MapmakerStackPanelPresenter mapmakerStackPanelPresenter;
    private MapmakerMapViewPresenter mapmakerMapViewPresenter;
    private ImportShapefileMetadataPresenter importShapefileMetadataPresenter;
    private ImportFeaturesMetadataPresenter importFeaturesMetadataPresenter;

    private DispatchAsync dispatchAsync;


    @Inject
    public MapmakerAppShellPresenter(final EventBus eventBus,
                                     final MyView view,
                                     final MyProxy proxy,
                                     MapmakerStackPanelPresenter mapmakerStackPanelPresenter,
                                     MapmakerMapViewPresenter mapmakerMapViewPresenter,
                                     ImportShapefileMetadataPresenter importShapefileMetadataPresenter,
                                     ImportFeaturesMetadataPresenter importFeaturesMetadataPresenter,
                                     DispatchAsync dispatchAsync) {

        super(eventBus, view, proxy);

        this.mapmakerStackPanelPresenter = mapmakerStackPanelPresenter;
        this.mapmakerMapViewPresenter = mapmakerMapViewPresenter;
        this.importShapefileMetadataPresenter = importShapefileMetadataPresenter;
        this.importFeaturesMetadataPresenter = importFeaturesMetadataPresenter;
        this.dispatchAsync = dispatchAsync;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        // in order for the leaf presenters to function, they have to be added to the slots defined in this class. In
        // this case, we need to add the stack panel presenter and map view presenter, which I injected via gin in the
        // constructor
        setInSlot(StackPanelPresenterSlot, mapmakerStackPanelPresenter);
        setInSlot(MapPanelPresenterSlot, mapmakerMapViewPresenter);

        // we need to check the metadata before the user can use the actual interface
        // check the ShapefileMetadata
        dispatchAsync.execute(new CountShapefileMetadataAction(), new AsyncCallback<CountShapefileMetadataResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(CountShapefileMetadataResult result) {
                if (result.getResult() == 0) {
                    RevealRootPopupContentEvent.fire(MapmakerAppShellPresenter.this, importShapefileMetadataPresenter);
                }
            }
        });

        dispatchAsync.execute(new CountFeaturesMetadataAction(), new AsyncCallback<CountFeaturesMetadataResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(CountFeaturesMetadataResult result) {
                if (result.getResult() == 0) {
                    RevealRootPopupContentEvent.fire(MapmakerAppShellPresenter.this, importFeaturesMetadataPresenter);
                }
            }
        });


    }

}
