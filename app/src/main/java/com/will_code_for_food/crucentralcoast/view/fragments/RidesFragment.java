package com.will_code_for_food.crucentralcoast.view.fragments;

import android.app.Activity;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.EventsActivity;
import com.will_code_for_food.crucentralcoast.R;
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
 * Created by Kayla on 2/1/2016.
 */
public class RidesFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        viewAvailableRides();

        return fragmentView;
    }

    // Takes the user to the list of rides for the event
    //TODO: user first needs to fill out form
    public void viewAvailableRides() {

        new SetEventHeader().execute();

        Retriever retriever = new SingleRetriever<Ride>(RetrieverSchema.RIDE);
        CardFragmentFactory factory = new RideCardFactory();
        //TODO: callback task for selecting a ride (currently null)
        new RetrievalTask<Ride>(retriever, factory, null,
                R.id.list_rides, R.string.toast_no_rides).execute();
    }

    // Sets up and loads the image for the event into the image header
    private class SetEventHeader extends AsyncTask<Void, Void, Void> {

        Activity parent;
        Event event;

        @Override
        protected Void doInBackground(Void... params) {
            parent = (Activity) getParent();
            event = EventsActivity.getEvent();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ImageView imageView = (ImageView) parent.findViewById(R.id.image_ride_event);
            Point scaledSize = Util.scaledImageSize(parent, UI.IMAGE_HEADER_LENGTH_RATIO, UI.IMAGE_HEADER_HEIGHT_RATIO);
            if (event != null && event.getImage() != null && event.getImage() != "") {
                Picasso.with(parent).load(event.getImage()).resize(scaledSize.x, scaledSize.y)
                        .onlyScaleDown().placeholder(R.drawable.transparent).into(imageView);
            } else {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(scaledSize.x, scaledSize.y);
                imageView.setLayoutParams(layoutParams);
            }
        }
    }
}
