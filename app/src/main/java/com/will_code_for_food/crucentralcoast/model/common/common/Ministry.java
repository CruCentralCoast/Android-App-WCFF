package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 */
public class Ministry extends DatabaseObject {
    private String name;
    private String description;

    public Ministry(JsonObject obj) {
        super(obj);

        name = this.get("name");
        description = this.get("description");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
