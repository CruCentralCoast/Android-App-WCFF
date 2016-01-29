package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;

/**
 * Created by mallika on 1/14/16.
 */
public class SummerMissionsActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFragmentById(R.layout.fragment_summermissions_list, "Summer Missions");
    }

}
