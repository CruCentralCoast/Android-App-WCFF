package com.will_code_for_food.crucentralcoast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.will_code_for_food.crucentralcoast.controller.retrieval.EventRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity which handles displaying of Events
 */
public class EventsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_eventslist, "Events");
        loadEvents();
    }

    // Display the list of events
    public void loadEvents() {
        new EventTask().execute();
    }

    public void testNotifier(View view) {
        notifier.createNotification("title", "text", getApplicationContext());
    }

    public void testCalendar(View view) {
        ImageButton calendarButton = (ImageButton)findViewById(R.id.button_calendar);
        calendarButton.setImageResource(R.drawable.calendar_added);
    }

    public void testRideshare(View view) {

    }

    public void testFacebook(View view) {

    }

    public void testMap(View view) {

    }
}
