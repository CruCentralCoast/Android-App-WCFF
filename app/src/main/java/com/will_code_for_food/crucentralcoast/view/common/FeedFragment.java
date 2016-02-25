package com.will_code_for_food.crucentralcoast.view.common;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
    MenuItem sortItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        layout = (SwipeRefreshLayout) hold.findViewById(R.id.card_refresh_layout);
        loadList();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        sortItem = menu.findItem(R.id.sort);

        searchItem.setActionView(R.layout.action_search);
        //menu.findItem(R.id.sort).setActionView(R.layout.action_sort);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                sortItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                sortItem.setVisible(true);
                return true;
            }
        });

        EditText search = (EditText)menu.findItem(R.id.search).getActionView().findViewById(R.id.text);
        //Spinner sortOptions = (Spinner) menu.findItem(R.id.sort).getActionView().findViewById(R.id.sort_spinner);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getParent(), R.array.sort_options, android.R.layout.simple_spinner_item);


        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sortOptions.setAdapter(adapter);

        //search.setOnEditorActionListener(this);
        search.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            sortItem.setVisible(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        sortItem.setVisible(true);
        super.onOptionsMenuClosed(menu);
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
