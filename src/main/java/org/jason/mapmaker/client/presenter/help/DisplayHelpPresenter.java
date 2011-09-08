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
package org.jason.mapmaker.client.presenter.help;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import org.jason.mapmaker.client.view.help.DisplayHelpUiHandlers;

/**
 * GWT-Platform Presenter for the DisplayHelp popup window.
 *
 * @since 0.1
 * @author Jason Ferguson
 */
public class DisplayHelpPresenter extends PresenterWidget<DisplayHelpPresenter.MyView>
                                        implements DisplayHelpUiHandlers {

    public interface MyView extends PopupView, HasUiHandlers<DisplayHelpUiHandlers> {

    }

    @Inject
    public DisplayHelpPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void doClose() {
        getView().hide();
    }
}
