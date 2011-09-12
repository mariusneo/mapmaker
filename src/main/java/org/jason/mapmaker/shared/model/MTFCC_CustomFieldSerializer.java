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
import java.util.List;

/**
 * GWT Custom Field Serializer for the MTFCC class. Handy for dealing with Hibernate or OpenJPA's usage of nonstandard
 * collection implementations
 *
 * @since 0.5.0
 * @author Jason Ferguson
 */
public class MTFCC_CustomFieldSerializer {

    public static void serialize(SerializationStreamWriter writer, MTFCC instance) throws SerializationException {

        writer.writeLong(instance.getId());
        writer.writeString(instance.getMtfccCode());
        writer.writeString(instance.getFeatureClass());
        writer.writeString(instance.getSuperClass());
        writer.writeBoolean(instance.isPoint());
        writer.writeBoolean(instance.isLinear());
        writer.writeBoolean(instance.isAreal());
        writer.writeString(instance.getFeatureClassDescription());
        writer.writeObject(new ArrayList<Location>());

//        if (instance.getLocationList() == null) {
//            writer.writeObject(new ArrayList<Location>());
//        } else {
//            writer.writeObject(new ArrayList<Location>(instance.getLocationList()));
//        }

    }

    public static void deserialize(SerializationStreamReader reader, MTFCC instance) throws SerializationException {

        instance.setId(reader.readLong());
        instance.setMtfccCode(reader.readString());
        instance.setFeatureClass(reader.readString());
        instance.setSuperClass(reader.readString());
        instance.setPoint(reader.readBoolean());
        instance.setLinear(reader.readBoolean());
        instance.setAreal(reader.readBoolean());
        instance.setFeatureClassDescription(reader.readString());
        instance.setLocationList((List) reader.readObject());
    }
}
