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

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

import java.util.ArrayList;

/**
 * GWT Custom field serializer for Location objects. This is yet another attempt to deal with Hibernate's silly
 * Persistent collection implementations; would it kill them to add a method to them to spit out an ArrayList (or
 * whatever)?
 *
 * @author Jason Ferguson
 * @since 0.4.3
 */
@SuppressWarnings("unused")
public class Location_CustomFieldSerializer {

    public static int countD = 0;

    public static void serialize(SerializationStreamWriter writer, Location instance) throws SerializationException {
        writer.writeLong(instance.getId());
        writer.writeString(instance.getGeoId());
        writer.writeString(instance.getStateFP());
        writer.writeString(instance.getCountyFP());
        writer.writeString(instance.getTractFP());
        writer.writeString(instance.getCd111FP());
        writer.writeString(instance.getConsolidatedCityFP());
        writer.writeString(instance.getCountySubdivisionFP());
        writer.writeString(instance.getElementaryDistrictFP());
        writer.writeString(instance.getSecondaryDistrictFP());
        writer.writeString(instance.getUnifiedDistrictFP());
        writer.writeString(instance.getSubMinorFP());
        writer.writeString(instance.getPlaceFP());
        writer.writeString(instance.getStateUpperDistrictFP());
        writer.writeString(instance.getStateLowerDistrictFP());
        writer.writeString(instance.getVotingDistrictFP());
        writer.writeString(instance.getClassFP());
        writer.writeString(instance.getName());
        writer.writeObject(instance.getMtfcc());
        writer.writeString(instance.getUrbanRural());
        writer.writeString(instance.getFunctionalStatus());
        writer.writeLong(instance.getLandArea());
        writer.writeLong(instance.getWaterArea());
        writer.writeDouble(instance.getInternalLat());
        writer.writeDouble(instance.getInternalLng());
        if (instance.getBorderPointList() == null) {
            writer.writeObject(new ArrayList<BorderPoint>());
        } else {
            writer.writeObject(new ArrayList<BorderPoint>(instance.getBorderPointList()));
        }
        writer.writeString(instance.getTigerVersion());
        if (instance.getFeatureList() == null) {
            writer.writeObject(new ArrayList<Feature>());
        } else {
            writer.writeObject(new ArrayList<Feature>(instance.getFeatureList()));
        }

    }

    public static void deserialize(SerializationStreamReader reader, Location instance) throws SerializationException {
        instance.setId(reader.readLong());
        instance.setGeoId(reader.readString());
        instance.setStateFP(reader.readString());
        instance.setCountyFP(reader.readString());
        instance.setTractFP(reader.readString());
        instance.setCd111FP(reader.readString());
        instance.setConsolidatedCityFP(reader.readString());
        instance.setCountySubdivisionFP(reader.readString());
        instance.setElementaryDistrictFP(reader.readString());
        instance.setSecondaryDistrictFP(reader.readString());
        instance.setUnifiedDistrictFP(reader.readString());
        instance.setSubMinorFP(reader.readString());
        instance.setPlaceFP(reader.readString());
        instance.setStateUpperDistrictFP(reader.readString());
        instance.setStateLowerDistrictFP(reader.readString());
        instance.setVotingDistrictFP(reader.readString());
        instance.setClassFP(reader.readString());
        instance.setName(reader.readString());
        instance.setMtfcc((MTFCC) reader.readObject());
        instance.setUrbanRural(reader.readString());
        instance.setFunctionalStatus(reader.readString());
        instance.setLandArea(reader.readLong());
        instance.setWaterArea(reader.readLong());
        instance.setInternalLat(reader.readDouble());
        instance.setInternalLng(reader.readDouble());
        instance.setBorderPointList((ArrayList<BorderPoint>) reader.readObject());
        instance.setTigerVersion(reader.readString());
        instance.setFeatureList((ArrayList<Feature>) reader.readObject());
    }

}
