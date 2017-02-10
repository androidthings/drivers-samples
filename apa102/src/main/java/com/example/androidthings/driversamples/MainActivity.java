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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;

import java.io.IOException;

/**
 * Sample activity that demonstrates usage of the APA102 LED driver.
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // LED configuration.
    private static final int NUM_LEDS = 7;
    private static final int LED_BRIGHTNESS = 5; // 0 ... 31
    private static final Apa102.Mode LED_MODE = Apa102.Mode.BGR;

    // Animation configuration.
    private static final int FRAME_DELAY_MS = 100; // 10fps

    private Apa102 mLedstrip;
    private int[] mLedColors;
    private int mFrame = 0;
    private Handler mHandler = new Handler();
    private HandlerThread mPioThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "APA102 MainActivity created");

        mPioThread = new HandlerThread("pioThread");
        mPioThread.start();

        mHandler = new Handler(mPioThread.getLooper());

        mLedColors = new int[NUM_LEDS];
        try {
            Log.d(TAG, "Initializing LED strip");
            mLedstrip = new Apa102(BoardDefaults.getSPIPort(), LED_MODE);
            mLedstrip.setBrightness(LED_BRIGHTNESS);
            mHandler.post(mAnimateRunnable);
        } catch (IOException e) {
            Log.e(TAG, "Error initializing LED strip", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove pending sensor Runnable from the handler.
        mHandler.removeCallbacks(mAnimateRunnable);
        mPioThread.quitSafely();
        Log.d(TAG, "Closing LED strip");
        try {
            mLedstrip.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception closing LED strip", e);
        } finally {
            mLedstrip = null;
        }
    }

    private Runnable mAnimateRunnable = new Runnable() {
        final float[] hsv = {1f, 1f, 1f};

        @Override
        public void run() {
            try {
                for (int i = 0; i < mLedColors.length; i++) { // Assigns gradient colors.
                    int n = (i + mFrame) % mLedColors.length;
                    hsv[0] = n * 360.f / mLedColors.length;
                    mLedColors[i] = Color.HSVToColor(0, hsv);
                }
                mLedstrip.write(mLedColors);
                mFrame = (mFrame + 1) % mLedColors.length;
            } catch (IOException e) {
                Log.e(TAG, "Error while writing to LED strip", e);
            }
            mHandler.postDelayed(mAnimateRunnable, FRAME_DELAY_MS);
        }
    };

}
