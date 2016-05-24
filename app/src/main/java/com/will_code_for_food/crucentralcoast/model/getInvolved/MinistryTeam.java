package com.will_code_for_food.crucentralcoast.model.getInvolved;

import android.content.res.Resources;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.JsonDatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.messaging.Notifier;
import com.will_code_for_food.crucentralcoast.model.common.messaging.SMSNotifier;

/**
 * A simple object representing a minsitry team, with
 * the functionality to sign up
 */
public class MinistryTeam extends JsonDatabaseObject {

    public MinistryTeam(JsonObject obj) {
        super(obj);
    }

    /**
     * Signs the user up for the ministry team
     */
    public boolean signup(final String name, final String number,
                       final String email, final Notifier notifier) {
        String message = String.format(
                Util.getString(R.string.ministry_team_signup_message), name, getName(), number, email);
        return notifier.notify(message);
    }
}
