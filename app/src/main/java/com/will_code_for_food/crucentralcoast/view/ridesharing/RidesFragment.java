package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleMemoryRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;

/**
 * Created by Kayla on 2/1/2016.
 */
public class RidesFragment extends CruFragment {
    ListView listView;
    SwipeRefreshLayout layout;
    private int index, top;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        loadRidesList();
        layout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.ridelist_swipe);
        listView = (ListView) fragmentView.findViewById(R.id.list_cards);

        //Set up action button to add a ride

        final Event selectedEvent = EventsActivity.getEvent();
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParent().loadFragmentById(R.layout.fragment_ridesharing_driver_form,
                        selectedEvent.getName() + " > " +
                                Util.getString(R.string.ridesharing_driver_form_title),
                        new RideShareDriverFormFragment(), getParent());
            }
        });
        return fragmentView;
    }

    @Override
    public void onPause(){
        index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : v.getTop();
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRidesList();
            }
        });
    }

    public void loadRidesList() {
        SingleMemoryRetriever retriever = new SingleMemoryRetriever(Database.REST_RIDE);
        populateList(retriever);
    }

    public void refreshRidesList() {
        Log.i("RidesFragment", "refreshing rides list");

        if (!DBObjectLoader.loadRides(Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh rides", Toast.LENGTH_SHORT).show();
        }

        loadRidesList();
    }

    // Takes the user to the list of rides for the event
    //TODO: user first needs to fill out form
    public void populateList(Retriever retriever) {
        new SetEventHeader().execute();

        CardFragmentFactory factory = new RideCardFactory(getParent());
        //TODO: callback task for selecting a ride (currently null)
        new RetrievalTask<Ride>(retriever, factory, R.string.toast_no_rides,
                new AsyncResponse(getParent()) {
            @Override
            public void otherProcessing() {
                layout.setRefreshing(false);
            }
        }).execute(index, top);
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
