package com.will_code_for_food.crucentralcoast.model.ridesharing;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.tasks.ParameterizedTask;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * An Asyncronous task that sends a new ride to the database
 * and returns a copy of it with the database ID in place
 */
public class SendRideToDBTask extends ParameterizedTask {
        @Override
        protected Ride doInBackground(Void... params) {
            Ride ride = null;
            try {
                ride = new Ride(RestUtil.create(((Ride) getExtra(0)).toJSON(), Database.REST_RIDE));
            } catch (NullPointerException ex) {
                Logger.e("Send Ride to DB", "No ride object given to task as an extra");
            }
            return ride;
        }
}
