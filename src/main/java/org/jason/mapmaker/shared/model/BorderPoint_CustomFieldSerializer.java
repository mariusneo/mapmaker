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
 * GWT CustomFieldSerializer for BorderPoint objects.
 *
 * @since 0.4.3
 * @author Jason Ferguson
 */
@SuppressWarnings("unused")
public class BorderPoint_CustomFieldSerializer {

    public static void serialize(SerializationStreamWriter writer, BorderPoint instance) throws SerializationException {
        writer.writeLong(instance.getId());
        writer.writeObject(instance.getLocation());
        writer.writeDouble(instance.getLat());
        writer.writeDouble(instance.getLng());
    }

    public static void deserialize(SerializationStreamReader reader, BorderPoint instance) throws SerializationException {
        instance.setId(reader.readLong());
        instance.setLocation((Location) reader.readObject());
        instance.setLat(reader.readDouble());
        instance.setLng(reader.readDouble());
    }
}
