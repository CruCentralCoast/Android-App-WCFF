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

    public ArrayList<String> getCampuses() { return campuses; }

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

        //printMinistries(ministries); //for testing

        return ministries;
    }

    private static void printMinistries(ArrayList<Ministry> ministries) {

        System.out.println("Ministries Retrieved:");
        for (Ministry ministry : ministries) {
            System.out.println("Name: " + ministry.getName() + "\n    ID: " + ministry.getId());
            System.out.println("    Campuses:");

            for (String campus : ministry.getCampuses()) {
                System.out.println("        " + campus);
            }
        }
    }
}
