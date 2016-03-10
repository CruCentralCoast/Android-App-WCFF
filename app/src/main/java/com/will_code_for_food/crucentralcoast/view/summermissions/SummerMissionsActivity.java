package com.will_code_for_food.crucentralcoast.view.summermissions;

import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by mallika on 1/14/16.
 */
public class SummerMissionsActivity extends MainActivity {

    private static SummerMission mission = null;

    public static void setMission(final SummerMission newMission) {
        mission = newMission;
    }

    public static SummerMission getMission() {
        return mission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFragmentById(R.layout.fragment_card_list, "Missions", new SummerMissionFragment(), this);
    }
}