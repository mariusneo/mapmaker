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

/**
 * GWT CustomFieldSerializer for FeaturesMetadata objects
 *
 * @author Jason Ferguson
 * @since 0.4.3
 */
@SuppressWarnings("unused")
public class FeaturesMetadata_CustomFieldSerializer {

    public static void serialize(SerializationStreamWriter writer, FeaturesMetadata instance) throws SerializationException {

        writer.writeInt(instance.getId());
        writer.writeString(instance.getState());
        writer.writeString(instance.getStateAbbr());
        writer.writeString(instance.getUsgsDate());
        writer.writeString(instance.getFilename());
        writer.writeString(instance.getStateGeoId());
        writer.writeString(instance.getCurrentStatus());
//        if (instance.getFeatureList().size() == 0) {
//            writer.writeObject(new ArrayList<Feature>());
//        } else {
//            writer.writeObject(new ArrayList<Feature>(instance.getFeatureList()));
//        }
    }

    public static void deserialize(SerializationStreamReader reader, FeaturesMetadata instance) throws SerializationException {

        instance.setId(reader.readInt());
        instance.setState(reader.readString());
        instance.setStateAbbr(reader.readString());
        instance.setUsgsDate(reader.readString());
        instance.setFilename(reader.readString());
        instance.setStateGeoId(reader.readString());
        instance.setCurrentStatus(reader.readString());
        //instance.setFeatureList((List) reader.readObject());
    }
}
