package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.tasks.BackgroundSound;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;

/**
 * Created by MasonJStevenson on 1/19/2016.
 */
public class SplashscreenActivity extends Activity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        context = this;

        run();
    }

    private void run() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Finish splash activity so user cant go back to it.
                SplashscreenActivity.this.finish();

                //Apply splash exit (fade out) and main entry (fade in) animation transitions.
                overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);

                launchApp();
            }
        }, UI.SETUP_SPLASHSCREEN_WAIT_DURATION);
    }

    private void launchApp() {
        if (!Util.loadBool(Android.PREF_SETUP_COMPLETE)) {
            Intent intent = new Intent(this, SetupCampusActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
