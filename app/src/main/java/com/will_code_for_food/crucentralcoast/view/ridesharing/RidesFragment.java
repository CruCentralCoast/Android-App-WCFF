package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.Activity;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.UI;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;

/**
 * Created by Kayla on 2/1/2016.
 */
public class RidesFragment extends CruFragment {
    SwipeRefreshLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        viewAvailableRides();
        layout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.rides_layout);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewAvailableRides();
            }
        });
    }

    // Takes the user to the list of rides for the event
    //TODO: user first needs to fill out form
    public void viewAvailableRides() {
        new SetEventHeader().execute();

        Retriever retriever = new SingleRetriever<Ride>(RetrieverSchema.RIDE);
        CardFragmentFactory factory = new RideCardFactory();
        //TODO: callback task for selecting a ride (currently null)
        new RetrievalTask<Ride>(retriever, factory, R.string.toast_no_rides,
                new AsyncResponse(getParent()) {
            @Override
            public void otherProcessing() {
                layout.setRefreshing(false);
            }
        }).execute();
    }

    // Sets up and loads the image for the event into the image header
    private class SetEventHeader extends AsyncTask<Void, Void, Void> {
        Activity parent;
        Event event;

        @Override
        protected Void doInBackground(Void... params) {
            parent = getParent();
            event = EventsActivity.getEvent();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ImageView imageView = (ImageView) parent.findViewById(R.id.image_ride_event);
            if (event != null && event.getImage() != null && event.getImage() != "") {
                Picasso.with(parent).load(event.getImage()).fit().into(imageView);
            }
        }
    }
}
