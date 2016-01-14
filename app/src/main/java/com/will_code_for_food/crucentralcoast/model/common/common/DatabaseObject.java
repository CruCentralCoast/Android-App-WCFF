package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 */
public abstract class DatabaseObject {

    private String id;
    private String name;
    private String image;
    private String description;
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

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    private void load(JsonObject obj) {
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            fields.put(entry.getKey(), entry.getValue());
        }

        if (fields.containsKey("_id")) {
            id = fields.get("_id").getAsString();
        }

        if (fields.containsKey("name")) {
            name = fields.get("name").getAsString();
        }

        if (fields.containsKey("image")) {
            JsonObject imageObject = fields.get("image").getAsJsonObject();
            image = imageObject.get("url").getAsString();
        }

        if (fields.containsKey("description")) {
            description = fields.get("description").getAsString();
        }
    }
}
