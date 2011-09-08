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

import com.google.common.collect.ImmutableSet;

/**
 * Utility class containing public data members and methods to deal with Feature objects. Using a lookup member/method
 * should be faster than a database hit, and reduces dependence on the database.
 *
 * The class currently contains Google Guava ImmutableSet's holding the descriptions of the feature descriptions,
 * which descriptions are geographic, and which are manmade.
 *
 * @author Jason Ferguson
 * @since 0.4
 */
public class FeatureUtil {

    public static final ImmutableSet<String> geographicFeatureDescriptions;
    public static final ImmutableSet<String> manmadeFeatureDescriptions;
    public static final ImmutableSet<String> allFeatureDescriptions;

    static {

        geographicFeatureDescriptions = new ImmutableSet.Builder<String>()
                .add("Arch")
                .add("Area")
                .add("Arroyo")
                .add("Bar")
                .add("Basin")
                .add("Bay")
                .add("Beach")
                .add("Bench")
                .add("Bend")
                .add("Canal")
                .add("Cape")
                .add("Cave")
                .add("Channel")
                .add("Cliff")
                .add("Crater")
                .add("Crossing")
                .add("Dam")
                .add("Falls")
                .add("Flat")
                .add("Forest")
                .add("Gap")
                .add("Glacier")
                .add("Gut")
                .add("Harbor")
                .add("Island")
                .add("Isthmus")
                .add("Lake")
                .add("Lava")
                .add("Pillar")
                .add("Plain")
                .add("Range")
                .add("Rapids")
                .add("Reserve")
                .add("Reservoir")
                .add("Ridge")
                .add("Sea")
                .add("Slope")
                .add("Spring")
                .add("Stream")
                .add("Summit")
                .add("Swamp")
                .add("Valley")
                .add("Well")
                .add("Woods")
                .build();

        manmadeFeatureDescriptions = new ImmutableSet.Builder<String>()
                .add("Airport")
                .add("Bridge")
                .add("Building")
                .add("Cemetery")
                .add("Census")
                .add("Church")
                .add("Civil")
                .add("Hospital")
                .add("Levee")
                .add("Locale")
                .add("Military")
                .add("Mine")
                .add("Oilfield")
                .add("Park")
                .add("Populated Place")
                .add("Post Office")
                .add("School")
                .add("Tower")
                .add("Trail")
                .add("Tunnel")
                .build();

        allFeatureDescriptions = new ImmutableSet.Builder<String>()
                .addAll(geographicFeatureDescriptions)
                .addAll(manmadeFeatureDescriptions)
                .build();
    }

    public static boolean isGeographicFeature(String featureDescription) {

        return geographicFeatureDescriptions.contains(featureDescription);
    }

    public static boolean isManmadeFeature(String featureDescription) {

        return manmadeFeatureDescriptions.contains(featureDescription);

    }

    public static boolean isFeature(String featureDescription) {

        return allFeatureDescriptions.contains(featureDescription);

    }
}
