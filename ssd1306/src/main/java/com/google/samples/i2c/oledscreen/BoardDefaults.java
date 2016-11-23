package com.google.samples.i2c.oledscreen;

import android.os.Build;

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
    private static final String DEVICE_EDISON = "edison";
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_NXP = "imx6ul";

    /**
     * Return the preferred I2C port for each board.
     */
    public static String getI2CPort() {
        // TODO: confirm DEVICE and preferred port for NXP
        switch (Build.DEVICE) {
            case DEVICE_EDISON:
                return "I2C6";
            case DEVICE_RPI3:
                return "I2C1";
            case DEVICE_NXP:
                return "??";
            default:
                throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
        }
    }
}