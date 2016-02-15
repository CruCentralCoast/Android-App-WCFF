package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsFragment;

/**
 * Created by mallika on 1/14/16.
 */
public class RideShareActivity extends MainActivity {

    private static Ride ride;

    public static void setRide(Ride newRide) {
        ride = newRide;
    }

    public static Ride getRide() {
        return ride;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFragmentById(R.layout.fragment_my_rides_list, "My Rides", new MyRidesFragment(), this);
    }
}