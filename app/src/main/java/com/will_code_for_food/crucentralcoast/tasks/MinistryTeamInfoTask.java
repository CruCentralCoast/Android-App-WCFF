package com.will_code_for_food.crucentralcoast.tasks;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.view.getinvolved.GetInvolvedActivity;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

/**
 * Created by Brian on 2/19/2016.
 */
public class MinistryTeamInfoTask extends AsyncTask<MinistryTeam, Void, Void> {
    GetInvolvedActivity currentActivity;
    MinistryTeam ministryTeam;

    public MinistryTeamInfoTask() {
        currentActivity = (GetInvolvedActivity) SummerMissionsActivity.context;
    }

    @Override
    protected Void doInBackground(MinistryTeam... params) {
        ministryTeam = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Setup UI elements
        ImageView header = (ImageView) currentActivity.findViewById(R.id.image_ministry_team);
        if (ministryTeam.getImage() != null && ministryTeam.getImage() != "") {
            Picasso.with(currentActivity).load(ministryTeam.getImage()).fit().into(header);
        }

        TextView cost = (TextView) currentActivity.findViewById(R.id.text_ministry_team_name);
        cost.setText(ministryTeam.getName());

        TextView description = (TextView) currentActivity.findViewById(R.id.text_ministry_team_description);
        description.setText(ministryTeam.getDescription());
    }
}
