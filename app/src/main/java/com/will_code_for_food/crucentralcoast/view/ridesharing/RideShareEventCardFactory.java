package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.view.View;
import android.widget.AdapterView;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.events.EventCardFactory;

import java.util.List;

/**
 * Created by MasonJStevenson on 2/2/2016.
 */
public class RideShareEventCardFactory extends EventCardFactory {
    @Override
    public boolean include(Event object) {
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

    @Override
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final Content<Event> myDBObjects) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = (Event) myDBObjects.getObjects().get(position);
                EventsActivity.setEvent(selectedEvent);
                currentActivity.loadFragmentById(R.layout.fragment_ridesharing_select_action,
                        selectedEvent.getName() + " > " + RideShareSelectActionFragment.TITLE,
                        new RideShareSelectActionFragment(), currentActivity);
            }
        };
    }
}
