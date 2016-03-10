package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.ridesharing.EnterNameDialog;

/**
 * Created by mallika on 1/14/16.
 */
public class GetInvolvedActivity extends MainActivity {

    private static String title = "Get Involved";
    private static MinistryTeam team = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_get_involved, title, new GetInvolvedFragment(), this);
    }

    public void viewMinistryTeams(){
        loadFragmentById(R.layout.fragment_card_list, title, new MinistryTeamFragment(), this);
    }

    public void viewJoinCommunityGroup(){

    }

    public static void setMinistryTeam(MinistryTeam team){
        GetInvolvedActivity.team = team;
    }

    public static MinistryTeam getMinistryTeam() {
        return team;
    }

    public void joinMinistryTeam(View view){
        MinistryTeamSignupDialog popup = MinistryTeamSignupDialog.newInstance(team);
        FragmentManager manager = getFragmentManager();
        popup.show(manager, "ministry_team_signup");
    }
}
