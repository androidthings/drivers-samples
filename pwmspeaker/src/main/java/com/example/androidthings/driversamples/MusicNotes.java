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
    public static final double G4 = 392;
    public static final double C5 = 523.25;
    public static final double E5 = 659.25;
    public static final double G5 = 783.99;

    public static final double[] SMB_OVERWORLD_MAIN_THEME = {
            E5, E5, REST, E5, REST, C5, E5, REST, G5, REST, REST, REST, G4
    };

    private MusicNotes() {
        //no instance
    }
}
