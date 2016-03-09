package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by Kayla on 3/8/2016.
 */
public class MinistryTeamInfoFragment extends CruFragment {
    private Button applyButton;
    private MinistryTeam ministryTeam = GetInvolvedActivity.getMinistryTeam();

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
                joinMinistryTeam();
            }
        });
    }

    private void populateView(View fragmentView) {
        ImageView header = (ImageView) fragmentView.findViewById(R.id.image_ministry_team);
        if (ministryTeam.getImage() != null && ministryTeam.getImage() != "") {
            Picasso.with(getParent()).load(ministryTeam.getImage()).fit().into(header);
        }

        TextView name = (TextView) fragmentView.findViewById(R.id.text_ministry_team_name);
        name.setText(ministryTeam.getName());

        TextView description = (TextView) fragmentView.findViewById(R.id.text_ministry_team_description);
        description.setText(ministryTeam.getDescription());
    }

    private void joinMinistryTeam(){
        MinistryTeamSignupDialog popup = MinistryTeamSignupDialog.newInstance(ministryTeam);
        FragmentManager manager = getFragmentManager();
        popup.show(manager, "ministry_team_signup");
    }
}