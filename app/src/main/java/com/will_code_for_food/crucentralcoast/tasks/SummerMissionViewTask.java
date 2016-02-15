package com.will_code_for_food.crucentralcoast.tasks;

import android.graphics.Point;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.values.UI;

/**
 * Created by Kayla on 1/29/2016.
 */
public class SummerMissionViewTask extends AsyncTask<SummerMission, Void, Void> {
    SummerMissionsActivity currentActivity;
    SummerMission mission;

    public SummerMissionViewTask() {
        currentActivity = (SummerMissionsActivity) SummerMissionsActivity.context;
    }

    @Override
    protected Void doInBackground(SummerMission... params) {
        mission = params[0];
        SummerMissionsActivity.setMission(mission);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Setup UI elements
        ImageView header = (ImageView) currentActivity.findViewById(R.id.image_mission);
        if (mission.getImage() != null && mission.getImage() != "") {
            Picasso.with(currentActivity).load(mission.getImage()).fit().into(header);
        }

        TextView date = (TextView) currentActivity.findViewById(R.id.text_mission_date);
        date.setText(mission.getMissionFullDate());

        TextView cost = (TextView) currentActivity.findViewById(R.id.text_mission_cost);
        cost.setText("Cost: $" + mission.getCost());

        TextView description = (TextView) currentActivity.findViewById(R.id.text_mission_description);
        description.setText(mission.getDescription());
    }
}
