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

package com.google.samples.servo;

import android.os.Build;

class BoardDefaults {

    private BoardDefaults() {
        //no instance
    }

    public static String getPwmPin() {
        switch (Build.DEVICE) {
            case "edison":
                return "IO6";
            case "rpi3":
                return "PWM0";
            case "imx6ul":
                return "??"; //TODO
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }
}
