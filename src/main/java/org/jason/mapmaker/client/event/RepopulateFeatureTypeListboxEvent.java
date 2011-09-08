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
package org.jason.mapmaker.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * GWT Event for repopulating the FTLB
 *
 * @since 0.4
 * @author Jason Ferguson
 */
public class RepopulateFeatureTypeListboxEvent extends GwtEvent<RepopulateFeatureTypeListboxHandler> {

    public static Type<RepopulateFeatureTypeListboxHandler> TYPE = new Type<RepopulateFeatureTypeListboxHandler>();

    @Override
    public Type<RepopulateFeatureTypeListboxHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RepopulateFeatureTypeListboxHandler handler) {
        handler.doRepopulateFeatureTypeListbox(this);
    }
}
