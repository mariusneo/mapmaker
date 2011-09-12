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
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

import java.util.Set;

/**
 * This class contains static data members and methods used for the various Geography stuff in the app. It
 * replaces MtfccUtil and will replace FeatureUtil.
 * <p/>
 * This class is intended to prevent excessive database hits. Going this way should be even faster than
 * caching, as well. Also, I just find maps damn useful.
 * <p/>
 * This class makes heavy use of Google's Guava collections library. I like the idea of when I say something
 * is immutable, the elements inside it are immutable too.
 *
 * @author Jason Ferguson
 * @since 0.4
 */
public class GeographyUtils {

    // all the data members are public so that I can get them and iterate through them as necessary
    public static final String currentTigerYear = "10";
    public static final String currentTigerCD = "111";

    public static final BiMap<String, String> stateGeoIdMap;
    public static final BiMap<String, String> stateAbbreviations;
    public static final BiMap<String, String> mangledNamesMap;
    public static final BiMap<String, String> nameToMtfccMap;
    public static final BiMap<String, String> prettyNameToMtfccMap;
    public static final Set<String> stateBasedMtfccs;
    public static final Set<String> countyBasedMtfccs;
    public static final Set<String> allMtfccs;

    static {

        stateGeoIdMap = new ImmutableBiMap.Builder<String, String>()
                .put("01", "Alabama")
                .put("02", "Alaska")
                .put("04", "Arizona")
                .put("05", "Arkansas")
                .put("06", "California")
                .put("08", "Colorado")
                .put("09", "Connecticut")
                .put("10", "Delaware")
                .put("11", "District of Columbia")
                .put("12", "Florida")
                .put("13", "Georgia")
                .put("15", "Hawaii")
                .put("16", "Idaho")
                .put("17", "Illinois")
                .put("18", "Indiana")
                .put("19", "Iowa")
                .put("20", "Kansas")
                .put("21", "Kentucky")
                .put("22", "Louisiana")
                .put("23", "Maine")
                .put("24", "Maryland")
                .put("25", "Massachusetts")
                .put("26", "Michigan")
                .put("27", "Minnesota")
                .put("28", "Mississippi")
                .put("29", "Missouri")
                .put("30", "Montana")
                .put("31", "Nebraska")
                .put("32", "Nevada")
                .put("33", "New Hampshire")
                .put("34", "New Jersey")
                .put("35", "New Mexico")
                .put("36", "New York")
                .put("37", "North Carolina")
                .put("38", "North Dakota")
                .put("39", "Ohio")
                .put("40", "Oklahoma")
                .put("41", "Oregon")
                .put("42", "Pennsylvania")
                .put("72", "Puerto Rico")
                .put("44", "Rhode Island")
                .put("45", "South Carolina")
                .put("46", "South Dakota")
                .put("47", "Tennessee")
                .put("48", "Texas")
                .put("49", "Utah")
                .put("50", "Vermont")
                .put("51", "Virginia")
                .put("52", "Washington")
                .put("54", "West Virginia")
                .put("55", "Wisconsin")
                .put("56", "Wyoming")
                .build();

        stateAbbreviations = new ImmutableBiMap.Builder<String, String>()
                .put("Alabama", "AL")
                .put("Alaska", "AK")
                .put("Arizona", "AZ")
                .put("Arkansas", "AR")
                .put("California", "CA")
                .put("Colorado", "CO")
                .put("Connecticut", "CT")
                .put("Delaware", "DE")
                .put("District of Columbia", "DC")
                .put("Florida", "FL")
                .put("Georgia", "GA")
                .put("Hawaii", "HI")
                .put("Idaho", "ID")
                .put("Illinois", "IL")
                .put("Indiana", "IN")
                .put("Iowa", "IA")
                .put("Kansas", "KS")
                .put("Kentucky", "KY")
                .put("Louisiana", "LA")
                .put("Maine", "ME")
                .put("Maryland", "MD")
                .put("Massachusetts", "MA")
                .put("Michigan", "MI")
                .put("Minnesota", "MN")
                .put("Mississippi", "MS")
                .put("Missouri", "MO")
                .put("Montana", "MT")
                .put("Nebraska", "NE")
                .put("Nevada", "NV")
                .put("New Hampshire", "NH")
                .put("New Jersey", "NJ")
                .put("New Mexico", "NM")
                .put("New York", "NY")
                .put("North Carolina", "NC")
                .put("North Dakota", "ND")
                .put("Ohio", "OH")
                .put("Oklahoma", "OK")
                .put("Oregon", "OR")
                .put("Pennsylvania", "PA")
                .put("Puerto Rico", "PR")
                .put("Rhode Island", "RI")
                .put("South Carolina", "SC")
                .put("South Dakota", "SD")
                .put("Tennessee", "TN")
                .put("Texas", "TX")
                .put("Utah", "UT")
                .put("Vermont", "VT")
                .put("Virginia", "VA")
                .put("Washington", "WA")
                .put("West Virginia", "WV")
                .put("Wisconsin", "WI")
                .put("Wyoming", "WY")
                .build();

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
                .put("STATE", MTFCC.STATE)
                .put("COUNTY", MTFCC.COUNTY)
                .put("CD111", MTFCC.CD111)
                .put("CONSOLIDATED_CITY", MTFCC.CONSOLIDATED_CITY)
                .put("ELEMENTARY_DISTRICT", MTFCC.ELEMENTARY_DISTRICT)
                .put("SECONDARY_DISTRICT", MTFCC.SECONDARY_DISTRICT)
                .put("UNIFIED_DISTRICT", MTFCC.UNIFIED_DISTRICT)
                .put("STATE_UPPER_DISTRICT", MTFCC.STATE_UPPER_DISTRICT)
                .put("STATE_LOWER_DISTRICT", MTFCC.STATE_LOWER_DISTRICT)
                .put("SUBMINOR_CIVIL_DIVISION", MTFCC.SUBMINOR_CIVIL_DIVISION)
                .put("INCORPORATED_PLACE", MTFCC.INCORPORATED_PLACE)
                .put("CENSUS_DESIGNATED_PLACE", MTFCC.CENSUS_DESIGNATED_PLACE)
                .put("COUNTY_SUBDIVISION", MTFCC.COUNTY_SUBDIVISION)
                .put("CENSUS_TRACT", MTFCC.CENSUS_TRACT)
                .put("VOTING_DISTRICT", MTFCC.VOTING_DISTRICT)
                .build();

        prettyNameToMtfccMap = new ImmutableBiMap.Builder<String, String>()
                .put("State", MTFCC.STATE)
                .put("County", MTFCC.COUNTY)
                .put("Congressional District", MTFCC.CD111)
                .put("Consolidated City", MTFCC.CONSOLIDATED_CITY)
                .put("Elementary School District", MTFCC.ELEMENTARY_DISTRICT)
                .put("Secondary School District", MTFCC.SECONDARY_DISTRICT)
                .put("Unified School District", MTFCC.UNIFIED_DISTRICT)
                .put("State Legislative Dist (Upper)", MTFCC.STATE_UPPER_DISTRICT)
                .put("State Legislative Dist (Lower)", MTFCC.STATE_LOWER_DISTRICT)
                .put("Subminor Civil Division", MTFCC.SUBMINOR_CIVIL_DIVISION)
                .put("Incorporated Place", MTFCC.INCORPORATED_PLACE)
                .put("Census Designated Place", MTFCC.CENSUS_DESIGNATED_PLACE)
                .put("County Subdivision", MTFCC.COUNTY_SUBDIVISION)
                .put("Census Tract", MTFCC.CENSUS_TRACT)
                .put("Voting District", MTFCC.VOTING_DISTRICT)
                .build();

        stateBasedMtfccs = new ImmutableSet.Builder<String>()
                .add(MTFCC.STATE)
                .add(MTFCC.COUNTY)
                .add(MTFCC.CD111)
                .add(MTFCC.CONSOLIDATED_CITY)
                .add(MTFCC.ELEMENTARY_DISTRICT)
                .add(MTFCC.SECONDARY_DISTRICT)
                .add(MTFCC.UNIFIED_DISTRICT)
                .add(MTFCC.STATE_UPPER_DISTRICT)
                .add(MTFCC.STATE_LOWER_DISTRICT)
                .add(MTFCC.SUBMINOR_CIVIL_DIVISION)
                .add(MTFCC.INCORPORATED_PLACE)
                .add(MTFCC.CENSUS_DESIGNATED_PLACE)
                .build();

        countyBasedMtfccs = new ImmutableSet.Builder<String>()
                .add(MTFCC.COUNTY)
                .add(MTFCC.COUNTY_SUBDIVISION)
                .add(MTFCC.CENSUS_TRACT)
                .add(MTFCC.VOTING_DISTRICT)
                .build();

        allMtfccs = new ImmutableSet.Builder<String>()
                .addAll(stateBasedMtfccs)
                .addAll(countyBasedMtfccs)
                .build();

    }

