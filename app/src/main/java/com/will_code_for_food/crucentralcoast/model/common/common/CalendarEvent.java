package com.will_code_for_food.crucentralcoast.model.common.common;

/**
 * Created by Gavin on 11/15/2015.
 */
public class CalendarEvent {
    private String title;
    private String description;
    private String location;
    private long startTime;

    private Long id;

    // three hours reminder
    public static final int reminderTime = 3 * 60;

    public CalendarEvent(String title, String description, String location, long startTime) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        id = null;
    }

    private CalendarEvent(String title, String description, String location, long startTime,
            long id) {
        this(title, description, location, startTime);
        this.id = id;
    }

    public CalendarEvent copy(long newId) {
        return new CalendarEvent(title, description, location, startTime, newId);
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
