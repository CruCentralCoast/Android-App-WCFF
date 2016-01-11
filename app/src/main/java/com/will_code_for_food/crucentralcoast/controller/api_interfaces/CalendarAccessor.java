package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by Gavin on 11/15/2015.
 */
public class CalendarAccessor {
    // todo(Gavin) should fix where this is used
    public static final String CAL_EXISTING_EVENTS_FNAME = "existingEventsFile";

    public static long TEST_ID = -1;

    public static void editExistingEvent(final CalendarEvent newEvent, final String oldEventName, final Activity currentActivity) {
        if (TEST_ID != -1) {
            ContentResolver cr = currentActivity.getContentResolver();
            ContentValues values = new ContentValues();
            Uri updateUri = null;
            // TODO update other fields as well
            values.put(Events.TITLE, newEvent.title);
            updateUri = ContentUris.withAppendedId(Events.CONTENT_URI, TEST_ID);
            int rows = currentActivity.getContentResolver().update(updateUri, values, null, null);

            Toast.makeText(currentActivity.getApplicationContext(),
                    R.string.cal_event_edit_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(currentActivity.getApplicationContext(),
                    R.string.cal_fail_msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void addEventToCalendar(final CalendarEvent event, final Activity currentActivity) {
        try {
            // event information
            ContentValues eventVals = new ContentValues();
            eventVals.put(Events.TITLE, event.title);
            eventVals.put(Events.DESCRIPTION, event.description);
            eventVals.put(Events.DTSTART, event.startTime);
            eventVals.put(Events.EVENT_LOCATION, event.location);
            TimeZone timeZone = TimeZone.getDefault();
            eventVals.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
            // hour long
            eventVals.put(Events.DTEND, event.startTime + 60 * 60 * 1000);
            // default calendar
            eventVals.put(CalendarContract.Events.CALENDAR_ID, 1);
            // insert event to calendar
            ContentResolver cr = currentActivity.getContentResolver();
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, eventVals);

            // add reminder
            long eventId = Long.parseLong(uri.getLastPathSegment());
            TEST_ID = eventId; // TODO remove

            ContentValues reminderVals = new ContentValues();
            reminderVals.put(CalendarContract.Reminders.MINUTES, CalendarEvent.reminderTime);
            reminderVals.put(CalendarContract.Reminders.EVENT_ID, eventId);
            reminderVals.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderVals);

            // display confirmation message
            Toast.makeText(currentActivity.getApplicationContext(),
                    R.string.cal_add_success,
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            // I know this is not good practice, but I wanted a catch all for now
            Toast.makeText(currentActivity.getApplicationContext(),
                    R.string.cal_fail_msg,
                    Toast.LENGTH_LONG).show();
        }
    }

    private static void storeCalendarEvent(final long id, final String name, final Activity currentActivity) {
        Context ctxt = currentActivity.getApplicationContext();
        FileOutputStream fos = null;
        try {
            // this needs to be changed to not overwrite the file each time
            fos = ctxt.openFileOutput(CAL_EXISTING_EVENTS_FNAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String toWrite = '"' + name + '"' + ':' + id;
        try {
            fos.write(toWrite.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
