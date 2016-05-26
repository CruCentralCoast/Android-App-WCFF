package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.Date;

/**
 * Created by Kayla on 1/31/2016.
 */
public class RideCardFactory implements CardFragmentFactory {
    private Event event;
    private MainActivity parent;

    public RideCardFactory(MainActivity parent) {
        this.parent = parent;
        event = EventsActivity.getEvent();
    }

    @Override
    public boolean include(DatabaseObject object) {
        Ride ride = (Ride) object;

        // Filter for the event that was chosen
        //TODO: filter based on time leaving, 1/2-way, etc.
        // TODO use the RideSorter class!

        return event != null && event.getId().equals(ride.getEventId()) && (!ride.isFullFromEvent() || !ride.isFullToEvent())
                && ride.getDate().after(new Date());
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new RideAdapter(parent,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity, final Content myDBObjects) {
        return new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride ride = (Ride) myDBObjects.get(position);
                RideShareActivity.setRide(ride);
                currentActivity.loadFragmentById(R.layout.fragment_ride_info, ride.getDriverName() + "'s Ride", new RideInfoFragment(), currentActivity);
            }
        };
    }
}