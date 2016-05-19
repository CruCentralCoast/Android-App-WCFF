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

    // Gets the date of the mission in reader format
    public String getMissionDateString() {
        return super.getFormattedDate(Database.JSON_KEY_EVENT_STARTDATE, Database.MISSION_DATE_FORMAT);
    }

    // Gets the start and end date of the mission in reader format
    public String getMissionFullDate() {
        return getMissionDateString() + " - " +
                super.getFormattedDate(Database.JSON_KEY_EVENT_ENDDATE, Database.MISSION_DATE_FORMAT);
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