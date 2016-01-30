package com.will_code_for_food.crucentralcoast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;

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

        loadFragmentById(R.layout.fragment_summermissions_list, "Missions");
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