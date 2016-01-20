package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.local_io.log.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by Gavin on 11/15/2015.
 */
public class CalendarAccessor {

    public static void editExistingEvent(final CalendarEvent event, final Activity currentActivity) {
        if (!event.hasId()) {
            ContentResolver cr = currentActivity.getContentResolver();
            Uri updateUri = null;
            ContentValues values = getContentValues(event);
            updateUri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getId());
            int rows = currentActivity.getContentResolver().update(updateUri, values, null, null);
            Toast.makeText(currentActivity.getApplicationContext(), R.string.cal_event_edit_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(currentActivity.getApplicationContext(), R.string.cal_fail_msg, Toast.LENGTH_LONG).show();
        }
    }

    public static long addEventToCalendar(final CalendarEvent event, final Activity currentActivity) {
        long eventId = -1;
        try {
            Log.i("EVENT", "Creating event in calendar: " + event.getTitle());
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
        } catch (Exception ex) {
            // I know this is not good practice, but I wanted a catch all for now
            ex.printStackTrace();
        }

        return eventId;
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
