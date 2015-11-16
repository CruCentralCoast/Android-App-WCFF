package com.will_code_for_food.crucentralcoast.model.common.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gavin on 11/15/2015.
 */
public class CalendarEvent {
    public final String title;
    public final String description;
    public final String location;
    public final long startTime;

    // three hours reminder
    public static final int reminderTime = 3 * 60;

    public CalendarEvent(String title, String description, String location, Calendar startTime) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.startTime = startTime.getTime().getTime();
    }
}
