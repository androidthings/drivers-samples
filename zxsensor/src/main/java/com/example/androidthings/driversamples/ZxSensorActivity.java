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

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.things.contrib.driver.zxsensor.ZxSensor;
import com.google.android.things.contrib.driver.zxsensor.ZxSensorI2c;
import com.google.android.things.contrib.driver.zxsensor.ZxSensorUart;

import java.io.IOException;

/**
 * ZxSensorActivity is an example that uses the driver
 * for the ZX Distance & Gesture Sensor. You can use i2c or UART (UART recommend only for debugging or POC).
 */
public class ZxSensorActivity extends Activity {

    private static final String TAG = ZxSensorActivity.class.getSimpleName();
    /**
     * Toggle this to see the I2C or UART examples
     */
    private static final boolean EXAMPLE_I2C = true;

    private ZxSensorI2c zxSensorI2c;
    private ZxSensorUart zxSensorUart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity started, swipe your hand over the sensor.");
        Log.d(TAG, "Demoing " + (EXAMPLE_I2C ? "I2C" : "UART"));

        if (EXAMPLE_I2C) {
            try {
                zxSensorI2c = ZxSensor.Factory.openViaI2c(BoardDefaults.getI2CPin(), BoardDefaults.getGpioPin());
            } catch (IOException e) {
                throw new IllegalStateException("Can't open, did you use the correct pin names?", e);
            }
            zxSensorI2c.setSwipeLeftListener(swipeLeftListener);
            zxSensorI2c.setSwipeRightListener(swipeRightListener);

        } else {
            try {
                zxSensorUart = ZxSensor.Factory.openViaUart(BoardDefaults.getUartPin());
            } catch (IOException e) {
                throw new IllegalStateException("Can't open, did you use the correct pin name?", e);
            }
            zxSensorUart.setSwipeLeftListener(swipeLeftListener);
            zxSensorUart.setSwipeRightListener(swipeRightListener);
        }
    }

    private final ZxSensor.SwipeLeftListener swipeLeftListener = new ZxSensor.SwipeLeftListener() {
        @Override
        public void onSwipeLeft(int speed) {
            logSwipe("left", speed);
        }
    };

    private final ZxSensor.SwipeRightListener swipeRightListener = new ZxSensor.SwipeRightListener() {
        @Override
        public void onSwipeRight(int speed) {
            logSwipe("right", speed);
        }
    };

    private void logSwipe(String direction, int speed) {
        Log.d(TAG, "Hey you swiped " + direction + " with velocity " + speed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (EXAMPLE_I2C) {
            zxSensorI2c.startMonitoringGestures();
        } else {
            zxSensorUart.startMonitoringGestures();
        }
    }

    @Override
    protected void onPause() {
        if (EXAMPLE_I2C) {
            zxSensorI2c.stopMonitoringGestures();
        } else {
            zxSensorUart.stopMonitoringGestures();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (EXAMPLE_I2C) {
            zxSensorI2c.close();
        } else {
            zxSensorUart.close();
        }
        super.onDestroy();
    }
}
