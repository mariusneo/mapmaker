package org.jason.mapmaker.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Jason Ferguson
 */
public class EnableRedrawMapButtonEvent extends GwtEvent<EnableRedrawMapButtonHandler> {

    public static Type<EnableRedrawMapButtonHandler> TYPE = new Type<EnableRedrawMapButtonHandler>();

    @Override
    public Type<EnableRedrawMapButtonHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EnableRedrawMapButtonHandler handler) {
        handler.onEnableRedrawMapButton(this);
    }
}
