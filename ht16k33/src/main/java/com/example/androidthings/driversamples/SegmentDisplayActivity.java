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
import android.util.Log;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;

import java.io.IOException;

/**
 * SegmentDisplayActivity is an example that use the driver
 * for the HT16k33 Alphanumeric segment display.
 */
public class SegmentDisplayActivity extends Activity {
    private static final String TAG = SegmentDisplayActivity.class.getSimpleName();
    /**
     * I2C bus the segment display is connected to.
     */
    private static final String I2C_BUS = BoardDefaults.getI2CPort();

    private AlphanumericDisplay mSegmentDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting SegmentDisplayActivity");

        try {
            mSegmentDisplay = new AlphanumericDisplay(I2C_BUS);
            mSegmentDisplay.setBrightness(1.0f);
            mSegmentDisplay.setEnabled(true);
            mSegmentDisplay.clear();
            mSegmentDisplay.display("ABCD");
        } catch (IOException e) {
            Log.e(TAG, "Error configuring display", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSegmentDisplay != null) {
            Log.i(TAG, "Closing display");
            try {
                mSegmentDisplay.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing display", e);
            } finally {
                mSegmentDisplay = null;
            }
        }
    }
}
