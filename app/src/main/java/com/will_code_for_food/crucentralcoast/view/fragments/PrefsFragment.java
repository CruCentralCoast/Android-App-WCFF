package com.will_code_for_food.crucentralcoast.view.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.will_code_for_food.crucentralcoast.R;

/**
 * Created by Brian on 1/17/2016.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
