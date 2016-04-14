package com.will_code_for_food.crucentralcoast.controller.crash_reports;

import android.app.Activity;
import android.util.Log;

/**
 * Created by Gavin on 4/7/2016.
 */
public class CrashReportExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Activity act = null;
    private Thread.UncaughtExceptionHandler oldHandler = null;

    public void setActivityAndHandler(Activity act, Thread.UncaughtExceptionHandler oldHandler) {
        if (this.act == null && act != null && oldHandler != null && this.oldHandler == null) {
            this.act = act;
            this.oldHandler = oldHandler;
        }
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        // these should stay as Log, not Logger
        Log.e("Crash Report", "App crash detected");
        CrashReport report = new CrashReport(throwable, null);
        report.cache();
        Log.e("Crash Report", "Report cached");
        oldHandler.uncaughtException(thread, throwable);
    }
}
