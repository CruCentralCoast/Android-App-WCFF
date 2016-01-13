package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by MasonJStevenson on 1/12/2016.
 */
public class MinistryRetriever implements Retriever {

    public ArrayList<DatabaseObject> getAll() {
        JsonArray ministriesJson;
        Iterator<JsonElement> iterator;
        ArrayList<DatabaseObject> ministries = new ArrayList<DatabaseObject>();
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
