package com.ruenzuo.pokeffective.utils;

import android.graphics.Color;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class ColorUtils {

    public static int darkerColor(int color)
    {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

}
