package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.view.fragments.PrefsFragment;

import com.will_code_for_food.crucentralcoast.model.common.common.Util;

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