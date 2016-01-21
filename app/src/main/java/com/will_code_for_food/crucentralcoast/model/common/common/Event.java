package com.will_code_for_food.crucentralcoast.model.common.common;


import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Gavin on 1/13/2016.
 */
public class Event extends DatabaseObject {
    private CalendarEvent calendarEvent;
    private ArrayList<String> parentMinistries;

    public Event(JsonObject json) {
        super(json);

        loadParentMinistries();
        updateCalendarEvent();
    }

    public ArrayList<String> getParentMinistries() {
        return parentMinistries;
    }

    /**
     * Saves the event to the user's calendar
     */
    public void saveToCalendar(final Activity currentActivity) {
        updateCalendarEvent();
        Log.i("Accessing Calendar", "Creating new event");
        long id = CalendarAccessor.addEventToCalendar(calendarEvent, currentActivity);
        calendarEvent.setCalendarId(id);
    }

    /**
     * Checks if the event has been saved to the user's
     * local calendar.
     */
    public boolean isInCalendarAlready() {
        return calendarEvent.hasCalendarId();
    }

    public void deleteFromCalendar(final Activity currentActivity) {
        Log.i("Accessing Calendar", "Deleting event from calendar");
        CalendarAccessor.deleteEventFromCalendar(calendarEvent, currentActivity);
        calendarEvent.setCalendarId(null);
    }

    /**
     * Parses the JsonArray that contains the parent Ministries associated with this object.
     */
    private void loadParentMinistries() {
        parentMinistries = new ArrayList<String>();
        JsonArray ministriesJson = this.getField(Database.JSON_KEY_EVENT_MINISTRIES).getAsJsonArray();

        for (JsonElement ministry : ministriesJson) {
            if (ministry.isJsonPrimitive()) {
                parentMinistries.add(ministry.getAsString());
            }
        }
    }

    /**
     * Refills all fields of the event from the information in the database
     */
    private void updateCalendarEvent() {
        final DateFormat isoFormater = new SimpleDateFormat(Database.ISO_FORMAT);
        Date startTime;
        Date endTime;
        Date reminderTime;
        long reminderMinutesBefore = CalendarEvent.DEFAULT_REMINDER_TIME;
        String databaseId;

        try {
            startTime = isoFormater.parse(getFieldAsString(Database.JSON_KEY_EVENT_STARTDATE));
            databaseId = getFieldAsString(Database.JSON_KEY_COMMON_ID);
            try {
                endTime = isoFormater.parse(getFieldAsString(Database.JSON_KEY_EVENT_ENDDATE));
            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(startTime);
                calendar.add(Calendar.HOUR, 1);
                endTime = calendar.getTime();
                Log.e("Event Creation", "Unable to get event end time for " + getName());
            }

            try {
                reminderTime = isoFormater.parse(getFieldAsString(Database.JSON_KEY_EVENT_REMINDER));
                long diff =(int)(endTime.getTime() - reminderTime.getTime());
                if (diff > 0) {
                    reminderMinutesBefore = diff / (60 * 1000) % 60;
                    // TODO switch to Joda-Time instead of java.util.Date
                }
            } catch (java.text.ParseException | NullPointerException ex) {
                Log.i("Event Creation", "Unable to get event reminder time for " + getName());
            }

            int calendarId = CalendarAccessor.getExitingCalendarEventId(getName(), databaseId);

            calendarEvent = new CalendarEvent(databaseId, getName(), getDescription(),
                    getFieldAsString(Database.JSON_KEY_EVENT_LOCATION), startTime.getTime(),
                    endTime.getTime(), reminderMinutesBefore, calendarId);
        } catch (java.text.ParseException ex) {
            Log.e("Event Creation", "Unable to get mandatory data for" + getName());
        }
    }
}
