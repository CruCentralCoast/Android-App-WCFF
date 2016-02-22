package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by mallika on 1/14/16.
 */
public class RideShareActivity extends MainActivity {

    private static ArrayList<Event> events;
    private static Ride ride;

    public static void setRide(Ride newRide) {
        ride = newRide;
    }

    public static Ride getRide() {
        return ride;
    }

    public static Event getEvent(Ride ride) {

        events = DBObjectLoader.getEvents();

        if (events != null) {
            for (Event event : events) {
                if (event.getId().equals(ride.getEventId())) {
                    return event;
                }
            }
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_my_rides_list, "My Rides", new MyRidesFragment(), this);
    }
}