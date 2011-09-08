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
 * Maintain the application-wide settings
 *
 * @author Jason Ferguson
 * @since 0.4.2
 */
@Entity
@Table(name = "GENERICSETTINGS")
@SuppressWarnings("unused")
public class GenericSettings implements Serializable, IsSerializable{

    private int id;
    private String tigerVersion;
    private String usgsVersion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="TIGERVERSION")
    public String getTigerVersion() {
        return tigerVersion;
    }

    public void setTigerVersion(String tigerVersion) {
        this.tigerVersion = tigerVersion;
    }

    @Column(name="USGSVERSION")
    public String getUsgsVersion() {
        return usgsVersion;
    }

    public void setUsgsVersion(String usgsVersion) {
        this.usgsVersion = usgsVersion;
    }
}
