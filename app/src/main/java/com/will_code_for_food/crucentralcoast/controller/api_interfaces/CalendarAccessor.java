package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.util.List;

/**
 * Adds an event to the calendar using an intent
 */
public class CalendarAccessor {
    private static final String ALREADY_ADDED = "event-ids-added-to-calendar";
    /**
     * Adds a CalendarEvent to the user's calendar and saves the event's new calendar id
     */
    public static void addEventToCalendar(final CalendarEvent event, final Activity currentActivity) {
        Logger.i("Add to Calendar", "Building calendar intent");
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(CalendarContract.Events.CONTENT_URI);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(Events.TITLE, event.getTitle());
        Logger.i("Event Location", "location: " + event.getLocation());
        calIntent.putExtra(Events.EVENT_LOCATION, event.getLocation());
        calIntent.putExtra(Events.DESCRIPTION, event.getDescription());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                event.getStartTime());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                event.getEndTime());
        currentActivity.startActivity(calIntent);
        Logger.i("Add to Calendar", "Saving event id locally");
        LocalStorageIO.appendToList(event.getDatabaseId(), ALREADY_ADDED);
    }

    public static boolean isAlreadyAdded(final Event event) {
        Logger.i("Add to Calendar", "Checking if event id has been saved locally");
        List<String> events =  LocalStorageIO.readList(ALREADY_ADDED);
        return events != null && events.contains(event.getId());
    }
}
