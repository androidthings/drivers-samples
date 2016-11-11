package com.google.samples.segmentdisplay;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.brillo.driver.tm1637.NumericDisplay;

import java.io.IOException;

/**
 * SegmentDisplayActivity is an example that use the driver
 * for the TM1637 Alphanumeric segment display.
 */
public class SegmentDisplayActivity extends Activity {
    private static final String TAG = SegmentDisplayActivity.class.getSimpleName();

    private NumericDisplay mSegmentDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting SegmentDisplayActivity");
        try {
            mSegmentDisplay = new NumericDisplay(
                    BoardDefaults.getGPIOforData(),
                    BoardDefaults.getGPIOforClock());
            mSegmentDisplay.setBrightness(1.0f);
            mSegmentDisplay.setColonEnabled(true);
            mSegmentDisplay.display("2342");
        } catch (IOException e) {
            Log.e(TAG, "Error configuring display", e);
        }
    }

    @Override
    protected void onDestroy() {
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
