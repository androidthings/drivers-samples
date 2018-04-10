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

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.gps.NmeaGpsDriver;

import java.io.IOException;

public class GpsActivity extends Activity {
    private static final String TAG = "GpsActivity";

    public static final int UART_BAUD = 9600;
    public static final float ACCURACY = 2.5f; // From GPS datasheet

    private LocationManager mLocationManager;
    private NmeaGpsDriver mGpsDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // We need permission to get location updates
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // A problem occurred auto-granting the permission
            Log.d(TAG, "No permission");
            return;
        }

        try {
            // Register the GPS driver
            mGpsDriver = new NmeaGpsDriver(this, BoardDefaults.getUartName(),
                    UART_BAUD, ACCURACY);
            mGpsDriver.register();
            // Register for location updates
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, mLocationListener);
            mLocationManager.registerGnssStatusCallback(mStatusCallback);
            mLocationManager.addNmeaListener(mMessageListener);
        } catch (IOException e) {
            Log.w(TAG, "Unable to open GPS UART", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Verify permission was granted
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission");
            return;
        }

        if (mGpsDriver != null) {
            // Unregister components
            mGpsDriver.unregister();
            mLocationManager.removeUpdates(mLocationListener);
            mLocationManager.unregisterGnssStatusCallback(mStatusCallback);
            mLocationManager.removeNmeaListener(mMessageListener);
            try {
                mGpsDriver.close();
            } catch (IOException e) {
                Log.w(TAG, "Unable to close GPS driver", e);
            }
        }
    }

    /** Report location updates */
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.v(TAG, "Location update: " + location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    /** Report satellite status */
    private GnssStatus.Callback mStatusCallback = new GnssStatus.Callback() {
        @Override
        public void onStarted() { }

        @Override
        public void onStopped() { }

        @Override
        public void onFirstFix(int ttffMillis) { }

        @Override
        public void onSatelliteStatusChanged(GnssStatus status) {
            Log.v(TAG, "GNSS Status: " + status.getSatelliteCount() + " satellites.");
        }
    };

    /** Report raw NMEA messages */
    private OnNmeaMessageListener mMessageListener = new OnNmeaMessageListener() {
        @Override
        public void onNmeaMessage(String message, long timestamp) {
            Log.v(TAG, "NMEA: " + message);
        }
    };
}
