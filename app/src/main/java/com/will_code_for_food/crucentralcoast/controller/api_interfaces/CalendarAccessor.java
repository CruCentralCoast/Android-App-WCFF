package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by Gavin on 11/15/2015.
 */
public class CalendarAccessor {

    private static final String EVENT_KEY_SET = "cal_event_id_set";
    private static final String DELIMITER = "|";

    private static void editExistingEvent(final CalendarEvent event, final Activity currentActivity) {
        if (!event.hasCalendarId()) {
            ContentResolver cr = currentActivity.getContentResolver();
            Uri updateUri = null;
            ContentValues values = getContentValues(event);
            updateUri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getCalendarId());
            int rows = currentActivity.getContentResolver().update(updateUri, values, null, null);
            Toast.makeText(currentActivity.getApplicationContext(), R.string.cal_event_edit_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(currentActivity.getApplicationContext(), R.string.cal_fail_msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Deletes an event from the calendar and from the set of calendar ids
     */
    public static void deleteEventFromCalendar(final CalendarEvent event,
                                               final Activity currentActivity) {
        Uri eventUri = ContentUris
                .withAppendedId(getCalendarUriBase(), event.getCalendarId());
        Logger.i("Deleting...", "deleting id " + event.getCalendarId());
        currentActivity.getContentResolver().delete(eventUri, null, null);
        deleteFromSet(event.getCalendarId());
    }

    /**
     * Deletes a list of calendar ids from the set
     */
    private static void deleteFromSet(Long... calIds) {
        Set<String> set = Util.loadStringSet(EVENT_KEY_SET);
        Set<String> newSet = new HashSet<>();
        for (String str : set) {
            boolean add = true;
            for (Long toRemove : calIds) {
                if (str.contains(toRemove.toString())) {
                    add = false;
                }
            }
            if (add) {
                newSet.add(str);
            }
        }
        Util.clearSet(EVENT_KEY_SET);
        Util.saveToSet(EVENT_KEY_SET, newSet);
    }

    /**
     * Gets the URI needed to remove events from the calendar. Will be deprecated with
     * the use of events.
     */
    private static Uri getCalendarUriBase() {
        // TODO this will change when we use Intents to add events
        Uri eventUri;
        if (android.os.Build.VERSION.SDK_INT <= 7) {
            // the old way
            eventUri = Uri.parse("content://calendar/events");
        } else {
            // the new way
            eventUri = Uri.parse("content://com.android.calendar/events");
        }
        return eventUri;
    }

    /**
     * Adds a CalendarEvent to the user's calendar and saves the event's new calendar id
     */
    public static long addEventToCalendar(final CalendarEvent event, final Activity currentActivity) {
        long eventId = getExitingCalendarEventId(event.getTitle(), event.getDatabaseId());
        if (eventId == -1) {
            try {
                Logger.i("EVENT", "Creating event in calendar: " + event.getTitle());
                // event information
                ContentValues values = getContentValues(event);
                TimeZone timeZone = TimeZone.getDefault();
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                // default calendar
                values.put(CalendarContract.Events.CALENDAR_ID, 1);
                // insert event to calendar
                ContentResolver cr = currentActivity.getContentResolver();
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                // add reminder
                eventId = Long.parseLong(uri.getLastPathSegment());

                ContentValues reminderVals = new ContentValues();
                reminderVals.put(CalendarContract.Reminders.MINUTES, event.getReminderTime());
                reminderVals.put(CalendarContract.Reminders.EVENT_ID, eventId);
                reminderVals.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderVals);

                // save id to phone (calendar id, event id from database)
                Util.saveToSet(EVENT_KEY_SET, eventId + DELIMITER + event.getDatabaseId());
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.i("Event to calendar", "Unable to add event to calendar");
            }
        } else {
            Logger.i("Event to calendar", "Found event already in calendar. Updating.");
            editExistingEvent(event, currentActivity);
        }
        return eventId;
    }

    /**
     * Returns the id associated with the event in the users calendar, if it was added through the
     * app and the id is still associated with the event database id in the event calendar set
     */
    public static int getExitingCalendarEventId(final String eventTitle, final String databaseId) {
        Set<String> set = Util.loadStringSet(EVENT_KEY_SET);
        int foundId = -1;
        if (set != null) {
            for (String str : set) {
                String[] pair = str.split("\\" + DELIMITER);
                if (databaseId.equals(pair[1])) {
                    Logger.i("Found event", "Id: " + pair[0] + ", Title: " + eventTitle);
                    return Integer.parseInt(pair[0]);
                }
            }
        }
        return foundId;
    }

    /**
     * Called on startup of events tab. Removes any id from the set that is no longer
     * in the user's calendar (they deleted it from outside the app)
     */
    public static void pruneEventsList(final Activity currentActivity) {
        Set<String> set = Util.loadStringSet(EVENT_KEY_SET);
        if (set != null && set.size() > 0) {
            Long[] toRemove = new Long[set.size()];
            int ndx = 0;
            for (String str : set) {
                String[] pair = str.split("\\" + DELIMITER);
                long id = Long.parseLong(pair[0]);
                Uri event = ContentUris.withAppendedId(Events.CONTENT_URI, id);
                Cursor cursor = currentActivity.managedQuery(event, null, null, null, null);
                if (cursor.getCount() != 1) {
                    Logger.i("Deleted Event", "Could not find event " + pair[1]);
                    toRemove[ndx++] = id;
                }
            }
            deleteFromSet(toRemove);
        }
    }

    private static ContentValues getContentValues(final CalendarEvent event) {
        ContentValues values = new ContentValues();
        values.put(Events.TITLE, event.getTitle());
        values.put(Events.DESCRIPTION, event.getDescription());
        values.put(Events.EVENT_LOCATION, event.getLocation());
        values.put(Events.DTSTART, event.getStartTime());
        values.put(Events.DTEND, event.getEndTime());
        return values;
    }
}
