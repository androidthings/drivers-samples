/*
 * Copyright 2017, The Android Open Source Project
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
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.things.contrib.driver.matrixkeypad.MatrixKeypadInputDriver;
import java.io.IOException;

public class MatrixKeypadActivity extends Activity {
    private static final String TAG = MatrixKeypadActivity.class.getSimpleName();
    private static final boolean USE_LAYOUT = true;

    private MatrixKeypadInputDriver mMatrixKeypadDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (USE_LAYOUT) {
            setContentView(R.layout.layout_main);
        }
        try {
            mMatrixKeypadDriver = new MatrixKeypadInputDriver(BoardDefaults.getRowPins(),
                    BoardDefaults.getColPins(),
                    new int[] {KeyEvent.KEYCODE_NUMPAD_1, KeyEvent.KEYCODE_NUMPAD_2,
                            KeyEvent.KEYCODE_NUMPAD_3, KeyEvent.KEYCODE_NUMPAD_4,
                            KeyEvent.KEYCODE_NUMPAD_5, KeyEvent.KEYCODE_NUMPAD_6,
                            KeyEvent.KEYCODE_NUMPAD_7, KeyEvent.KEYCODE_NUMPAD_8,
                            KeyEvent.KEYCODE_NUMPAD_9, KeyEvent.KEYCODE_NUMPAD_MULTIPLY,
                            KeyEvent.KEYCODE_NUMPAD_0, KeyEvent.KEYCODE_NUMPAD_ENTER});
            mMatrixKeypadDriver.register();
        } catch (IOException e) {
            Log.e(TAG, "Cannot register matrix keypad driver:", e);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.d(TAG, "Detect key down " + KeyEvent.keyCodeToString(keyCode) + " " + event
                .toString());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "Detect key up " + KeyEvent.keyCodeToString(keyCode) + " " + event
                    .toString());
        if (USE_LAYOUT && keyCode == KeyEvent.KEYCODE_ENTER) {
            // Update lines in sample
            ((TextView) findViewById(R.id.previousentry)).setText(
                ((EditText) findViewById(R.id.textentry)).getText().toString());
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mMatrixKeypadDriver.close();
        } catch (IOException e) {
            Log.e(TAG, "Driver unable to close:", e);
        }
    }
}
