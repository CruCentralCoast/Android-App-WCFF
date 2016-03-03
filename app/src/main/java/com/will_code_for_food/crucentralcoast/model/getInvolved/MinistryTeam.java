package com.will_code_for_food.crucentralcoast.model.getInvolved;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.messaging.Notifier;

/**
 * Created by MasonJStevenson on 11/16/2015.
 */
public class MinistryTeam extends DatabaseObject{

    public MinistryTeam(JsonObject obj) {
        super(obj);
    }

    public void signup(String name, String number, String email){
        String message = String.format(
                Util.getString(R.string.ministry_team_signup_message), name, getName(), number, email);
        Notifier.textUser(Util.getString(R.string.phone_number), message);
    }
}
