package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

/**
 * Created by mallika on 1/14/16.
 */
public class RideShareActivity extends EventsActivity {

    private static Ride ride;

    public static void setRide(Ride newRide) {
        ride = newRide;
    }

    public static Ride getRide() {
        return ride;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.title = "RS Events";
        super.onCreate(savedInstanceState);
    }
}