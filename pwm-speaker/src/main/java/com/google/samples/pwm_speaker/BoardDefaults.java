package com.google.samples.pwm_speaker;

import android.os.Build;

public class BoardDefaults {

    public static String getPwmPin() {
        switch (Build.DEVICE) {
            case "edison":
                return "IO3";
            case "rpi3":
                return "PWM1";
            case "imx6ul":
                return "??"; // TODO
            default:
                throw new UnsupportedOperationException("Unknown device: " + Build.DEVICE);
        }
    }

    private BoardDefaults() {
        //no instance
    }
}
