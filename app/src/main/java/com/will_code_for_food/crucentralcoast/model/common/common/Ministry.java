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

    public static ArrayList<Ministry> getMinistries() {
        JsonArray ministriesJson;
        Iterator<JsonElement> iterator;
        ArrayList<Ministry> ministries = new ArrayList<Ministry>();
        JsonObject temp;

        ministriesJson = RestUtil.getAll(Util.getString(R.string.rest_ministry_all));

        if (ministriesJson != null) {
            iterator = ministriesJson.iterator();

            while (iterator.hasNext()) {
                temp = iterator.next().getAsJsonObject();
                ministries.add(new Ministry(temp));
            }
        }

        return ministries;
    }
}
