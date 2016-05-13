package com.will_code_for_food.crucentralcoast.model.common.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.common.MyApplication;
import com.will_code_for_food.crucentralcoast.view.common.SplashscreenActivity;
import com.will_code_for_food.crucentralcoast.values.Android;

import java.util.HashSet;
import java.util.Set;

import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;

/**
 * General utility methods
 */
public class Util {

    /**
     * Gets a string resource using the given context
     */
    public static String getString(final Context ctxt, final int resId) {
        return ctxt.getString(resId);
    }

    /**
     * Gets a string resource without a context
     */
    public static String getString(final int resId) {
        try {
            return Resources.getSystem().getString(resId);
        } catch (Exception ex) {
            return getString(MainActivity.context, resId);
        }
    }

    /**
     * Gets an int resource using MainActivity.java's context.
     */
    public static int getInt(final int resId) {
        return Integer.parseInt(getString(resId));
    }

    /**
     * Gets a double resource using MainActivity.java's context.
     */
    public static double getDouble(final int resId) {
        return Double.parseDouble(getString(resId));
    }

    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * Saves a string with the given key to the shared preferences file
     */
    public static void saveString(String key, String toSave) {
        Context context = MyApplication.getContext();
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, toSave);
        editor.commit();
    }

    public static void saveBool(String key, Boolean toSave) {
        Context context = MyApplication.getContext();
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, toSave);
        editor.commit();
    }


    // Saves the string to the set in the given key
    public static void saveToSet(String key, String toSave) {
        Context context = MyApplication.getContext();
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

    // Saves the string to the set in the given key
    public static void saveToSet(String key, Set<String> toSave) {
        for (String str : toSave) {
            saveToSet(key, str);
        }
    }

    // Clears a set
    public static void clearSet(final String key) {
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
        Context context = MyApplication.getContext();
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, null);

        return value;
    }

    public static Boolean loadBool(String key) {
        Context context = MyApplication.getContext();
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Boolean value = sharedPref.getBoolean(key, false);

        return value;
    }

    /**
     * Loads a string set from the shared preferences file, or an empty HashSet if none exists
     */
    public static Set<String> loadStringSet(String key) {
        Context context = MyApplication.getContext();
        String preferences_file = Android.PREFS_FILE;
        SharedPreferences sharedPref = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
        Set<String> value = sharedPref.getStringSet(key, null);

        if (value == null) {
            value = new HashSet<String>();
        }

        return value;
    }

    /**
     * Prints a set to the console (debugging purposes)
     */
    public static void printSet(final String key) {
        Set<String> set = Util.loadStringSet(key);
        Logger.i("*********", "**************************");
        if (set == null) {
            Logger.i("   ", "No set");
        } else {
            for (String s : set) {
                Logger.i("     ", s);
            }
        }
        Logger.i("*********", "**************************");
    }

    /**
     * Gets the scaled size for an image, dependent on the android screen size
     * @param widthRatio percent of screen width the image takes (1.0 is max)
     * @param heightRatio percent of screen height the image takes (1.0 is max)
     */
    public static Point scaledImageSize(Activity currentActivity, double widthRatio, double heightRatio) {
        // Get the screen size
        Display display = currentActivity.getWindowManager().getDefaultDisplay();
        Point windowSize = new Point();
        display.getSize(windowSize);
        int width_screen = windowSize.x;
        int height_screen = windowSize.y;

        // Scale image based on screen size
        int newWidth = (int)(width_screen * widthRatio);
        int newHeight = (int)(height_screen * heightRatio);

        return new Point(newWidth, newHeight);
    }

    public static String getPhoneNum() {
        String phoneNum;

        try {
            //get this phone number
            TelephonyManager tMgr = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            phoneNum = tMgr.getLine1Number();
        } catch (java.lang.SecurityException e) {
            //triggered if emulator is in use
            phoneNum = "EMULATOR";
        }

        return phoneNum;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}