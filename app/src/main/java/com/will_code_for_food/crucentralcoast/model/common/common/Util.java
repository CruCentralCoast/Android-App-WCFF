package com.will_code_for_food.crucentralcoast.model.common.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;

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
    public static void saveString(String key, String toSave) {
        Context context = MainActivity.context;
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, toSave);
        editor.commit();
    }

    // Saves the string to the set in the given key
    public static void saveToSet(String key, String toSave) {
        Context context = MainActivity.context;
        String preferences_file = Util.getString(R.string.preferences_file);
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

    // Saves the string to the set in the given key
    public static void saveToSet(String key, Set<String> toSave) {
        for (String str : toSave) {
            saveToSet(key, str);
        }
    }

    // Clears a set
    public static void clearSet(String key) {
        Context context = MainActivity.context;
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key, new HashSet<String>());
        editor.commit();
    }

    /**
     * Loads a string from the shared preferences file, or null if none exist
     */
    public static String loadString(String key) {
        Context context = MainActivity.context;
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, null);

        return value;
    }

    /**
     * Loads a string set from the shared preferences file, or null if none exists
     */
    public static Set<String> loadStringSet(String key) {
        Context context = MainActivity.context;
        String preferences_file = Util.getString(R.string.preferences_file);
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Set<String> value = sharedPref.getStringSet(key, null);

        return value;
    }

    /**
     * Prints a set to the console (debugging purposes)
     */
    public static void printSet(final String key) {
        Set<String> set = Util.loadStringSet(key);
        Log.i("*********", "**************************");
        if (set == null) {
            Log.i("   ", "No set");
        } else {
            for (String s : set) {
                Log.i("     ", s);
            }
        }
        Log.i("*********", "**************************");
    }
}