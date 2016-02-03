package com.will_code_for_food.crucentralcoast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.fragments.EventsFragment;
import com.will_code_for_food.crucentralcoast.view.fragments.RideShareSelectActionFragment;

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

        loadFragmentById(R.layout.fragment_eventslist, title, new EventsFragment(), this);
    }

    /**
     * Changes the "add to calendar" button to reflect whether or not
     * the event is already in the calendar
     */
    public void modifyAddToCalendarButton() {
        Log.e("Changing button", "changing button");
        calendarButton = (ImageButton) findViewById(R.id.button_calendar);
        if (event.isInCalendarAlready()) {
            calendarButton.setImageResource(R.drawable.calendar_added);
        } else {
            calendarButton.setImageResource(R.drawable.calendar_add2);
        }
    }
    
    // Adds the event to the user's Google Calendar
    public void calendarButton(View view) {
        if (event != null) {
            if (event.isInCalendarAlready()) {
                event.deleteFromCalendar(this);
                Toast.makeText(getApplicationContext(),
                        Util.getString(R.string.toast_calendar_removed), Toast.LENGTH_LONG).show();
                calendarButton.setImageResource(R.drawable.calendar_add2);
            } else {
                event.saveToCalendar(this);
                Toast.makeText(getApplicationContext(),
                        Util.getString(R.string.toast_calendar_added), Toast.LENGTH_LONG).show();
                calendarButton.setImageResource(R.drawable.calendar_added);
            }
        } else {
            Toast.makeText(getApplicationContext(), Util.getString(R.string.cal_fail_msg),
                    Toast.LENGTH_LONG).show();
        }
    }

    // Opens the event's ridesharing page, if one exists
    public void viewRidesharing(View view) {
        if (event.hasRideSharing()) {
            this.loadFragmentById(R.layout.fragment_ridesharing_select_action, event.getName() + " > " + RideShareSelectActionFragment.TITLE, new RideShareSelectActionFragment(), this);
        }
        else {
            Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_no_rides),
                    Toast.LENGTH_LONG).show();
        }
    }

    // Opens the event's Facebook page
    public void viewFacebook(View view) {
        String url = event.getFacebookLink();

        // Check for valid url
        if (event.hasFacebook()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        // Invalid url
        else {
            Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_no_facebook),
                    Toast.LENGTH_LONG).show();
        }
    }

    // Opens the event's location in Google Maps
    public void viewMap(View view) {
        // No map for this location
        if (!event.hasLocation()) {
            Toast.makeText(getApplicationContext(), Util.getString(R.string.toast_no_map),
                    Toast.LENGTH_LONG).show();
        }
        // Link to the Google Map page
        else {
            String map = Database.GOOGLE_MAP + event.getEventLocation();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
        }
    }
}