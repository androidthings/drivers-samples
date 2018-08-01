/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.things.ads1015;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.TextView;

import com.google.android.things.contrib.driver.adc.ads1xxx.Ads1xxx;

import java.io.IOException;

/**
 * Sample activity that demonstrates the usage of the
 * Ads1xxx driver with an ADS1015 module.
 *
 * https://www.adafruit.com/product/1083
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DELAY_MS = 100; // 10 samples/second

    private Ads1xxx mAdcDriver;
    private Handler mHandler;
    private HandlerThread mReadThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Log.d(TAG, "Initializing ADC Driver");
            mAdcDriver = new Ads1xxx(BoardDefaults.getI2CPort(), Ads1xxx.Configuration.ADS1015);
            // Increase default range to fit +3.3V
            mAdcDriver.setInputRange(Ads1xxx.RANGE_4_096V);

            // Set up I/O polling thread
            mReadThread = new HandlerThread("ADC Reader");
            mReadThread.start();
            mHandler = new Handler(mReadThread.getLooper());
            mHandler.post(mReadAction);
        } catch (IOException e) {
            Log.e(TAG, "Failed to initialize ADC driver", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReadThread.quit();

        try {
            mAdcDriver.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to close ADC driver", e);
        } finally {
            mAdcDriver = null;
        }
    }

    /* Read a single analog sample and log the result */
    private Runnable mReadAction = new Runnable() {
        @Override
        public void run() {
            try {
                // Read differential between IN0+/IN1-
                final int value = mAdcDriver.readDifferentialInput(Ads1xxx.INPUT_DIFF_0P_1N);
                Log.i(TAG, "Current ADC value: " + value);
            } catch (IOException e) {
                Log.e(TAG, "Unable to read analog sample", e);
            }

            mHandler.postDelayed(this, DELAY_MS);
        }
    };
}
