package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 */
public abstract class DatabaseObject {

    private final HashMap<String, JsonElement> fields;

    public DatabaseObject(JsonObject obj) {
        fields = new HashMap<String, JsonElement>();
        load(obj);
    }

    /**
     * Gets a field from this object as a String. If the field can't be represented as a String
     * or if it does not exist, returns null.
     */
    public String getFieldAsString(String fieldName) {

        JsonElement fieldValue = fields.get(fieldName);

        if (fieldValue != null && fieldValue.isJsonPrimitive()) {
            return fieldValue.getAsString();
        }

        else {
            return null;
        }
    }

    /**
     * Returns the corresponding JsonElement for key fieldName, or null if that key does not exist.
     */
    public JsonElement getField(String fieldName) {
        return fields.get(fieldName);
    }

    public String getId() {
        return getFieldAsString(Database.JSON_KEY_COMMON_ID);
    }

    public String getName() {
        return getFieldAsString(Database.JSON_KEY_COMMON_NAME);
    }

    public String getImage() {
        if (fields.containsKey(Database.JSON_KEY_COMMON_IMAGE)) {
            JsonObject imageObject = fields.get(Database.JSON_KEY_COMMON_IMAGE).getAsJsonObject();
            String image = imageObject.get(Database.JSON_KEY_COMMON_IMAGE_URL).getAsString();
            return image;
        }

        else {
            return null;
        }
    }

    public String getDescription() {
        return getFieldAsString(Database.JSON_KEY_COMMON_DESCRIPTION);
    }

    /**
     * Converts the JsonObject for this DatabaseObject into a hashmap.
     */
    private void load(JsonObject obj) {
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            fields.put(entry.getKey(), entry.getValue());
        }
    }
}
