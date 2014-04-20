package com.ruenzuo.pokeffective.utils;

/**
 * Created by ruenzuo on 21/04/14.
 */
public class SDKUtils {

    public static boolean isHigherThanJellyBean() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
    }

}
