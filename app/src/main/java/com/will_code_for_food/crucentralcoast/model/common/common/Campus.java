package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Created by Gavin on 11/12/2015.
 */
public class Campus extends DatabaseObject {
    private Location location;
    private String websiteUrl;

    public Campus(JsonObject obj) {
        super(obj);

        websiteUrl = this.getFieldAsString(Database.JSON_KEY_CAMPUS_URL);
        location = new Location(this.getField(Database.JSON_KEY_COMMON_LOCATION));
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public Location getLocation() {
        return location;
    }
}
