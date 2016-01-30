package com.will_code_for_food.crucentralcoast.model.getInvolved;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Brian on 1/28/2016.
 */
public class SummerMission extends DatabaseObject {
    public SummerMission(JsonObject obj) {
        super(obj);
    }
    // Gets the date of the event in reader format
    public String getMissionDateString() {

        JsonElement dateStart = this.getField(Database.JSON_KEY_EVENT_STARTDATE);
        JsonElement dateEnd = this.getField(Database.JSON_KEY_EVENT_ENDDATE);
        String eventDate;

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date start = dateFormat.parse(dateStart.getAsString());
            Date end = dateFormat.parse(dateEnd.getAsString());
            eventDate = formatDate(start);
        } catch (ParseException e) {
            // Can't be parsed; just use the default ISO format
            eventDate = dateStart.getAsString();
        }
        return eventDate;
    }

    // Formats the date into the form Jan 15, 7:00AM
    private String formatDate(Date date) {
        String formattedDate = new SimpleDateFormat(Database.EVENT_DATE_FORMAT).format(date);
        return formattedDate;
    }
}
