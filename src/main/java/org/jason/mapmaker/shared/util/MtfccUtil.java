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
package org.jason.mapmaker.shared.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;

/**
 * Utility class for deal with MTFCC objects
 *
 * The class contains Google Guava BiMap and ImmutableSet implementations for lookup purposes, which is intended to
 * be faster than a database lookup (and even if it isn't, it still reduces dependency on the backend database).
 *
 * Maps (private, use the accessor methods):
 * - mangledNamesMap - used to convert between the official description of the MTFCC as contained in the shapefile
 *          (i.e. "CONSOLIDATED_CITY") and the abbreviated version used in the actual filenames of the shapefiles
 *          (i.e. "CONCITY")
 * - nameToMtfccMap - used to convert between the official description of the MTFCC (i.e. "STATE") and the MTFCC
 *          code (i.e. "G4000")
 *
 * Sets (public):
 * - stateBasedMtfccs - Set containing the name of the state-based MTFCCs
 * - countBasedMtfccs - Set containing the name of the county-based MTFCCs
 * - allMtfccs - You figure it out, hotshot.
 *
 * @author Jason Ferguson
 * @since 0.4
 */
public class MtfccUtil {

    public static final BiMap<String, String> mangledNamesMap;
    public static final BiMap<String, String> nameToMtfccMap;
    public static final ImmutableSet<String> stateBasedMtfccs;
    public static final ImmutableSet<String> countyBasedMtfccs;
    public static final ImmutableSet<String> allMtfccs;

    static {
        // since this is a utility class separated from the object itself, I don't mind importing the guava library
        // and avoiding some expensive database hits


        mangledNamesMap = new ImmutableBiMap.Builder<String, String>()
                .put("STATE", "STATE")
                .put("COUNTY", "COUNTY")
                .put("CD111", "CD111")
                .put("CONSOLIDATED_CITY", "CONCITY")
                .put("COUNTY_SUBDIVISION", "COUSUB")
                .put("ELEMENTARY_DISTRICT", "ELSD")
                .put("SECONDARY_DISTRICT", "SCSD")
                .put("UNIFIED_DISTRICT", "UNSD")
                .put("STATE_UPPER_DISTRICT", "SLDU")
                .put("STATE_LOWER_DISTRICT", "SLDL")
                .put("SUBMINOR_CIVIL_DIVISION", "SUBMCD")
                .put("INCORPORATED_PLACE", "PLACE")
                .put("CENSUS_TRACT", "TRACT")
                .put("VOTING_DISTRICT", "VTD")
                .build();

        nameToMtfccMap = new ImmutableBiMap.Builder<String, String>()
                .put("STATE", GeographyUtils.MTFCC.STATE)
                .put("COUNTY", GeographyUtils.MTFCC.COUNTY)
                .put("CD111", GeographyUtils.MTFCC.CD111)
                .put("CONSOLIDATED_CITY", GeographyUtils.MTFCC.CONSOLIDATED_CITY)
                .put("ELEMENTARY_DISTRICT", GeographyUtils.MTFCC.ELEMENTARY_DISTRICT)
                .put("SECONDARY_DISTRICT", GeographyUtils.MTFCC.SECONDARY_DISTRICT)
                .put("UNIFIED_DISTRICT", GeographyUtils.MTFCC.UNIFIED_DISTRICT)
                .put("STATE_UPPER_DISTRICT", GeographyUtils.MTFCC.STATE_UPPER_DISTRICT)
                .put("STATE_LOWER_DISTRICT", GeographyUtils.MTFCC.STATE_LOWER_DISTRICT)
                .put("SUBMINOR_CIVIL_DIVISION", GeographyUtils.MTFCC.SUBMINOR_CIVIL_DIVISION)
                .put("INCORPORATED_PLACE", GeographyUtils.MTFCC.INCORPORATED_PLACE)
                .put("COUNTY_SUBDIVISION", GeographyUtils.MTFCC.COUNTY_SUBDIVISION)
                .put("CENSUS_TRACT", GeographyUtils.MTFCC.CENSUS_TRACT)
                .put("VOTING_DISTRICT", GeographyUtils.MTFCC.VOTING_DISTRICT)
                .build();

        stateBasedMtfccs = new ImmutableSet.Builder<String>()
                .add(GeographyUtils.MTFCC.STATE)
                .add(GeographyUtils.MTFCC.COUNTY)
                .add(GeographyUtils.MTFCC.CD111)
                .add(GeographyUtils.MTFCC.CONSOLIDATED_CITY)
                .add(GeographyUtils.MTFCC.ELEMENTARY_DISTRICT)
                .add(GeographyUtils.MTFCC.SECONDARY_DISTRICT)
                .add(GeographyUtils.MTFCC.UNIFIED_DISTRICT)
                .add(GeographyUtils.MTFCC.STATE_UPPER_DISTRICT)
                .add(GeographyUtils.MTFCC.STATE_LOWER_DISTRICT)
                .add(GeographyUtils.MTFCC.SUBMINOR_CIVIL_DIVISION)
                .add(GeographyUtils.MTFCC.INCORPORATED_PLACE)
                .build();

        countyBasedMtfccs = new ImmutableSet.Builder<String>()
                .add(GeographyUtils.MTFCC.COUNTY)
                .add(GeographyUtils.MTFCC.COUNTY_SUBDIVISION)
                .add(GeographyUtils.MTFCC.CENSUS_TRACT)
                .add(GeographyUtils.MTFCC.VOTING_DISTRICT)
                .build();

        allMtfccs = new ImmutableSet.Builder<String>()
            .addAll(stateBasedMtfccs)
            .addAll(countyBasedMtfccs)
            .build();
    }

    /**
     * Mangles the "official" type name into the TIGER filename abbreviation
     *
     * @param typeName String representing the typename ("STATE","COUNTY","VOTING_DISTRICT", etc)
     * @return String representing the abbreviation used in the TIGER/Line shapefile file names ("CONCITY","VTD", etc)
     */
    public static String mangleTypeName(String typeName) {

        return mangledNamesMap.get(typeName);

    }

    /**
     * Demangles the TIGER/Line shapefile filename abbreviation back into the official type name
     *
     * @param mangledTypeName String representing the already mangled name
     * @return String representing the official name
     */
    public static String demangleTypeName(String mangledTypeName) {

        return mangledNamesMap.inverse().get(mangledTypeName);
    }

    /**
     * Return the mtfcc code for the given "official" name
     *
     * @param name String representing the official name
     * @return String representing the MTFCC code
     */
    public static String getMtfccForName(String name) {
        return nameToMtfccMap.get(name);
    }

    /**
     * Return the official name for a given MTFCC code
     *
     * @param mtfcc String representing the MTFCC code
     * @return String representing the official name
     */
    public static String getNameForMtfcc(String mtfcc) {
        return nameToMtfccMap.inverse().get(mtfcc);
    }
}
