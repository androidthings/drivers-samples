/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidthings.driversamples;

public class MusicNotes {

    public static final double REST = -1;
    public static final double G4 = 391.995;
    public static final double E4_FLAT = 311.127;

    public static final double[] DRAMATIC_THEME = {
            G4, REST, G4, REST, G4, REST, E4_FLAT, E4_FLAT
    };

    private MusicNotes() {
        //no instance
    }
}
