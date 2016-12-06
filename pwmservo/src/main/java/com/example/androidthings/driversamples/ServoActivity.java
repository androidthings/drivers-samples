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
import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.pwmservo.Servo;

import java.io.IOException;

public class ServoActivity extends Activity {

    private static final String TAG = "ServoActivity";

    private Servo mServo;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mServo = new Servo(BoardDefaults.getPwmPin());
            mServo.setAngleRange(0f, 180f);
            mServo.setEnabled(true);
        } catch (IOException e) {
            Log.e(TAG, "Error creating Servo", e);
            return; // don't init handler
        }

        mHandler = new Handler();
        mHandler.post(mMoveServoRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacks(mMoveServoRunnable);
        }
        if (mServo != null) {
            try {
                mServo.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing Servo");
            } finally {
                mServo = null;
            }
        }
    }

    private Runnable mMoveServoRunnable = new Runnable() {

        private static final float ANGLE_STEP = 30;
        private static final long DELAY_MS = 5000L; // 5 seconds

        private double mAngle = Float.NEGATIVE_INFINITY;

        @Override
        public void run() {
            if (mServo == null) {
                return;
            }

            try {
                if (mAngle < mServo.getMinimumAngle()) {
                    mAngle = mServo.getMinimumAngle();
                } else {
                    mAngle = mAngle + ANGLE_STEP;
                    if (mAngle > mServo.getMaximumAngle()) {
                        mAngle = mServo.getMinimumAngle();
                    }
                }
                mServo.setAngle(mAngle);

                mHandler.postDelayed(this, DELAY_MS);
            } catch (IOException e) {
                Log.e(TAG, "Error setting Servo angle");
            }
        }
    };
}
