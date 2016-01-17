package com.will_code_for_food.crucentralcoast.model.common.common;


import android.app.Activity;
import android.provider.ContactsContract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public void saveToCalendar(final Activity currentActivity) {
        updateCalendarEvent();
        if (calendarEvent.hasId()) {
            CalendarAccessor.editExistingEvent(calendarEvent, currentActivity);
        } else {
            long id = CalendarAccessor.addEventToCalendar(calendarEvent, currentActivity);
            calendarEvent = calendarEvent.copy(id);
        }
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

    private void updateCalendarEvent() {
        DateFormat isoFormater = new SimpleDateFormat(Database.ISO_FORMAT);
        Date dateTime;

        try {
            dateTime = isoFormater.parse(getFieldAsString(Database.JSON_KEY_EVENT_STARTDATE));
        }

        catch (java.text.ParseException exception) {
            exception.printStackTrace();
            dateTime = new Date();
        }

        calendarEvent = new CalendarEvent(getName(), getDescription(), getFieldAsString(Database.JSON_KEY_EVENT_LOCATION), dateTime);
    }
}
