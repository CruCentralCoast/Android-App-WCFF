package com.will_code_for_food.crucentralcoast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.temp.EventTask;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Implementation of the UI behavior for the Event UI
 * Calls asynchronous task EventTask to load the list of events
 * Button logic goes in here
 */
public class EventsActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_eventslist, "Events");
        //loadEvents();
    }

    // Display the list of events
    public void loadEvents() {
        new EventTask().execute();
    }

    public void testNotifier(View view) {
        notifier.createNotification("title", "text", getApplicationContext());
    }

    // Adds the event to the Google Calendar
    public void testCalendar(View view) {
        ImageButton calendarButton = (ImageButton)findViewById(R.id.button_calendar);
        calendarButton.setImageResource(R.drawable.calendar_added);
        // TODO: ADD TO CALENDAR
        Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_calendar_added), Toast.LENGTH_LONG).show();
    }

    // Links to the event's ridesharing page, if ridesharing exists
    public void testRideshare(View view) {
        // UNIMPLEMENTED FOR NOW
        Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_no_rides), Toast.LENGTH_LONG).show();
    }

    // Links to the event's Facebook page
    public void testFacebook(View view) {
        ImageButton fbButton = (ImageButton)findViewById(R.id.button_facebook);
        String url = fbButton.getContentDescription().toString();

        // Check for valid url
        if (url != null && url != "") {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else {
            Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_no_facebook), Toast.LENGTH_LONG).show();
        }
    }

    // Link to the Google map location of the event
    public void testMap(View view) {
        ImageButton mapButton = (ImageButton)findViewById(R.id.button_map);

        // No map for this location
        if (mapButton.getContentDescription().equals(Database.EVENT_BAD_LOCATION)) {
            Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_no_map), Toast.LENGTH_LONG).show();
        }
        else {
            TextView locationLabel = (TextView)findViewById(R.id.text_event_location);
            String map = "http://maps.google.co.in/maps?q=" + locationLabel.getText();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
        }
    }
}