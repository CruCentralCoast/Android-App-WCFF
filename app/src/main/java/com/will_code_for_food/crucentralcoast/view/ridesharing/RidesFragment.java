package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleMemoryRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.Date;

/**
 * Created by Kayla on 2/1/2016.
 */
public class RidesFragment extends CruFragment implements TextView.OnEditorActionListener {
    ListView listView;
    SwipeRefreshLayout layout;
    EditText search;
    private int index, top;
    MenuItem sortItem;
    Event selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        loadRidesList();
        setHasOptionsMenu(true);
        layout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.ridelist_swipe);
        listView = (ListView) fragmentView.findViewById(R.id.list_cards);

        //Set up action button to add a ride

        final Event selectedEvent = EventsActivity.getEvent();
        selected = selectedEvent;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.rides_options_menu, menu);
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
                ((RideAdapter) listView.getAdapter()).clearSearch();
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
            ((RideAdapter) listView.getAdapter()).search(v.getText().toString());
            InputMethodManager imm = (InputMethodManager) getParent().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(listView.getWindowToken(), 0);
        }
        return true;
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
        Logger.i("RidesFragment", "refreshing rides list");

        if (!DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh rides", Toast.LENGTH_SHORT).show();
        }
        loadRidesList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_newest) {
            Logger.i("RideFragment", "sorting by newest");
            ((RideAdapter) listView.getAdapter()).sortByNewest();
            return true;
        } else if (item.getItemId() == R.id.sort_oldest) {
            Logger.i("RideFragment", "sorting by oldest");
            ((RideAdapter) listView.getAdapter()).sortByOldest();
            return true;
        } else if(item.getItemId() == R.id.sort_time){
            Logger.i("RideFragment", "sorting by time");

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.enter_time_dialog);
            dialog.setTitle("Sort by Time");

            // set the custom dialog components - text, image and button
            final TimePicker time = (TimePicker) dialog.findViewById(R.id.time_picker_time);
            Button dialogButton = (Button) dialog.findViewById(R.id.filter_time_button);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    String[] dayInfo = selected.getEventDate().split("/");
                    date.setMonth(Integer.parseInt(dayInfo[0]));
                    date.setDate(Integer.parseInt(dayInfo[1]));
                    date.setYear(Integer.parseInt("20" + dayInfo[2]));
                    date.setTime(time.getCurrentHour() + time.getCurrentMinute());
                    ((RideAdapter) listView.getAdapter()).sortByTime(date);
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
