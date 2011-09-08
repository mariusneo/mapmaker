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

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * UiHandler methods needed for the Stack Panel elements
 *
 * @author Jason Ferguson
 */
public interface StackPanelUiHandlers extends UiHandlers{

    /**
     * Fires a RootRevealPopupEvent to display the help popup screen
     */
    void onDisplayHelp();

    /**
     * Fires a RootRevealPopupEvent to display the Borders Management popup
     */
    void onDisplayManageBorders();

    /**
     * Fires a RootRevealPopupEvent to display the Features Management popup
     */
    void onDisplayManageFeatures();

    void onDisplayManageAvailableData();

    /**
     * Fires a "real" event from the StackPanelPresenter through the event bus to the Map Panel Presenter
     * to redraw the map.
     */
    void onRedrawMap();

    void doClearBorderTypeListBox();

    void doClearPrimaryListBox();

    void doClearSecondaryListBox();

    void doClearTertiaryListBox();
}
