package com.google.samples.grove.app;

import com.google.brillo.driver.grove.lcdrgb.LcdRgbBacklight;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

/**
 * LcdRgbBacklightDemo is a sample activity that uses the driver for the Grove LCD Backlight
 * to display a text message with a colored background.
 */
public class LcdRgbBacklightDemo extends Activity {
    private static final String TAG = LcdRgbBacklightDemo.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "LCD RGB Backlight demo created");

        try (LcdRgbBacklight lcd = new LcdRgbBacklight(BoardDefaults.getI2CPort())) {
            lcd.clear();
            lcd.setBackground(Color.MAGENTA);
            lcd.write("THINGS");
        } catch (IOException|IllegalStateException e) {
            Log.e(TAG, "driver error: ", e);
        }
    }

    @Override
    public void onDestroy() {
    }
}
