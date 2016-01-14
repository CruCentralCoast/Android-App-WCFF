package com.will_code_for_food.crucentralcoast.model.common.common;


import android.app.Activity;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;

/**
 * Created by Gavin on 1/13/2016.
 */
public class Event extends DatabaseObject {
    private CalendarEvent calendarEvent;

    public Event(JsonObject json) {
        super(json);
        updateCalendarEvent();
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
        calendarEvent = new CalendarEvent(getName(), getDescription(), getString("location"),
                Long.parseLong(getString("startDate")));
    }
}
