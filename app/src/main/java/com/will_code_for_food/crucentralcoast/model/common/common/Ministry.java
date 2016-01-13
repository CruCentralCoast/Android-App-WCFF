package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 */
public class Ministry extends DatabaseObject {
    private String name;
    private String description;
    private ArrayList<String> campuses;

    public Ministry(JsonObject obj) {
        super(obj);

        name = this.getString("name");
        description = this.getString("description");
        campuses = new ArrayList<String>();

        JsonElement campusList = this.get("campuses");

        if (campusList.isJsonArray()) {
            for (JsonElement campus : campusList.getAsJsonArray()) {
                campuses.add(campus.getAsString());
            }
        }

        else {
            System.out.println("campusList is not an array");
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getCampuses() {
        return campuses;
    }
}
