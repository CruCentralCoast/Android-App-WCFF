package com.will_code_for_food.crucentralcoast.view.summermissions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

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

    // Opens the mission's website application in browser
    public void applyToMission(View view) {
        if (mission != null) {
            String url = mission.getWebsiteUrl();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }
}