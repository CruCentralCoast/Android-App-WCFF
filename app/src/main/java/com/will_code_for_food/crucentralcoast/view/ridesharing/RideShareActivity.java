package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.Bundle;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.ArrayList;

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