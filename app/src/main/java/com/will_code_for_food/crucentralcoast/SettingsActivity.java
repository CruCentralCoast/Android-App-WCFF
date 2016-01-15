package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * Created by mallika on 1/14/16.
 */
public class SettingsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_ministries, "Settings");
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