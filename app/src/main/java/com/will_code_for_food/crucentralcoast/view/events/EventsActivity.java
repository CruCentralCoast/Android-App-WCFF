package com.will_code_for_food.crucentralcoast.view.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareSelectActionFragment;

/**
 * EventsActivity implements the functionality of the buttons in an Event page
 * Calls asynchronous task LoadEventsTask to load the list of events
 */
public class EventsActivity extends MainActivity {

    protected String title = "Events";

    private static Event event = null;
    private ImageButton calendarButton;

    public static void setEvent(final Event newEvent) {
        event = newEvent;
    }

    public static Event getEvent() {
        return event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_card_list, title, new EventsFragment(), this);
    }
}