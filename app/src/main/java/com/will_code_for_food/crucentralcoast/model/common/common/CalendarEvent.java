package com.will_code_for_food.crucentralcoast.model.common.common;

import android.app.Activity;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gavin on 11/15/2015.
 */
public class CalendarEvent {
    private String title;
    private String description;
    private String location;
    private long startTime;
    private long endTime;
    private long reminder;
    private String databaseId;

    private Long calendarId;

    // three hours reminder
    public static final int DEFAULT_REMINDER_TIME = 3 * 60;

    public CalendarEvent(String databaseId, String title, String description, String location, long startTime, long endTime, long reminder) {
        this.databaseId = databaseId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        if (reminder > 0) {
            this.reminder = reminder;
        } else {
            this.reminder = DEFAULT_REMINDER_TIME;
        }

        calendarId = null;
    }

    protected CalendarEvent(String databaseId, String title, String description, String location, long startTime, long endTime, long reminder, long calendarId) {
        this(databaseId, title, description, location, startTime, endTime, reminder);
        if (calendarId != -1) {
            this.calendarId = calendarId;
            Log.i("Create Event", "Found " + title + " in calendar");
        } else {
            Log.i("Create Event", "Did not find " + title + " in calendar");
            this.calendarId = null;
        }
    }

    protected void setCalendarId(final Long newId) {
        calendarId = newId;
    }

    public long getReminderTime() {
        return reminder;
    }

    public long getCalendarId() {
        return calendarId;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public boolean hasCalendarId() {
        return calendarId != null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(Date newStartTime) {
        startTime = newStartTime.getTime();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setEndTime(Date newEndTime) {
        endTime = newEndTime.getTime();
    }

    public long getEndTime() {
        return endTime;
    }
}
