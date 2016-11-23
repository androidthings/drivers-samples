package com.google.samples.servo;

import android.os.Build;

class BoardDefaults {

    private BoardDefaults() {
        //no instance
    }

    public static String getPwmPin() {
        switch (Build.DEVICE) {
            case "edison":
                return "IO5";
            case "rpi3":
                return "PWM0";
            case "imx6ul":
                return "??"; //TODO
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }
}
