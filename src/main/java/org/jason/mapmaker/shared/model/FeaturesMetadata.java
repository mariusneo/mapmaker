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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Model class maintaining information about the USGS features
 *
 * @author Jason Ferguson
 */
@Entity
@Table(name = "FEATURESMETADATA")
@SuppressWarnings("unused")
public class FeaturesMetadata implements Serializable, IsSerializable {

    private int id;
    private String state;
    private String stateAbbr;
    private String usgsDate;
    private String filename;
    private String stateGeoId;
    private String currentStatus;
    private List<Feature> featureList;

    public FeaturesMetadata() {
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

    @Column(name="STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name="STATEABBR")
    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    @Column(name="USGSDATE")
    public String getUsgsDate() {
        return usgsDate;
    }

    public void setUsgsDate(String usgsDate) {
        this.usgsDate = usgsDate;
    }

    @Column(name="FILENAME")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Column(name="STATEGEOID")
    public String getStateGeoId() {
        return stateGeoId;
    }

    public void setStateGeoId(String stateGeoId) {
        this.stateGeoId = stateGeoId;
    }

    @Column(name="STATUS")
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @OneToMany(mappedBy = "featuresMetadata")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    public List<Feature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Feature> featureList) {
        this.featureList = featureList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeaturesMetadata that = (FeaturesMetadata) o;

        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return state != null ? state.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FeaturesMetadata{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", stateAbbr='" + stateAbbr + '\'' +
                ", usgsDate='" + usgsDate + '\'' +
                ", filename='" + filename + '\'' +
                ", stateGeoId='" + stateGeoId + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                '}';
    }
}
