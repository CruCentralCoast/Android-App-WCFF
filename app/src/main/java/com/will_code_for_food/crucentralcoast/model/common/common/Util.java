package com.will_code_for_food.crucentralcoast.model.common.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;

import java.util.Set;

/**
 * General utility methods
 */
public class Util {

    /**
     * Gets a string resource using MainActivity.java's context.
     */
    public static String getString(int resId) {
        return MainActivity.context.getString(resId);
    }

    /**
     * Gets an int resource using MainActivity.java's context.
     */
    public static int getInt(int resId) {
        return Integer.parseInt(getString(resId));
    }

    /**
     * Gets a double resource using MainActivity.java's context.
     */
    public static double getDouble(int resId) {
        return Double.parseDouble(getString(resId));
    }

    /**
     * Saves a string with the given key to the shared preferences file
     */
    public static void saveString(String key, String toSave, Context context) {
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, toSave);
        editor.commit();
    }

    /**
     * Saves a string set to the shared preferences file
     */
    public static void saveStringSet(String key, Set<String> toSave, Context context) {
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key, toSave);
        editor.commit();
    }

    /**
     * Loads a string from the shared preferences file, or null if none exist
     */
    public static String loadString(String key, Context context) {
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, null);

        return value;
    }

    /**
     * Loads a string set from the shared preferences file, or null if none exists
     */
    public static Set<String> loadStringSet(String key, Context context) {
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Set<String> value = sharedPref.getStringSet(key, null);

        return value;
    }
}
