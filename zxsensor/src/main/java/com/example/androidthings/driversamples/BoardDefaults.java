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

import android.os.Build;

import com.google.android.things.pio.PeripheralManagerService;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
    private static final String DEVICE_EDISON_ARDUINO = "edison_arduino";
    private static final String DEVICE_EDISON = "edison";
    private static final String DEVICE_JOULE = "joule";
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_IMX6UL_PICO = "imx6ul_pico";
    private static final String DEVICE_IMX6UL_VVDN = "imx6ul_iopb";
    private static final String DEVICE_IMX7D_PICO = "imx7d_pico";
    private static String sBoardVariant = "";

    public static String getUartPin() {
        switch (getBoardVariant()) {
            // same for Edison Arduino breakout and Edison SOM
            case DEVICE_EDISON:
                return "UART1";
            case DEVICE_JOULE:
                return "UART1";
            case DEVICE_RPI3:
                return "UART0";
            case DEVICE_IMX6UL_PICO:
                return "UART3";
            case DEVICE_IMX6UL_VVDN:
                return "UART2";
            case DEVICE_IMX7D_PICO:
                return "UART6";
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String getGpioPin() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO7";
            case DEVICE_EDISON:
                return "GP45";
            case DEVICE_JOULE:
                return "FLASH_TRIGGER";
            case DEVICE_RPI3:
                return "BCM23";
            case DEVICE_IMX6UL_PICO:
                return "GPIO4_IO23";
            case DEVICE_IMX6UL_VVDN:
                return "GPIO3_IO01";
            case DEVICE_IMX7D_PICO:
                return "GPIO_174";
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String getI2CPin() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "I2C6";
            case DEVICE_EDISON:
                return "I2C1";
            case DEVICE_JOULE:
                return "I2C0";
            case DEVICE_RPI3:
                return "I2C1";
            case DEVICE_IMX6UL_PICO:
                return "I2C2";
            case DEVICE_IMX6UL_VVDN:
                return "I2C4";
            case DEVICE_IMX7D_PICO:
                return "I2C1";
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }

    private static String getBoardVariant() {
        if (!sBoardVariant.isEmpty()) {
            return sBoardVariant;
        }
        sBoardVariant = Build.DEVICE;
        // For the edison check the pin prefix
        // to always return Edison Breakout pin name when applicable.
        if (sBoardVariant.equals(DEVICE_EDISON)) {
            PeripheralManagerService pioService = new PeripheralManagerService();
            List<String> gpioList = pioService.getGpioList();
            if (gpioList.size() != 0) {
                String pin = gpioList.get(0);
                if (pin.startsWith("IO")) {
                    sBoardVariant = DEVICE_EDISON_ARDUINO;
                }
            }
        }
        return sBoardVariant;
    }
}
