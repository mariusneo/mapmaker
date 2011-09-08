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

/**
 * BorderPoint.java
 *
 * Represents a single point on one of the feature Borders
 *
 * 17 May 11: equals() method takes all three major fields now
 *
 * @author Jason Ferguson
 */
@Entity
@Table(name = "BORDERPOINT")
public class BorderPoint implements Serializable, IsSerializable {

    private Long id;
    private Location location;
    private Double lat;
    private Double lng;

    public BorderPoint() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BORDERID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="LAT")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Column(name="LNG")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name="LOCATIONID")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorderPoint)) return false;

        BorderPoint that = (BorderPoint) o;

        if (!lat.equals(that.lat)) return false;
        if (!lng.equals(that.lng)) return false;
        if (!location.equals(that.location)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + lat.hashCode();
        result = 31 * result + lng.hashCode();
        return result;
    }
}
