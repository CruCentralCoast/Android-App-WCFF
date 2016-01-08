package com.will_code_for_food.crucentralcoast.model.common.common;

import com.will_code_for_food.crucentralcoast.MainActivity;

/**
 * Created by MasonJStevenson on 1/8/2016.
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
}
