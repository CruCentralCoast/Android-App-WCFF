package com.will_code_for_food.crucentralcoast.view.common;

import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.common.PrefsFragment;

/**
 * Allows the user to change their settings, such as Ministry, Campus, and notifications.
 */
public class SettingsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        loadFragmentById(R.layout.fragment_settings, "Settings", new PrefsFragment(), this);
    }
}