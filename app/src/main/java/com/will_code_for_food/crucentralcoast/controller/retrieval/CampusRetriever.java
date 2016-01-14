package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by MasonJStevenson on 1/12/2016.
 */
public class CampusRetriever implements Retriever {

    public ArrayList<DatabaseObject> getAll() {
        JsonArray campusesJson;
        Iterator<JsonElement> iterator;
        ArrayList<DatabaseObject> campuses = new ArrayList<DatabaseObject>();
        JsonObject temp;

        campusesJson = RestUtil.getAll(Util.getString(R.string.rest_campus_all));

        if (campusesJson != null) {
            iterator = campusesJson.iterator();

            while (iterator.hasNext()) {
                temp = iterator.next().getAsJsonObject();
                campuses.add(new Campus(temp));
            }
        }

        return campuses;
    }
}
