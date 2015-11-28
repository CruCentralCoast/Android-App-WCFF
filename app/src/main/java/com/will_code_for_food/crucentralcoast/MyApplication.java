package com.will_code_for_food.crucentralcoast;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

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
    }
}
