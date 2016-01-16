package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;

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
        return getFieldAsString(Util.getString(R.string.json_key_common_id));
    }

    public String getName() {
        return getFieldAsString(Util.getString(R.string.json_key_common_name));
    }

    public String getImage() {
        if (fields.containsKey(Util.getString(R.string.json_key_common_image))) {
            JsonObject imageObject = fields.get(Util.getString(R.string.json_key_common_image)).getAsJsonObject();
            String image = imageObject.get(Util.getString(R.string.json_key_common_image_url)).getAsString();
            return image;
        }

        else {
            return null;
        }
    }

    public String getDescription() {
        return getFieldAsString(Util.getString(R.string.json_key_common_description));
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
