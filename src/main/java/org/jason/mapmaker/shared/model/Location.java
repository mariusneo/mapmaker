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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "Cadillac" class to represent data in TIGER/Line files. This ultimately turned out to be the easiest way to
 * store the data I wanted.
 *
 * @since 0.1
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
@Entity
@Table(name="LOCATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable, IsSerializable, Cloneable {

    private Long id;

    private String geoId;

    private String stateFP;
    private String countyFP;
    private String tractFP;
    private String cd111FP;
    private String consolidatedCityFP;
    private String countySubdivisionFP;
    private String elementaryDistrictFP;
    private String secondaryDistrictFP;
    private String unifiedDistrictFP;
    private String subMinorFP;
    private String placeFP;
    private String stateUpperDistrictFP;
    private String stateLowerDistrictFP;
    private String votingDistrictFP;
    private String classFP;

    private String name;
    private String lsad;
    private MTFCC mtfcc;
    private String urbanRural;
    private String functionalStatus;
    private Long landArea;
    private Long waterArea;

    private Double internalLat;
    private Double internalLng;

    private ShapefileMetadata shapefileMetadata;
    private List<BorderPoint> borderPointList;

    private String tigerVersion;

    private List<Feature> featureList;  // transient... do not map!

    // Location's bounding box
    private Double maxLat;
    private Double maxLng;
    private Double minLat;
    private Double minLng;

    public Location() {
    }

    public Location(String geoId, String name, MTFCC mtfcc) {
        this.geoId = geoId;
        this.name = name;
        this.mtfcc = mtfcc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LOCATIONID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    @Column(name="STATEFP")
    public String getStateFP() {
        return stateFP;
    }

    public void setStateFP(String stateFP) {
        this.stateFP = stateFP;
    }

    @Column(name="COUNTYFP")
    public String getCountyFP() {
        return countyFP;
    }

    public void setCountyFP(String countyFP) {
        this.countyFP = countyFP;
    }

    @Column(name="TRACTFP")
    public String getTractFP() {
        return tractFP;
    }

    public void setTractFP(String tractFP) {
        this.tractFP = tractFP;
    }

    @Column(name="CD111FP")
    public String getCd111FP() {
        return cd111FP;
    }

    public void setCd111FP(String cd111FP) {
        this.cd111FP = cd111FP;
    }

    @Column(name="consolidatedCityFP")
    public String getConsolidatedCityFP() {
        return consolidatedCityFP;
    }

    public void setConsolidatedCityFP(String consolidatedCityFP) {
        this.consolidatedCityFP = consolidatedCityFP;
    }

    @Column(name="countySubdivisionFP")
    public String getCountySubdivisionFP() {
        return countySubdivisionFP;
    }

    public void setCountySubdivisionFP(String countySubdivisionFP) {
        this.countySubdivisionFP = countySubdivisionFP;
    }

    @Column(name="elementaryDistrictFP")
    public String getElementaryDistrictFP() {
        return elementaryDistrictFP;
    }

    public void setElementaryDistrictFP(String elementaryDistrictFP) {
        this.elementaryDistrictFP = elementaryDistrictFP;
    }

    @Column(name="secondaryDistrictFP")
    public String getSecondaryDistrictFP() {
        return secondaryDistrictFP;
    }

    public void setSecondaryDistrictFP(String secondaryDistrictFP) {
        this.secondaryDistrictFP = secondaryDistrictFP;
    }

    @Column(name="unifiedDistrictFP")
    public String getUnifiedDistrictFP() {
        return unifiedDistrictFP;
    }

    public void setUnifiedDistrictFP(String unifiedDistrictFP) {
        this.unifiedDistrictFP = unifiedDistrictFP;
    }

    @Column(name="subMinorFP")
    public String getSubMinorFP() {
        return subMinorFP;
    }

    public void setSubMinorFP(String subMinorFP) {
        this.subMinorFP = subMinorFP;
    }

    @Column(name="placeFP")
    public String getPlaceFP() {
        return placeFP;
    }

    public void setPlaceFP(String placeFP) {
        this.placeFP = placeFP;
    }

    @Column(name="stateUpperDistrictFP")
    public String getStateUpperDistrictFP() {
        return stateUpperDistrictFP;
    }

    public void setStateUpperDistrictFP(String stateUpperDistrictFP) {
        this.stateUpperDistrictFP = stateUpperDistrictFP;
    }

    @Column(name="stateLowerDistrictFP")
    public String getStateLowerDistrictFP() {
        return stateLowerDistrictFP;
    }

    public void setStateLowerDistrictFP(String stateLowerDistrictFP) {
        this.stateLowerDistrictFP = stateLowerDistrictFP;
    }

    @Column(name="votingDistrictFP")
    public String getVotingDistrictFP() {
        return votingDistrictFP;
    }

    public void setVotingDistrictFP(String votingDistrictFP) {
        this.votingDistrictFP = votingDistrictFP;
    }

    @Column(name="classFP")
    public String getClassFP() {
        return classFP;
    }

    public void setClassFP(String classFP) {
        this.classFP = classFP;
    }

    @Column(name="locationName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="LSAD")
    public String getLsad() {
        return lsad;
    }

    public void setLsad(String lsad) {
        this.lsad = lsad;
    }

    @ManyToOne
    @JoinColumn(name="MTFCCID")
    public MTFCC getMtfcc() {
        return mtfcc;
    }

    public void setMtfcc(MTFCC mtfcc) {
        this.mtfcc = mtfcc;
    }

    @Column(name="URBANRURAL")
    public String getUrbanRural() {
        return urbanRural;
    }

    public void setUrbanRural(String urbanRural) {
        this.urbanRural = urbanRural;
    }

    @Column(name="FUNCTIONALSTATUS")
    public String getFunctionalStatus() {
        return functionalStatus;
    }

    public void setFunctionalStatus(String functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    @Column(name="LANDAREA")
    public Long getLandArea() {
        return landArea;
    }

    public void setLandArea(Long landArea) {
        this.landArea = landArea;
    }

    @Column(name="WATERAREA")
    public Long getWaterArea() {
        return waterArea;
    }

    public void setWaterArea(Long waterArea) {
        this.waterArea = waterArea;
    }

    @Column(name="INTERNALLAT")
    public Double getInternalLat() {
        return internalLat;
    }

    public void setInternalLat(Double internalLat) {
        this.internalLat = internalLat;
    }

    @Column(name="INTERNALLNG")
    public Double getInternalLng() {
        return internalLng;
    }

    public void setInternalLng(Double internalLng) {
        this.internalLng = internalLng;
    }

    @ManyToOne
    @JoinColumn(name="SHAPEFILEMETADATAID")
    public ShapefileMetadata getShapefileMetadata() {
        return shapefileMetadata;
    }

    public void setShapefileMetadata(ShapefileMetadata shapefileMetadata) {
        this.shapefileMetadata = shapefileMetadata;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<BorderPoint> getBorderPointList() {
        return borderPointList;
    }

    public void setBorderPointList(List<BorderPoint> borderPointList) {
        this.borderPointList = borderPointList;
    }

    @Column(name="TIGERVERSION")
    public String getTigerVersion() {
        return tigerVersion;
    }

    public void setTigerVersion(String tigerVersion) {
        this.tigerVersion = tigerVersion;
    }

    @Transient
    public List<Feature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Feature> featureList) {
        this.featureList = featureList;
    }

    @Column(name="MAXLAT")
    public Double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

    @Column(name="MAXLNG")
    public Double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }

    @Column(name="MINLAT")
    public Double getMinLat() {
        return minLat;
    }

    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    @Column(name="MINLNG")
    public Double getMinLng() {
        return minLng;
    }

    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }

    @Transient
    public Map<String, Double> getBoundingBox() {
        Map<String, Double> box = new HashMap<String, Double>();
        box.put("MINLAT", getMinLat());
        box.put("MAXLAT", getMaxLat());
        box.put("MINLNG", getMinLng());
        box.put("MAXLNG", getMaxLng());

        return box;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (geoId != null ? !geoId.equals(location.geoId) : location.geoId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return geoId != null ? geoId.hashCode() : 0;
    }

}
