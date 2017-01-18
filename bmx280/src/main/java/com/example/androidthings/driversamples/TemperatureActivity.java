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
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorManager.DynamicSensorCallback;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver;

import java.io.IOException;

/**
 * TemperatureActivity is an example that use the driver for the BMP280 temperature sensor.
 */
public class TemperatureActivity extends Activity implements SensorEventListener {
    private static final String TAG = TemperatureActivity.class.getSimpleName();

    private Bmx280SensorDriver mTemperatureSensorDriver;
    private SensorManager mSensorManager;

    private DynamicSensorCallback mDynamicSensorCallback = new DynamicSensorCallback() {
        @Override
        public void onDynamicSensorConnected(Sensor sensor) {
            if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                Log.i(TAG, "Temperature sensor connected");
                mSensorManager.registerListener(TemperatureActivity.this,
                        sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting TemperatureActivity");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerDynamicSensorCallback(mDynamicSensorCallback);

        try {
            mTemperatureSensorDriver = new Bmx280SensorDriver(BoardDefaults.getI2CPort());
            mTemperatureSensorDriver.registerTemperatureSensor();
        } catch (IOException e) {
            Log.e(TAG, "Error configuring sensor", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Closing sensor");
        if (mTemperatureSensorDriver != null) {
            mSensorManager.unregisterDynamicSensorCallback(mDynamicSensorCallback);
            mSensorManager.unregisterListener(this);
            mTemperatureSensorDriver.unregisterTemperatureSensor();
            try {
                mTemperatureSensorDriver.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing sensor", e);
            } finally {
                mTemperatureSensorDriver = null;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "sensor changed: " + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "sensor accuracy changed: " + accuracy);
    }
}
