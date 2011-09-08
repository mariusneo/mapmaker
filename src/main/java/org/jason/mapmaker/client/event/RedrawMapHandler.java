package org.jason.mapmaker.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Jason Ferguson
 */
public interface RedrawMapHandler extends EventHandler {

    void onRedrawMap(RedrawMapEvent event);
}
