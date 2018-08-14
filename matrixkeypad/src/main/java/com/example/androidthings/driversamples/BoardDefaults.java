/*
 * Copyright 2017, The Android Open Source Project
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

import android.os.Build;

public class BoardDefaults {
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_RPI3BP = "rpi3bp";
    private static final String DEVICE_IMX7D_PICO = "imx7d_pico";

    public static String[] getRowPins() {
        switch (Build.DEVICE) {
            case DEVICE_RPI3:
            case DEVICE_RPI3BP:
                return new String[] {"BCM27", "BCM5", "BCM6", "BCM26"};
            case DEVICE_IMX7D_PICO:
                return new String[] {"GPIO2_IO03", "GPIO2_IO01", "GPIO2_IO02", "GPIO2_IO05"};
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String[] getColPins() {
        switch (Build.DEVICE) {
            case DEVICE_RPI3:
            case DEVICE_RPI3BP:
                return new String[] {"BCM16", "BCM24", "BCM23"};
            case DEVICE_IMX7D_PICO:
                return new String[] {"GPIO2_IO07", "GPIO6_IO12", "GPIO6_IO13"};
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }
}
