package com.will_code_for_food.crucentralcoast.model.common.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.SplashscreenActivity;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.HashSet;
import java.util.Set;

/**
 * General utility methods
 */
public class Util {

    /**
     * Gets a string resource using MainActivity.java's context.
     */
    public static String getString(int resId) {
        return SplashscreenActivity.context.getString(resId);
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
    public static void saveString(String key, String toSave) {
        Context context = SplashscreenActivity.context;
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, toSave);
        editor.commit();
    }

    public static void saveBool(String key, Boolean toSave) {
        Context context = SplashscreenActivity.context;
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, toSave);
        editor.commit();
    }


    // Saves the string to the set in the given key
    public static void saveToSet(String key, String toSave) {
        Context context = SplashscreenActivity.context;
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Set<String> newSet = sharedPref.getStringSet(key, null);

        // Create a new set if a set does not currently exist
        if (newSet == null) {
            newSet = new HashSet<String>();
        }
        newSet.add(toSave);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key, newSet);
        editor.commit();
    }

    /**
     * Loads a string from the shared preferences file, or null if none exist
     */
    public static String loadString(String key) {
        Context context = SplashscreenActivity.context;
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, null);

        return value;
    }

    public static Boolean loadBool(String key) {
        Context context = SplashscreenActivity.context;
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Boolean value = sharedPref.getBoolean(key, false);

        return value;
    }

    /**
     * Loads a string set from the shared preferences file, or null if none exists
     */
    public static Set<String> loadStringSet(String key) {
        Context context = SplashscreenActivity.context;
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Set<String> value = sharedPref.getStringSet(key, null);

        return value;
    }
}