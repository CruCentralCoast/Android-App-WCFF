package com.will_code_for_food.crucentralcoast.view.common;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.MultiMemoryRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class FeedFragment extends CruFragment implements TextView.OnEditorActionListener {
    SwipeRefreshLayout layout;
    MenuItem sortItem;
    ListView listView;
    EditText search;
    private int index, top;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        Log.i("FeedFragment", "feed fragment loading");

        layout = (SwipeRefreshLayout) hold.findViewById(R.id.card_refresh_layout);
        listView = (ListView) hold.findViewById(R.id.list_cards);
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
    public void onPause(){
        index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : v.getTop();
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        sortItem = menu.findItem(R.id.sort);

        searchItem.setActionView(R.layout.action_search);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                sortItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                ((FeedCardAdapter) listView.getAdapter()).clearSearch();
                search.setText("");
                sortItem.setVisible(true);
                return true;
            }
        });

        search = (EditText)menu.findItem(R.id.search).getActionView().findViewById(R.id.text);
        search.setOnEditorActionListener(this);
        search.setImeActionLabel(Util.getString(R.string.search_title), KeyEvent.KEYCODE_ENTER);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event == null || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            ((FeedCardAdapter) listView.getAdapter()).search(v.getText().toString());
            InputMethodManager imm = (InputMethodManager) getParent().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(listView.getWindowToken(), 0);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_newest) {
            Logger.i("FeedFragment", "sorting by newest");
            ((FeedCardAdapter) listView.getAdapter()).sortByNewest();
            return true;
        } else if (item.getItemId() == R.id.sort_oldest) {
            Logger.i("FeedFragment", "sorting by oldest");
            ((FeedCardAdapter) listView.getAdapter()).sortByOldest();
            return true;
        } else if (item.getItemId() == R.id.sort_type) {
            Logger.i("FeedFragment", "sorting by type");
            ((FeedCardAdapter) listView.getAdapter()).sortByType();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Gets objects loaded at application start.
     */
    private void loadList() {
        Logger.i("FeedFragment", "Loading feed for the first time");

        ArrayList<String> keyList = new ArrayList<String>();
        keyList.add(Database.REST_EVENT);
        keyList.add(Database.REST_RESOURCE);
        keyList.add(Database.VIDEOS);

        MultiMemoryRetriever retriever = new MultiMemoryRetriever(keyList);
        CardFragmentFactory factory = new FeedCardFactory();

        new RetrievalTask<Event>(retriever, factory, R.string.toast_no_events,
                new AsyncResponse(getParent()) {
                    @Override
                    public void otherProcessing() {
                        layout.setRefreshing(false);
                    }
                }).execute(index, top);
    }

    private void refreshList() {
        Logger.i("FeedFragment", "Refreshing Feed");

        if (!DBObjectLoader.loadEvents(Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh events", Toast.LENGTH_SHORT);
        }

        if (!DBObjectLoader.loadResources(Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh resources", Toast.LENGTH_SHORT);
        }

        if (!DBObjectLoader.loadVideos(Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh vidoes", Toast.LENGTH_SHORT);
        }

        loadList();
    }
}