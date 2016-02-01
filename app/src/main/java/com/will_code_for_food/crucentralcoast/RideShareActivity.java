package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.view.other.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.other.RideCardFactory;

/**
 * Created by mallika on 1/14/16.
 */
public class RideShareActivity extends MainActivity {

    private static Event event;
    private static Ride ride;

    public static void setRide(Ride newRide) {
        ride = newRide;
    }

    public static Ride getRide() {
        return ride;
    }

    public static void setEvent(Event newEvent) {
        event = newEvent;
    }

    public static Event getEvent() {
        return event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: load different fragment if user already has a ride
        loadFragmentById(R.layout.fragment_ridesharing_select_action, "Ride Sharing");
    }

    // Takes the user to the make a ride fragment
    public void makeRide(View view) {

    }

    // Takes the user to the list of rides for the event
    //TODO: user first needs to fill out form
    public void viewAvailableRides(View view) {
        Retriever retriever = new SingleRetriever<Ride>(RetrieverSchema.RIDE);
        CardFragmentFactory factory = new RideCardFactory();
        //TODO: callback task for selecting a ride (currently null)
        new RetrievalTask<Ride>(retriever, factory, null,
                R.id.list_rides, R.string.toast_no_rides).execute();
    }

    // Shows the user their rides
    public void viewMyRides(View view) {

    }
}