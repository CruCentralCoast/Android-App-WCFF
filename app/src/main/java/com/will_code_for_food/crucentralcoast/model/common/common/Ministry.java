package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 *
 * A Cru Ministry is a subgroup of the Cru organization usually associated with one or more campuses.
 */
public class Ministry extends JsonDatabaseObject {
    private ArrayList<String> campuses;
    private ArrayList<String> campusesName;

    public Ministry(JsonObject obj) {
        super(obj);

        campuses = new ArrayList<String>();
        campusesName = new ArrayList<String>();

        JsonElement campusList = this.getField(Database.JSON_KEY_MINISTRY_CAMPUSES);

        if (campusList.isJsonArray()) {
            int count = campusList.getAsJsonArray().size();
            for (JsonElement campus : campusList.getAsJsonArray()) {
                campuses.add(campus.getAsString());
            }

        } else {
            System.out.println("campusList is not an array");
        }
    }

    public ArrayList<String> getCampuses() {
        return campuses;
    }

}
