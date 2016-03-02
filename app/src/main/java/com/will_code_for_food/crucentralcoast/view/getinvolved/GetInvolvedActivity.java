package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by mallika on 1/14/16.
 */
public class GetInvolvedActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_get_involved, "Get Involved", new GetInvolvedFragment(), this);
    }

    public void viewMinistryTeams(View view){
        loadFragmentById(R.layout.fragment_card_list, "Get Involved", new MinistryTeamFragment(), this);
    }

    public void viewJoinCommunityGroup(View view){

    }

    public void joinMinistryTeam(View view){

    }
}
