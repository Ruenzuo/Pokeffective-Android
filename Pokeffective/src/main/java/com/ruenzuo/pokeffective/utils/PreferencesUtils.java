package com.ruenzuo.pokeffective.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ruenzuo on 21/04/14.
 */
public class PreferencesUtils {

    private static final String POKEFFECTIVE_PREFERENCES = "POKEFFECTIVE_PREFERENCES";
    private static final String UNLIMITED_BOX_STORAGE = "UNLIMITED_BOX_STORAGE";

    public static void enableUnlimitedBoxStorage(boolean enable, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(POKEFFECTIVE_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(UNLIMITED_BOX_STORAGE, enable).commit();
    }

    public static boolean isUnlimitedBoxStorageEnabled(Context context) {
        return context.getSharedPreferences(POKEFFECTIVE_PREFERENCES, Context.MODE_PRIVATE).getBoolean(UNLIMITED_BOX_STORAGE, false);
    }

}
