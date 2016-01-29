package com.will_code_for_food.crucentralcoast.model.common.common;

import android.provider.ContactsContract;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Created by Kayla on 1/27/2016.
 */
public class SummerMission extends DatabaseObject {

    public SummerMission(JsonObject json) {
        super(json);
    }

    // Gets the website url of the mission
    public String getWebsiteUrl() {
        return getFieldAsString(Database.JSON_KEY_COMMON_URL);
    }

    // Gets the destination of the mission
    public String getDestination() {
        return getFieldAsString(Database.JSON_KEY_MISSION_NAME);
    }

    // Gets the cost of the mission
    public int getCost() {
        JsonElement costField = getField(Database.JSON_KEY_MISSION_COST);
        return costField.getAsInt();
    }
}
