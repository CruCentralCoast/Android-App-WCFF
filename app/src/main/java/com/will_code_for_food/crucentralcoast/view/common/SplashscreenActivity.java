package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by MasonJStevenson on 1/19/2016.
 * <p/>
 * Controls the splashscreen the user sees when the app starts.
 */
public class SplashscreenActivity extends Activity {

    //public static Context context;

    private FrameLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.initialize();
        setContentView(R.layout.activity_splashscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //context = this;

        DBObjectLoader.loadAll();

        screen = (FrameLayout) findViewById(R.id.splashscreen_content);
        loadContent();
    }

    /**
     * Defines behavior of splashscreen
     */
    private void loadContent() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (DBObjectLoader.finishedLoading()) {
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
