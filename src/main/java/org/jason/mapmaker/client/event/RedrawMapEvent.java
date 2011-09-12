package org.jason.mapmaker.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Jason Ferguson
 */
public class RedrawMapEvent extends GwtEvent<RedrawMapHandler> {

    public static Type<RedrawMapHandler> TYPE = new Type<RedrawMapHandler>();

    private String geoId;
    private String mtfccCode;
    private String featureClassType;

    public static Type<RedrawMapHandler> getTYPE() {
        return TYPE;
    }

    public static void setTYPE(Type<RedrawMapHandler> TYPE) {
        RedrawMapEvent.TYPE = TYPE;
    }

    public RedrawMapEvent(String geoId, String mtfccCode) {
        this.geoId = geoId;
        this.mtfccCode = mtfccCode;
    }

    public RedrawMapEvent(String geoId, String mtfccCode, String featureClassType) {
        this.geoId = geoId;
        this.mtfccCode = mtfccCode;
        this.featureClassType = featureClassType;
    }

    public String getGeoId() {
        return geoId;
    }

    public String getFeatureClassType() {
        return featureClassType;
    }

    public String getMtfccCode() {
        return mtfccCode;
    }

    @Override
    public Type<RedrawMapHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RedrawMapHandler handler) {
        handler.onRedrawMap(this);
    }
}
