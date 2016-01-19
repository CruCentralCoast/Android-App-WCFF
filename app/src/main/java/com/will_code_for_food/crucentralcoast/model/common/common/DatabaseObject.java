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

    private final JsonObject fields;
    private ImageData imageData;

    public DatabaseObject(JsonObject obj) {
        fields = obj;

        if (fields.has(Database.JSON_KEY_COMMON_IMAGE)) {
            imageData = new ImageData(fields.get(Database.JSON_KEY_COMMON_IMAGE));
        }
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

    /**
     * DEPRECATED: USE getImageData
     * Gets the url for the image associated with this DatabaseObject
     */
    public String getImage() {
        if (imageData != null) {
            return imageData.getUrl();
        }

        else return null;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public String getDescription() {
        return getFieldAsString(Database.JSON_KEY_COMMON_DESCRIPTION);
    }
}
