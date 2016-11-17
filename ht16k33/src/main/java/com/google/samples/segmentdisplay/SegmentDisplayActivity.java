package com.google.samples.segmentdisplay;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.brillo.driver.ht16k33.AlphanumericDisplay;

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
