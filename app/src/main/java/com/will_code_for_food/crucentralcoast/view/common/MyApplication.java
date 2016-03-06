package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.squareup.leakcanary.LeakCanary;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Brian on 11/27/2015.
 */
public class MyApplication extends MultiDexApplication {

    private static Context appContext;

    @Override
    public void onCreate() {

        appContext = this;

        Parse.initialize(this, getResources().getString(R.string.parseAppId),
                getResources().getString(R.string.parseClientKey));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        super.onCreate();

        //Unfortunately, LeakCanary does not work properly with the emulator.
        //If you want to check for leaks, uncomment the following line and test on a physical device.
        LeakCanary.install(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("FreigSanProLig.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public static Context getContext() {

        if (appContext == null) {
            Log.e("MyApplication", "application context was null");
        }

        return appContext;
    }
}
