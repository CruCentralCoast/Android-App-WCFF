package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleMemoryRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsFragment;

import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 2/15/2016.
 */
public class MyRidesFragment extends CruFragment {

    private SwipeRefreshLayout layout;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        initComponents(fragmentView);
        loadList();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    private void initComponents(View fragmentView) {
        //Set up action button to add a ride
        fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.loadFragmentById(R.layout.fragment_card_list,
                        Util.getString(R.string.ridesharing_header), new EventsFragment(),
                        activity);
            }
        });

        layout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.card_refresh_layout);
    }

    private void loadList() {
        Retriever retriever = new SingleMemoryRetriever(Database.REST_RIDE);
        populateList(retriever);
    }

    private void refreshList() {
        Logger.i("MyRidesFragment", "refreshing rides");

        if (!DBObjectLoader.loadRides(Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh rides", Toast.LENGTH_SHORT).show();
        }

        loadList();
    }

    private void populateList(Retriever retriever) {
        CardFragmentFactory factory = new MyRideCardFactory();

        //try {
                new RetrievalTask<Ride>(retriever, factory, R.string.toast_no_my_rides, new AsyncResponse(getParent()) {
                @Override
                public void otherProcessing() {
                    layout.setRefreshing(false);
                }
            }).execute(); //.get(5000, TimeUnit.MILLISECONDS);
        //} catch (Exception e) {
        //    Toast.makeText(getParent(), "Unable to load rides", Toast.LENGTH_SHORT).show();
        //}
    }
}
