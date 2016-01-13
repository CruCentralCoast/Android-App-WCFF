package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gavin on 11/12/2015.
 */
public class Campus extends DatabaseObject {
    private String name;
    private String postcode;
    private String state;
    private String suburb;
    private String street1;
    private String country;
    private String websiteUrl;

    public Campus(JsonObject obj) {
        super(obj);

        name = this.getString("name");
        websiteUrl = this.getString("url");
    }

    public String getName() { return name; }
    public String getWebsiteUrl() { return websiteUrl; }
}
