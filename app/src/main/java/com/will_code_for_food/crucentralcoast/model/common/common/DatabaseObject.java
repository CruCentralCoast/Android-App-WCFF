package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 */
public abstract class DatabaseObject {

    private String id;
    private HashMap<String, JsonElement> fields;

    public DatabaseObject(JsonObject obj) {
        fields = new HashMap<String, JsonElement>();
        load(obj);
    }

    /**
     * Gets a field from this object as a String. If the field can't be represented as a String
     * or if it does not exist, returns null.
     */
    public String getString(String fieldName) {

        JsonElement toReturn = fields.get(fieldName);

        if (toReturn != null && toReturn.isJsonPrimitive()) {
            return toReturn.getAsString();
        }

        else {
            return null;
        }
    }

    public JsonElement get(String fieldName) {
        return fields.get(fieldName);
    }

    //Trying to create a generic get method- its not working at the moment...
    /*
    public static ArrayList<Object> getAll(Class type, int table) {
        JsonArray arrayJson;
        Iterator<JsonElement> iterator;
        ArrayList<Object> toReturn = new ArrayList<Object>();
        JsonObject temp;

        arrayJson = RestUtil.getAll(Util.getString(table));

        if (arrayJson != null) {
            iterator = arrayJson.iterator();

            while (iterator.hasNext()) {
                temp = iterator.next().getAsJsonObject();
                toReturn.add(type.getDeclaredConstructor().newInstance());
            }
        }

        return toReturn;
    }*/

    public String getId() {
        return id;
    }

    private void load(JsonObject obj) {
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            fields.put(entry.getKey(), entry.getValue());
        }

        if (fields.containsKey("_id")) {
            id = fields.get("_id").getAsString();
        }
    }
}
