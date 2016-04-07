package com.will_code_for_food.crucentralcoast.controller.crash_reports;

import android.app.Activity;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by Gavin on 4/7/2016.
 */
public class CrashReportExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Activity act = null;

    public void setActivity(Activity act) {
        if (this.act == null && act != null) {
            this.act = act;
        }
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e("Alert", "Lets See if it Works !!!");
        ((MainActivity) act).sendCrashReport(throwable);
    }
}
