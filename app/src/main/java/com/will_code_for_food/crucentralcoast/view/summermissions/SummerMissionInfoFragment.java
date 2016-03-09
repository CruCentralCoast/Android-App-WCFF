package com.will_code_for_food.crucentralcoast.view.summermissions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by Kayla on 3/8/2016.
 */
public class SummerMissionInfoFragment extends CruFragment {
    private Button applyButton;
    private SummerMission mission = SummerMissionsActivity.getMission();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        initComponents(fragmentView);
        populateView(fragmentView);

        return fragmentView;
    }

    private void initComponents(final View fragmentView) {
        applyButton = (Button) fragmentView.findViewById(R.id.button_apply_mission);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyToMission();
            }
        });
    }

    private void populateView(View fragmentView) {
        ImageView header = (ImageView) fragmentView.findViewById(R.id.image_mission);
        if (mission.getImage() != null && mission.getImage() != "") {
            Picasso.with(getParent()).load(mission.getImage()).fit().into(header);
        }

        TextView date = (TextView) fragmentView.findViewById(R.id.text_mission_date);
        date.setText(mission.getMissionFullDate());

        TextView cost = (TextView) fragmentView.findViewById(R.id.text_mission_cost);
        cost.setText("Cost: $" + mission.getCost());

        TextView description = (TextView) fragmentView.findViewById(R.id.text_mission_description);
        description.setText(mission.getDescription());
    }

    // Opens the mission's website application in browser
    private void applyToMission() {
        if (mission != null) {
            String url = mission.getWebsiteUrl();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }
}