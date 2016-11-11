package com.google.samples.temperature;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.google.brillo.driver.bmx280.Bmx280TemperatureSensorDriver;

import java.io.IOException;

/**
 * TemperatureActivity is an example that use the driver for the BMP280 temperature sensor.
 */
public class TemperatureActivity extends Activity implements SensorEventListener {
    private static final String TAG = TemperatureActivity.class.getSimpleName();

    private Bmx280TemperatureSensorDriver mTemperatureSensorDriver;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting TemperatureActivity");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerDynamicSensorCallback(new SensorManager.DynamicSensorCallback() {
            @Override
            public void onDynamicSensorConnected(Sensor sensor) {
                if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE
                        || sensor.getType() == Sensor.TYPE_TEMPERATURE) {
                    Log.i(TAG, "Temperature sensor connected");
                    mSensorManager.registerListener(TemperatureActivity.this,
                            sensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        try {
            mTemperatureSensorDriver = new Bmx280TemperatureSensorDriver(BoardDefaults.getI2CPort());
            mTemperatureSensorDriver.register();
        } catch (IOException e) {
            Log.e(TAG, "Error configuring sensor", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Closing sensor");
        if (mTemperatureSensorDriver != null) {
            mSensorManager.unregisterListener(this);
            mTemperatureSensorDriver.unregister();
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
