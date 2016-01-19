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
        getFragmentManager().beginTransaction().replace(R.id.content_frame,
                new PrefsFragment()).commit();
    }

    public void testSave(View view) {
        EditText editText = (EditText) findViewById(R.id.text_ministry);
        String text = editText.getText().toString();
        Util.saveString("ministry", text, this);
    }

    public void testLoad(View view) {
        EditText editText = (EditText) findViewById(R.id.text_ministry);
        String ministry = Util.loadString("ministry", this);
        editText.setText(ministry);
    }
}