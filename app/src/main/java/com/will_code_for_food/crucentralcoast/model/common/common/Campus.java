package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;

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
        websiteUrl = this.getString("url");
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }
}
