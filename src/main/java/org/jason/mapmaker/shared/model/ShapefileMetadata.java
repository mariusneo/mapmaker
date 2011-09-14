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
package org.jason.mapmaker.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Model class that maintains information about the TIGER/Line Shapefiles themselves, not the data they contain.
 *
 * @since 0.4
 * @author Jason Ferguson
 */
@Entity
@Table(name = "SHAPEFILEMETADATA")
public class ShapefileMetadata implements Serializable, IsSerializable {

    private int id;
    private String geoId;
    private String mtfccCode;
    private String url;
    private String version;
    private String currentStatus;

    private List<Location> locationList;

    public ShapefileMetadata() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="GEOID")
    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    @Column(name = "MTFCCCODE")
    public String getMtfccCode() {
        return mtfccCode;
    }

    public void setMtfccCode(String mtfccCode) {
        this.mtfccCode = mtfccCode;
    }

    @Column(name="FILENAME")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name="VERSION")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name="STATUS")
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @OneToMany(mappedBy = "shapefileMetadata")
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShapefileMetadata that = (ShapefileMetadata) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "ShapefileMetadata{" +
                "id=" + id +
                ", geoId='" + geoId + '\'' +
                ", mtfccCode='" + mtfccCode + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                '}';
    }
}
