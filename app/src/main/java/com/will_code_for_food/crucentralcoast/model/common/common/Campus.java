package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Created by Gavin on 11/12/2015.
 */
public class Campus extends DatabaseObject {
    private String postcode;
    private String state;
    private String suburb;
    private String street1;
    private String country;
    private String websiteUrl;

    public Campus(JsonObject obj) {
        super(obj);
        websiteUrl = this.getFieldAsString(Database.JSON_KEY_CAMPUS_URL);
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }
}
