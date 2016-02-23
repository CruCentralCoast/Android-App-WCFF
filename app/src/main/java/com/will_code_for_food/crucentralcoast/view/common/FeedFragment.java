package com.will_code_for_food.crucentralcoast.view.common;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.MultiMemoryRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.MultiRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.VideoRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.events.EventCardFactory;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareActivity;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareEventCardFactory;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class FeedFragment extends CruFragment {
    SwipeRefreshLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        layout = (SwipeRefreshLayout) hold.findViewById(R.id.card_refresh_layout);
        loadList();
        //refreshList();
        return hold;
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

    /**
     * Gets objects loaded at application start.
     */
    private void loadList() {
        Log.i("FeedFragment", "Loading feed for the first time");

        ArrayList<String> keyList = new ArrayList<String>();
        keyList.add(Database.REST_EVENT);
        keyList.add(Database.REST_RESOURCE);
        keyList.add(Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS);

        MultiMemoryRetriever retriever = new MultiMemoryRetriever(keyList);
        CardFragmentFactory factory = new FeedCardFactory();

        new RetrievalTask<Event>(retriever, factory, R.string.toast_no_events,
                new AsyncResponse(getParent()) {
                    @Override
                    public void otherProcessing() {
                        layout.setRefreshing(false);
                    }
                }).execute();
    }

    private void refreshList() {
        Log.i("FeedFragment", "Refreshing Feed");

        ArrayList<Retriever> retrieverList = new ArrayList<Retriever>();
        retrieverList.add(new SingleRetriever(RetrieverSchema.EVENT));
        retrieverList.add(new SingleRetriever(RetrieverSchema.RESOURCE));
        retrieverList.add(new VideoRetriever());

        MultiRetriever retriever = new MultiRetriever(retrieverList);
        CardFragmentFactory factory = new FeedCardFactory();

        new RetrievalTask<Event>(retriever, factory, R.string.toast_no_events,
                new AsyncResponse(getParent()) {
                    @Override
                    public void otherProcessing() {
                        layout.setRefreshing(false);
                    }
                }).execute();
    }
}
