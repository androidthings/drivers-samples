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

package com.google.samples.spi.apa102;

import android.os.Build;

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
    private static final String DEVICE_EDISON = "edison";
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_NXP = "imx6ul";

    /**
     * Return the preferred I2C port for each board.
     */
    public static String getSPIPort() {
        // TODO: confirm DEVICE and preferred port for RPI3 and NXP
        switch (Build.DEVICE) {
            case DEVICE_EDISON:
                return "SPI2";
            case DEVICE_RPI3:
                return "SPI0.0";
            case DEVICE_NXP:
                return "??";
            default:
                throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
        }
    }
}