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
        String eventDate;

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
            Date start = dateFormat.parse(dateStart.getAsString());
            eventDate = formatDate(start);
        } catch (ParseException e) {
            // Can't be parsed; just use the default ISO format
            eventDate = dateStart.getAsString();
        }

        return eventDate;
    }

    public String getMissionFullDate() {
        JsonElement dateEnd = getField(Database.JSON_KEY_EVENT_ENDDATE);
        String eventDate;
        try {
            DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
            SimpleDateFormat newFormat = new SimpleDateFormat(Database.MISSION_DATE_FORMAT);
            Date end = dateFormat.parse(dateEnd.getAsString());
            eventDate = getMissionDateString() + " - " + newFormat.format(end);
        } catch (ParseException e) {
            // Can't be parsed; just use the default ISO format
            eventDate = getMissionDateString();
        }

        return eventDate;
    }

    // Formats the date into the form January 15, 2016
    private String formatDate(Date date) {
        String formattedDate = new SimpleDateFormat(Database.MISSION_DATE_FORMAT).format(date);
        return formattedDate;
    }

    // Gets the website url of the mission
    public String getWebsiteUrl() {
        return getFieldAsString(Database.JSON_KEY_COMMON_URL);
    }

    // Gets the cost of the mission
    public int getCost() {
        JsonElement costField = getField(Database.JSON_KEY_MISSION_COST);
        return costField.getAsInt();
    }
}
