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

import java.util.Date;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/2/2016.
 */
public class RideShareEventCardFactory extends EventCardFactory {
    @Override
    public boolean include(Event object) {
        boolean validEvent = super.include(object);
        return validEvent && object.hasRideSharing();
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final Content<Event> myDBObjects) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = (Event) myDBObjects.getObjects().get(position);
                EventsActivity.setEvent(selectedEvent);
                currentActivity.loadFragmentById(R.layout.fragment_ride_list, "Rides", new RidesFragment(), currentActivity);

            }
        };
    }
}
