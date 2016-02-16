package com.will_code_for_food.crucentralcoast.model.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.ArrayList;

/**
 * Created by MasonStevenson on 11/15/2015.
 */
public class Resource extends DatabaseObject {

    private String url;
    private String type;
    private String date;
    private String title;
    private ArrayList<String> tags;

    public Resource(JsonObject obj) {
        super(obj);

        refreshFields();
    }

    public void refreshFields() {
        JsonArray tagsArray;

        url = getFieldAsString("url");
        type = getFieldAsString("type");
        date = getFieldAsString("date");
        title = getFieldAsString("title");
        tags = new ArrayList<String>();

        if (getField("tags").isJsonArray()) {
            tagsArray = getField("tags").getAsJsonArray();

            for (JsonElement tag : tagsArray) {
                if (tag.isJsonPrimitive()) {
                    tags.add(tag.getAsString());
                }
            }
        }
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }


}
