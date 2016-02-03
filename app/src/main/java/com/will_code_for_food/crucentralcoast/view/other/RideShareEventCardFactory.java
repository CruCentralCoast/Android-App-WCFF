package com.will_code_for_food.crucentralcoast.view.other;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Created by MasonJStevenson on 2/2/2016.
 */
public class RideShareEventCardFactory extends EventCardFactory {
    @Override
    public boolean include(DatabaseObject object) {
        JsonElement ministriesObject = object.getField(Database.JSON_KEY_EVENT_MINISTRIES);

        //Go through all ministries for the event and see if the user is subscribed
        for (JsonElement objectMinistry : ministriesObject.getAsJsonArray()) {
            if (getMyMinistries().contains(objectMinistry.getAsString())) {
                Event event = null;
                if ((object instanceof Event) && ((Event) object).hasRideSharing()) {
                    return true;
                }
            }
        }

        //Return false if my ministries are not found
        return false;
    }
}
