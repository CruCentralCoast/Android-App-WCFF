package com.will_code_for_food.crucentralcoast.model.common.common;

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

    private Long id;

    // three hours reminder
    public static final int DEFAULT_REMINDER_TIME = 3 * 60;

    public CalendarEvent(String title, String description, String location, long startTime,
                        long endTime, long reminder) {
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

        id = null;
    }

    public CalendarEvent(String title, String description, String location, Date startTime,
                         Date endTime, long reminder) {
        this(title, description, location, startTime.getTime(), endTime.getTime(), reminder);
    }

    private CalendarEvent(String title, String description, String location, long startTime,
                          long endTime, long reminder, long id) {
        this(title, description, location, startTime, endTime, reminder);
        this.id = id;
    }

    public long getReminderTime() {
        return reminder;
    }

    public CalendarEvent copy(long newId) {
        return new CalendarEvent(title, description, location, startTime, endTime, reminder, newId);
    }

    public long getId() {
        return id;
    }

    public boolean hasId() {
        return id != -1;
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
