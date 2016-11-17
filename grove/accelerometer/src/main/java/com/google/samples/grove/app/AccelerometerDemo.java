package com.google.samples.grove.app;

import com.google.brillo.driver.grove.accelerometer.Mma7660Fc;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.userdriver.sensors.AccelerometerDriver;
import android.hardware.userdriver.UserDriverManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

/**
 * AccelerometerDemo is a sample activity that use an Accelerometer driver to
 * read data from a Grove accelerator and log them.
 */
public class AccelerometerDemo extends Activity implements SensorEventListener {
    private static final String TAG = AccelerometerDemo.class.getSimpleName();

    private Mma7660Fc mMma770Fc;
    private AccelerometerDriver mAccelerometerDriver;
    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Accelerometer demo created");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerDynamicSensorCallback(new SensorManager.DynamicSensorCallback() {
            @Override
            public void onDynamicSensorConnected(Sensor sensor) {
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    Log.i(TAG, "Accelerometer sensor connected");
                    mSensorManager.registerListener(AccelerometerDemo.this, sensor,
                            SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });
        try {
            mMma770Fc = new Mma7660Fc(BoardDefaults.getI2CPort());
            mAccelerometerDriver = mMma770Fc.createAccelerometerDriver();
            UserDriverManager.getManager().registerSensorDriver(mAccelerometerDriver);
            Log.i(TAG, "Accelerometer driver registered");
        } catch (IOException e) {
            Log.e(TAG, "Error initializing accelerometer driver: ", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        UserDriverManager.getManager().unregisterSensorDriver(mAccelerometerDriver);
        try {
            mMma770Fc.close();
        }  catch (IOException e) {
            Log.e(TAG, "Error closing accelerometer driver: ", e);
        } finally {
            mMma770Fc = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "Accelerometer event: " +
                event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accelerometer accuracy changed: " + accuracy);
    }
}
