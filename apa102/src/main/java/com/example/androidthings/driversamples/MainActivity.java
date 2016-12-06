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

import com.google.android.things.driver.apa102.Apa102;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Sample activity that demonstrates controlling the RGB Led lights driver.
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // LED configuration
    private static final int NUM_LEDS = 13;
    private static final int LED_BRIGHTNESS = 12; // 0 ... 31
    private static final Apa102.Mode LED_MODE = Apa102.Mode.BGR;

    private Apa102 mDriver;
    private int[] mLedColors;
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
            mDriver = new Apa102(BoardDefaults.getSPIPort(), LED_MODE);
            // Create and initialize the RGB LED object
            mDriver.setBrightness(LED_BRIGHTNESS);
            mHandler.post(mAnimateRunnable);
        } catch (IOException e) {
            Log.e(TAG, "Error initializing LED driver", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove pending sensor Runnable from the handler.
        mHandler.removeCallbacks(mAnimateRunnable);
        mPioThread.quitSafely();
        // Close the I2C device.
        Log.d(TAG, "Closing Sensor device");
        try {
            mDriver.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception closing LED driver", e);
        } finally {
            mDriver = null;
        }
    }

    private Runnable mAnimateRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "Animating!");
            animateColorBlink();
            animateColorRGB();
            animateColorRainbow();
        }
    };

    /**
     * Blinks the RGB led strip with varying colors.
     */
    private void animateColorBlink() {
        try {
            Log.d(TAG, "CLEAR");
            ColorsHelper.clear(mLedColors);
            mDriver.write(mLedColors);
            TimeUnit.SECONDS.sleep(1);

            Log.d(TAG, "RED");
            ColorsHelper.setAllToColor(mLedColors, Color.RED);
            mDriver.write(mLedColors);
            TimeUnit.SECONDS.sleep(1);

            Log.d(TAG, "CLEAR");
            ColorsHelper.clear(mLedColors);
            mDriver.write(mLedColors);
            TimeUnit.SECONDS.sleep(1);

            Log.d(TAG, "GREEN");
            ColorsHelper.setAllToColor(mLedColors, Color.GREEN);
            mDriver.write(mLedColors);
            TimeUnit.SECONDS.sleep(1);

            Log.d(TAG, "CLEAR");
            ColorsHelper.clear(mLedColors);
            mDriver.write(mLedColors);
            TimeUnit.SECONDS.sleep(1);

            Log.d(TAG, "BLUE");
            ColorsHelper.setAllToColor(mLedColors, Color.BLUE);
            mDriver.write(mLedColors);
            TimeUnit.SECONDS.sleep(1);

            Log.d(TAG, "CLEAR");
            ColorsHelper.clear(mLedColors);
            mDriver.write(mLedColors);
        } catch (IOException|InterruptedException e) {
            Log.e(TAG, "Error while blinking LEDs", e);
        }
    }


    /**
     * Alternates the LEDs with RGB colors.
     */
    private void animateColorRGB() {
        try {
            for (int j = 0; j < mLedColors.length * 2; j++) { // Shifts RGB pattern.
                for (int i = 0; i < mLedColors.length; i++) { // Sets RGB pattern.
                    switch ((i + j) % 3) {
                        case 0:
                            mLedColors[i] = Color.RED;
                            break;
                        case 1:
                            mLedColors[i] = Color.GREEN;
                            break;
                        case 2:
                            mLedColors[i] = Color.BLUE;
                            break;
                        default:
                            // Never should be reached.
                            mLedColors[i] = Color.BLACK;
                            break;
                    }
                }

                mDriver.write(mLedColors);
                // 10 FPS
                TimeUnit.MILLISECONDS.sleep(100);
            } // End loop over color shift
        } catch (IOException|InterruptedException e) {
            Log.e(TAG, "Exception while blinking RGB LED strip", e);
        }
    }

    /**
     * Blasts rainbow colors to the LED pins.
     */
    private void animateColorRainbow() {
        try {
            // Add data for LED colors R/G/B pattern
            for (int j = 0; j < mLedColors.length * 10; j++) { // Shifts color gradient.
                for (int i = 0; i < mLedColors.length; i++) { // Assigns gradient colors.
                    mLedColors[i] = ColorsHelper.getRainbowColor(i + j);
                }
                mDriver.write(mLedColors);

                // 10 FPS
                TimeUnit.MILLISECONDS.sleep(100);
            } // End loop over color shift

            // Done, clear LEDs.
            ColorsHelper.clear(mLedColors);
            mDriver.write(mLedColors);
        } catch (IOException|InterruptedException e) {
            Log.e(TAG, "Error while writing rainbow to RGB LED strip", e);
        }
    }
}