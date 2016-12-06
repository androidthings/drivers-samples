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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.ssd1306.BitmapHelper;
import com.google.android.things.contrib.driver.ssd1306.Ssd1306;

import java.io.IOException;

/**
 * Activity that tests the Ssd1306 display.
 */
public class OledScreenActivity extends Activity {
    private static final String TAG = "OledScreenActivity";
    private static final int FPS = 30; // Frames per second on draw thread
    private static final int BMPS = 2; // Frequency for changes of bitmaps in test

    private boolean mExpandingPixels = true;
    private int mPixelMod = 1;
    private int mBmpMod = 0;
    private int tick = 0;
    private Modes mMode = Modes.TestBmp;
    private Ssd1306 mScreen;

    private Handler mHandler = new Handler();

    enum Modes {
        None,
        TestPixels,
        TestBmp
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mScreen = new Ssd1306(BoardDefaults.getI2CPort());
        } catch (IOException e) {
            Log.e(TAG, "Error while opening screen", e);
            throw new RuntimeException(e);
        }
        Log.d(TAG, "OLED screen service created");
        mHandler.post(mDrawRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // remove pending runnable from the handler
        mHandler.removeCallbacks(mDrawRunnable);
        // Close the device.
        try {
            mScreen.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing SSD1306", e);
        } finally {
            mScreen = null;
        }
    }

    /**
     * Draws crosshair pattern.
     */
    private void drawCrosshairs() {
        // Draw simple crosshair pattern.
        int y = tick % mScreen.getLcdHeight();
        mScreen.clearPixels();
        for (int x = 0; x < mScreen.getLcdWidth(); x++) {
            mScreen.setPixel(x, y, true);
            mScreen.setPixel(x, mScreen.getLcdHeight() - (y + 1), true);
        }
        int x = tick % mScreen.getLcdWidth();
        for (y = 0; y < mScreen.getLcdHeight(); y++) {
            mScreen.setPixel(x, y, true);
            mScreen.setPixel(mScreen.getLcdWidth() - (x + 1), y, true);
        }
    }

    /**
     * Draws expanding and contracting pixels.
     */
    private void drawTestPixels() {
        if (mExpandingPixels) {
            for (int x = 0; x < mScreen.getLcdWidth(); x++) {
                for (int y = 0; y < mScreen.getLcdHeight() && mMode == Modes.TestPixels; y++) {
                    mScreen.setPixel(x, y, (x % mPixelMod) == 1 && (y % mPixelMod) == 1);
                }
            }
            mPixelMod++;
            if (mPixelMod > mScreen.getLcdHeight()) {
                mExpandingPixels = false;
                mPixelMod = mScreen.getLcdHeight();
            }
        } else {
            for (int x = 0; x < mScreen.getLcdWidth(); x++) {
                for (int y = 0; y < mScreen.getLcdHeight() && mMode == Modes.TestPixels; y++) {
                    mScreen.setPixel(x, y, (x % mPixelMod) == 1 && (y % mPixelMod) == 1);
                }
            }
            mPixelMod--;
            if (mPixelMod < 1) {
                mExpandingPixels = true;
                mPixelMod = 1;
            }
        }
    }

    /**
     * Draws a BMP in one of three positions.
     */
    private void drawMovingBmp() {
        Bitmap sampleImage = BitmapFactory.decodeResource(getResources(), R.drawable.sampleimage);
        // Move the bmp every few ticks
        if (tick % BMPS == 0) {
            mScreen.clearPixels();
            for (int i = 0; i < 4; i++) {
                if (i == mBmpMod) {
                    int position = i * (int) ((double) sampleImage.getWidth() * 0.9);
                    // Rolls back.
                    if (i == 3) {
                        position = (int) ((double) sampleImage.getWidth() * 0.9);
                    }
                    // Change the x-position but keep y-position at the top.
                    BitmapHelper.setBmpData(mScreen, position, 0, sampleImage, false);
                }
            }
            mBmpMod = (mBmpMod + 1) % 4;
        }
    }

    private Runnable mDrawRunnable = new Runnable() {
        /**
         * Updates the display and tick counter.
         */
        @Override
        public void run() {
            // exit Runnable if the device is already closed
            if (mScreen == null) {
                return;
            }
            tick++;
            try {
                switch (mMode) {
                    case TestPixels:
                        drawTestPixels();
                        break;
                    case TestBmp:
                        drawMovingBmp();
                        break;
                    default:
                        drawCrosshairs();
                        break;
                }
                mScreen.show();
                mHandler.postDelayed(this, 1000 / FPS);
            } catch (IOException e) {
                Log.e(TAG, "Exception during screen update", e);
            }
        }
    };
}
