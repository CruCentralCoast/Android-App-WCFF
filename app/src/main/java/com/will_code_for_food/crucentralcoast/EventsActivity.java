package com.will_code_for_food.crucentralcoast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.will_code_for_food.crucentralcoast.controller.retrieval.EventRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 1/14/16.
 */
public class EventsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_event, "Events");

        loadEvents();
    }

    /**
     * Retrieves and loads all of the events
     */
    public void loadEvents() {
        new EventTask().execute(0);
    }

    public void testNotifier(View view) {
        notifier.createNotification("title", "text", getApplicationContext());
    }

    /*
    public void testCalendar(View view) {
        // building test event
        CalendarEvent event = new CalendarEvent("Leave for CRU Event", "This is a cru event " +
                "that should be added to the users calendar at this exact time.", "PAC Circle",
                Calendar.getInstance());
        CalendarAccessor.addEventToCalendar(event, this);
    }

    public void testCalendarEdit(View view) {
        // building test event
        CalendarEvent event = new CalendarEvent("New Title!", "This is a cru event " +
                "that should be added to the users calendar at this exact time.", "PAC Circle",
                Calendar.getInstance());
        CalendarAccessor.editExistingEvent(event, "Leave for CRU Event", this);
    }
    */
}
