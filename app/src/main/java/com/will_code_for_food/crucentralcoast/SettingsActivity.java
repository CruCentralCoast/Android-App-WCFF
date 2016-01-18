package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.view.fragments.PrefsFragment;

/**
 * Created by mallika on 1/14/16.
 */
public class SettingsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(R.id.content_frame,
                new PrefsFragment()).commit();
    }
}
