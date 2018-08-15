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
import android.view.KeyEvent;

import com.google.android.things.contrib.driver.cap1xxx.Cap1xxx;
import com.google.android.things.contrib.driver.cap1xxx.Cap1xxxInputDriver;

import java.io.IOException;

public class CaptouchActivity extends Activity {
    private static final String TAG = "CaptouchActivity";

    private Cap1xxxInputDriver mInputDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set input key codes
        int[] keyCodes = {
                KeyEvent.KEYCODE_1,
                KeyEvent.KEYCODE_2,
                KeyEvent.KEYCODE_3,
                KeyEvent.KEYCODE_4,
                KeyEvent.KEYCODE_5,
                KeyEvent.KEYCODE_6,
                KeyEvent.KEYCODE_7,
                KeyEvent.KEYCODE_8
        };

        try {
            mInputDriver = new Cap1xxxInputDriver(BoardDefaults.getI2CPort(), null,
                    Cap1xxx.Configuration.CAP1208, keyCodes);

            // Disable repeated events
            mInputDriver.setRepeatRate(Cap1xxx.REPEAT_DISABLE);
            // Block touches above 4 unique inputs
            mInputDriver.setMultitouchInputMax(4);

            mInputDriver.register();

        } catch (IOException e) {
            Log.w(TAG, "Unable to open driver connection", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mInputDriver != null) {
            mInputDriver.unregister();

            try {
                mInputDriver.close();
            } catch (IOException e) {
                Log.w(TAG, "Unable to close touch driver", e);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return handleKeyEvent(keyCode, true) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return handleKeyEvent(keyCode, false) || super.onKeyUp(keyCode, event);
    }

    /**
     * Handle key events from captouch inputs
     */
    private boolean handleKeyEvent(int keyCode, boolean pressed) {
        String event = pressed ? "pressed" : "released";
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
                Log.d(TAG, String.format("Captouch key %s: %d", event, keyCode));
                return true;
            default:
                Log.d(TAG, String.format("Unknown key %s: %d", event, keyCode));
                return false;
        }
    }
}
