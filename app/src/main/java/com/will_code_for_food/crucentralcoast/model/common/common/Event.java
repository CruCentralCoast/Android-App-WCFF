package com.will_code_for_food.crucentralcoast.model.common.common;


import android.app.Activity;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gavin on 1/13/2016.
 */
public class Event extends DatabaseObject {
    private CalendarEvent calendarEvent;

    public Event(JsonObject json) {
        super(json);
        //updateCalendarEvent();
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

    private void updateCalendarEvent() {
        DateFormat isoFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        Date dateTime;

        try {
            dateTime = isoFormater.parse(getString("startDate"));
        }

        catch (java.text.ParseException exception) {
            exception.printStackTrace();
            dateTime = new Date();
        }

        calendarEvent = new CalendarEvent(getName(), getDescription(), getString("location"), dateTime);
    }
}
