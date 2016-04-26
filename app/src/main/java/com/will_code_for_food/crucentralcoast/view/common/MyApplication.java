package com.will_code_for_food.crucentralcoast.view.common;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Brian on 11/27/2015.
 *
 * If you are getting any errors in this file, try refreshing your gradle: click on the Gradle tab
 * on the right-hand side of Android Studio, and then select "Refresh all Gradle projects"
 */
public class MyApplication extends MultiDexApplication {

    private static Context appContext;

    private final static int MAJOR_VERSION = 0;
    private final static int MINOR_VERSION = 0;
    private final static int DEFECT_VERSION = 0;
    private final static char RELEASE = 'A';
    private final static int RELEASE_PATCH = 1;

    @Override
    public void onCreate() {
        appContext = this;

        Parse.initialize(this, getResources().getString(R.string.parseAppId),
                getResources().getString(R.string.parseClientKey));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        super.onCreate();

        //Unfortunately, LeakCanary does not work properly with the emulator.
        //If you want to check for leaks, uncomment the following line and test on a physical device.
        //LeakCanary.install(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("FreigSanProLig.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public static Context getContext() {

        if (appContext == null) {
            Logger.e("MyApplication", "application context was null");
        }

        return appContext;
    }

    public static String getVersion() {
        return "v" + MAJOR_VERSION + '.' + MINOR_VERSION + '.' + DEFECT_VERSION
                + '.' + RELEASE + RELEASE_PATCH;
    }
}
