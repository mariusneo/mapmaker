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
package org.jason.mapmaker.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a feature that can be displayed on a map (or exported)
 *
 * A feature has the following fields:
 * - name (i.e. "Circle H Ranch Airport"; found that one by accident and thought it was kind of cool)
 * - featureClass (i.e. "Airport")
 * - lat: latitude/y-coordinate
 * - lng: longituate/x-coodinate
 * - featureSource: "usgs" or "other" for now
 *
 * There are also two sets that separate feature class names into categories of geographic (natural) or manmade
 * features. Currently, only manmade features are imported.
 *
 * @author Jason Ferguson
 */
@Entity
@Table(name = "FEATURE")
public class Feature implements Serializable, IsSerializable {

    private int id;
    private String name;
    private String featureClass;
    private double lat;
    private double lng;
    private String featureSource;
    private FeaturesMetadata featuresMetadata;

    public Feature() {
    }

    public Feature(int id, String name, String featureClass, double lat, double lng, FeaturesMetadata fm) {
        this.id = id;
        this.name = name;
        this.featureClass = featureClass;
        this.lat = lat;
        this.lng = lng;
        this.featuresMetadata = fm;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEATUREID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "FEATURENAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FEATURECLASS")
    public String getFeatureClass() {
        return featureClass;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    @Column(name = "LAT")
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Column(name = "LNG")
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Column(name = "FEATURESOURCE")
    public String getFeatureSource() {
        return featureSource;
    }

    public void setFeatureSource(String featureSource) {
        this.featureSource = featureSource;
    }

    @ManyToOne
    @JoinColumn(name="FEATUREMETADATAID")
    public FeaturesMetadata getFeaturesMetadata() {
        return featuresMetadata;
    }

    public void setFeaturesMetadata(FeaturesMetadata featuresMetadata) {
        this.featuresMetadata = featuresMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature = (Feature) o;

        if (Double.compare(feature.lat, lat) != 0) return false;
        if (Double.compare(feature.lng, lng) != 0) return false;
        if (!featureClass.equals(feature.featureClass)) return false;
        if (!name.equals(feature.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = name.hashCode();
        int latHash = Double.toString(lat).hashCode();
        int lngHash = Double.toString(lng).hashCode();

        return result * latHash * lngHash;

    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", featureClass='" + featureClass + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }


}