    public static String getGeoIdForState(String state) {
        return stateGeoIdMap.inverse().get(state);
    }

    public static String getStateForGeoId(String geoId) {
        return stateGeoIdMap.get(geoId);
    }

    public static String getStateForAbbreviation(String abbr) {
        return stateAbbreviations.inverse().get(abbr);
    }

    public static String getAbbreviationForState(String state) {
        return stateAbbreviations.get(state);
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

    public static boolean isStateBasedMtfcc(String mtfcc) {
        return stateBasedMtfccs.contains(mtfcc);
    }

    public static boolean isCountyBasedMtfcc(String mtfcc) {
        return countyBasedMtfccs.contains(mtfcc);
    }

    public static String getNameForMtfcc(String mtfcc) {
        return nameToMtfccMap.inverse().get(mtfcc);
    }

    public static String getMtfccForName(String name) {
        return nameToMtfccMap.get(name);
    }

    public static String getPrettyNameForMtfcc(String mtfcc) {
        return prettyNameToMtfccMap.inverse().get(mtfcc);
    }

    public static String getMtfccForPrettyName(String prettyName) {
        return prettyNameToMtfccMap.get(prettyName);
    }
    /**
     * Simplify a Geometry by reducing the number of points (technically, by increasing the distance between points to
     * whatever the tolerance is).
     *
     * @param geometry  Geometry object to simplify
     * @param tolerance minimum distance between two points
     * @return simplified Geometry
     */
    public static Geometry simplifyGeometry(Geometry geometry, double tolerance) {
        return TopologyPreservingSimplifier.simplify(geometry, tolerance);
    }

    public class MTFCC {
        public static final String STATE = "G4000";

        // TODO: Add class
        // State-based features
        public static final String COUNTY = "G4020";
        public static final String CD111 = "G5200";
        public static final String CONSOLIDATED_CITY = "G4120";
        public static final String ELEMENTARY_DISTRICT = "G5400";
        public static final String SECONDARY_DISTRICT = "G5410";
        public static final String UNIFIED_DISTRICT = "G5420";
        public static final String STATE_UPPER_DISTRICT = "G5210";
        public static final String STATE_LOWER_DISTRICT = "G5220";
        public static final String SUBMINOR_CIVIL_DIVISION = "G4060";
        public static final String INCORPORATED_PLACE = "G4110";
        public static final String CENSUS_DESIGNATED_PLACE = "G4210";

        // County-based features
        public static final String COUNTY_SUBDIVISION = "G4040";
        public static final String CENSUS_TRACT = "G5020";
        public static final String VOTING_DISTRICT = "G5240";
    }

    public class Status {

        public static final String IMPORTING = "Importing";
        public static final String IMPORTED = "Imported";
        public static final String NOT_IMPORTED = "Not Imported";
        public static final String NOT_AVAILABLE = "Not Available";
        public static final String DELETING = "Deleting";

    }
}
