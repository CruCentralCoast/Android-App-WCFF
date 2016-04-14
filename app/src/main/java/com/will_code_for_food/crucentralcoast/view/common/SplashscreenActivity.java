package com.will_code_for_food.crucentralcoast.view.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.messaging.RegistrationIntentService;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by MasonJStevenson on 1/19/2016.
 * <p/>
 * Controls the splashscreen the user sees when the app starts.
 */
public class SplashscreenActivity extends Activity {

    private FrameLayout screen;

    //if you need to request a permission on startup, add it here
    private static final String[] permissions = {Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.initialize();
        setContentView(R.layout.activity_splashscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DBObjectLoader.loadAll();

        screen = (FrameLayout) findViewById(R.id.splashscreen_content);

        if (Build.VERSION.SDK_INT >= Android.API_LEVEL_MARSHMALLOW) {
            requestPermissions();
        }

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

        loadContent();
    }

    /**
     * Starting with api level 23 (Marshmallow), you have to request some permissions from the user.
     */
    private void requestPermissions() {

        for (int count = 0; count < permissions.length; count++) {
            if (ContextCompat.checkSelfPermission(this, permissions[count]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permissions[count]}, 0);
            }
        }
    }

    /**
     * Checks to see if all startup permissions have been granted
     */
    private boolean checkAllPermissions() {

        for (int count = 0; count < permissions.length; count++) {
            if (ContextCompat.checkSelfPermission(this, permissions[count]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Defines behavior of splashscreen
     */
    private void loadContent() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (checkAllPermissions() && DBObjectLoader.finishedLoading()) {
                    //Finish splash activity so user cant go back to it.
                    finish();

                    //Apply splash exit (fade out) and main entry (fade in) animation transitions.
                    overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);

                    launchApp();
                } else {
                    loadContent();
                }
            }
        };

        //makes the pause on this screen skippable.
        //you aren't allowed to skip the splashscreen on the initial setup, because it crashes the app.
        if (Util.loadBool(Android.PREF_SETUP_COMPLETE)) {
            screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.removeCallbacks(runnable);

                    finish();
                    launchApp();
                }
            });
        }

        handler.postDelayed(runnable, UI.SETUP_SPLASHSCREEN_POLL_DURATION);
    }

    /**
     * Launches either the initial setup or the main page depending on if the user has previously completed the initial setup.
     */
    private void launchApp() {
        Logger.i("Launching App", "Starting MainActivity");
        if (!Util.loadBool(Android.PREF_SETUP_COMPLETE)) {
            Intent intent = new Intent(this, SetupCampusActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
