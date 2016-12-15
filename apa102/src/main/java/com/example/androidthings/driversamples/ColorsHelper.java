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

import android.graphics.Color;

import java.util.Arrays;

/**
 * Helper class for managing an array of integers representing ARGB 32-bits color points as
 * defined in {@link Color}.
 */
public class ColorsHelper {
    private static final String TAG = "ColorsHelper";

    /**
     * Sets all the elements in the color array to a given {@link Color}.
     *
     * @param colorArray array to be set
     * @param color an integer representing an ARGB 32-bits color point
     */
    public static void setAllToColor(int[] colorArray, int color) {
        Arrays.fill(colorArray, color);
    }

    /**
     * Clear a color array by setting all color points to black.
     *
     * @param colorArray The {@link Color} array to be cleared
     */
    public static void clear(int[] colorArray) {
        setAllToColor(colorArray, Color.BLACK);
    }

    /**
     * Generate a color that represents a point in a cyclical rainbow gradient.
     *
     * @param value An input value, typically a counter, that walks through the color gradient. Note
     *              that the value can be any integer and does not need to be within any particular
     *              range because adjacent colors will exhibit the properties of the gradient.
     */
    public static int getRainbowColor(int value) {
        final double gScale = .2; // Gradient scale, smaller value => longer gradient
        final int minColor = 128; // Component of color that is fixed, lower values => more vibrance
        final int sinColor = 255 - minColor; // Component of color that changes

        // Math.sin - Angle in RADS
        int r = (int) (Math.sin(gScale * value + 0) * sinColor) + minColor;
        int g = (int) (Math.sin(gScale * value + 2) * sinColor) + minColor;
        int b = (int) (Math.sin(gScale * value + 4) * sinColor) + minColor;

        return Color.rgb(r, g, b);
    }
}
