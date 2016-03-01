package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.will_code_for_food.crucentralcoast.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Brian on 11/27/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        Parse.initialize(this, getResources().getString(R.string.parseAppId),
                getResources().getString(R.string.parseClientKey));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("FreigSanProLig.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
