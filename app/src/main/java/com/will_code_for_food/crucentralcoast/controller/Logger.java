package com.will_code_for_food.crucentralcoast.controller;

import android.util.Log;

import java.util.Calendar;
import java.util.List;

/**
 * A wrapper class for the standard Android Log.
 * Saves a session's logs to a local file.
 */
public class Logger {
    private static final String SESSION_LOGS_FNAME = "session-log-history";

    public static void i(final String tag, final String msg) {
        Log.i(tag, msg);
        appendToLog("I/ " + tag.toUpperCase(), msg);
    }

    public static void e(final String tag, final String msg) {
        Log.e(tag, msg);
        appendToLog("E !!! / " + tag.toUpperCase(), msg);
    }

    public static void w(final String tag, final String msg) {
        Log.w(tag, msg);
        appendToLog("W/ " + tag.toUpperCase(), msg);
    }

    public static void d(final String tag, final String msg) {
        Log.d(tag, msg);
        appendToLog("D/ " + tag.toUpperCase(), msg);
    }

    public static void d(final String tag, final String msg, final Exception ex) {
        Log.d(tag, msg, ex);
        appendToLog("D/ " + tag.toUpperCase(), msg + ex);
    }

    private static void appendToLog(final String tag, final String msg) {
        LocalStorageIO.appendToList(
                Calendar.getInstance().getTime().toString() + " | " + tag + " : " + msg,
                SESSION_LOGS_FNAME);
    }

    public static void initialize() {
        LocalStorageIO.deleteFile(SESSION_LOGS_FNAME);
        LocalStorageIO.writeSingleLineFile(SESSION_LOGS_FNAME,
                "LOG SESSION: " + Calendar.getInstance().getTime());
    }

    public static List<String> getSessionLogs() {
        return LocalStorageIO.readList(SESSION_LOGS_FNAME);
    }
}
