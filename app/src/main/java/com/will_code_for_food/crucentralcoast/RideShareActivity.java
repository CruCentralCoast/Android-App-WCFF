package com.will_code_for_food.crucentralcoast;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.UI;
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

        //TODO: load "My Rides" fragment if user already has a ride

        // Loads the "I can drive!/Need a ride?" layout
        loadFragmentById(R.layout.fragment_ridesharing_select_action, "Ride Sharing");
        new SetEventHeader().execute();
    }

    // Takes the user to the make a ride fragment
    public void makeRide(View view) {

    }

    // Takes the user to the list of rides for the event
    //TODO: user first needs to fill out form
    public void viewAvailableRides(View view) {
        loadFragmentById(R.layout.fragment_rideslist, "Rides");
        new SetEventHeader().execute();

        Retriever retriever = new SingleRetriever<Ride>(RetrieverSchema.RIDE);
        CardFragmentFactory factory = new RideCardFactory();
        //TODO: callback task for selecting a ride (currently null)
        new RetrievalTask<Ride>(retriever, factory, null,
                R.id.list_rides, R.string.toast_no_rides).execute();
    }

    // Shows the user their rides
    public void viewMyRides(View view) {

    }

    // Sets up and loads the image for the event into the image header
    private class SetEventHeader extends AsyncTask<Void, Void, Void> {
        RideShareActivity curActivity;

        @Override
        protected Void doInBackground(Void... params) {
            curActivity = (RideShareActivity) RideShareActivity.context;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ImageView imageView = (ImageView) curActivity.findViewById(R.id.image_ride_event);
            Point scaledSize = Util.scaledImageSize(curActivity, UI.IMAGE_HEADER_LENGTH_RATIO, UI.IMAGE_HEADER_HEIGHT_RATIO);
            if (event != null && event.getImage() != null && event.getImage() != "") {
                Picasso.with(curActivity).load(event.getImage()).resize(scaledSize.x, scaledSize.y)
                        .onlyScaleDown().placeholder(R.drawable.transparent).into(imageView);
            } else {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(scaledSize.x, scaledSize.y);
                imageView.setLayoutParams(layoutParams);
            }
        }
    }
}