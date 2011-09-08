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
package org.jason.mapmaker.shared.action;


import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import org.jason.mapmaker.shared.result.GetLocationsResult;

/**
 * GWTP Action class to get Locations based on two possible sets of criteria:
 *
 * - MTFCC Code and State FIPS
 * - MTFCC Code, State FIPS, and County FIPS
 *
 * @since 0.2
 * @author Jason Ferguson
 */
public class GetLocationsAction extends UnsecuredActionImpl<GetLocationsResult> {

    private String mtfccCode;
    private String stateFP;
    private String countyFP;

    // for serialization only, do not use
    public GetLocationsAction() {
    }

    // constructor meant for use to get state-based features
    public GetLocationsAction(String mtfccCode, String stateFP) {
        this.mtfccCode = mtfccCode;
        this.stateFP = stateFP;
    }

    // constructor meant for use to get county-based features
    public GetLocationsAction(String mtfccCode, String stateFP, String countyFP) {
        this.mtfccCode = mtfccCode;
        this.stateFP = stateFP;
        this.countyFP = countyFP;
    }

    public void setMtfccCode(String mtfccCode) {
        this.mtfccCode = mtfccCode;
    }

    public void setStateFP(String stateFP) {
        this.stateFP = stateFP;
    }

    public void setCountyFP(String countyFP) {
        this.countyFP = countyFP;
    }

    public String getMtfccCode() {
        return mtfccCode;
    }

    public String getStateFP() {
        return stateFP;
    }

    public String getCountyFP() {
        return countyFP;
    }
}
