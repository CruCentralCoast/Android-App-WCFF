package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by Gavin on 11/12/2015.
 * Updated by Mason on 11/22/2015
 */
public abstract class JsonDatabaseObject implements DatabaseObject {

    protected JsonObject fields;
    private ImageData imageData;

    //for testing...
    public JsonDatabaseObject(String id) {
        fields = new JsonObject();

        fields.add(Database.JSON_KEY_COMMON_ID, new JsonPrimitive(id));
    }

    public JsonDatabaseObject(JsonObject obj) {
        update(obj);
    }

    @Override
    public void update(JsonObject obj) {
        fields = obj;

        if (fields.has(Database.JSON_KEY_COMMON_IMAGE)) {
            imageData = new ImageData(fields.get(Database.JSON_KEY_COMMON_IMAGE));
        }
    }

    public void setField(String key, JsonElement element) {
        fields.add(key, element);
    }

    /**
     * Gets a field from this object as a String. If the field can't be represented as a String
     * or if it does not exist, returns null.
     */
    @Override
    public String getFieldAsString(String fieldName) {

        JsonElement fieldValue = fields.get(fieldName);

        if (fieldValue != null && fieldValue.isJsonPrimitive()) {
            return fieldValue.getAsString();
        } else {
            return null;
        }
    }

    /**
     * Gets a field from this object as a Double. If the field can't be represented as a Double
     * or if it does not exist, returns null.
     */
    @Override
    public Double getFieldAsDouble(String fieldName) {
        String field = getFieldAsString(fieldName);

        try {
            if (field != null) {
                return Double.parseDouble(field);
            }
        } catch (NumberFormatException ex) {
            Logger.e("DatabaseObject", "NumberFormatException thrown in getFieldAsDouble() for the following field: " + fieldName);
        }

        return null;
    }

    /**
     * Gets a field from this object as a Integer. If the field can't be represented as a Integer
     * or if it does not exist, returns null.
     */
    @Override
    public Integer getFieldAsInt(String fieldName) {
        String field = getFieldAsString(fieldName);

        try {
            if (field != null) {
                return Integer.parseInt(field);
            }
        } catch (NumberFormatException ex) {
            Logger.e("DatabaseObject", "NumberFormatException thrown in getFieldAsInt() for the following field: " + fieldName);
        }

        return null;
    }

    /**
     * Returns the corresponding JsonElement for key fieldName, or null if that key does not exist.
     */
    @Override
    public JsonElement getField(String fieldName) {
        return fields.get(fieldName);
    }

    public HashMap<String, JsonElement> getJsonEntrySet() {
        HashMap<String, JsonElement> map = new HashMap<>();
        for (Entry<String, JsonElement> entry : fields.entrySet()) {
            if (entry.getValue().isJsonArray()) {
                map.put(entry.getKey(), entry.getValue().getAsJsonArray());
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    @Override
    public Map<String, String> getEntrySet(){
        HashMap<String, String> map = new HashMap<>();
        for (Entry<String, JsonElement> entry : fields.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }

    @Override
    public String getId() {
        return getFieldAsString(Database.JSON_KEY_COMMON_ID);
    }

    @Override
    public String getName() {
        return getFieldAsString(Database.JSON_KEY_COMMON_NAME);
    }

    /**
     * DEPRECATED: USE getImageData
     * Gets the url for the image associated with this DatabaseObject
     */
    @Override
    public String getImage() {
        if (imageData != null) {
            return imageData.getUrl();
        }

        return null;
    }

    @Override
    public ImageData getImageData() {
        return imageData;
    }

    @Override
    public String getDescription() {
        return getFieldAsString(Database.JSON_KEY_COMMON_DESCRIPTION);
    }

    @Override
    public boolean equals(Object other) {
        DatabaseObject dbObj;

        if (other instanceof JsonDatabaseObject) {
            dbObj = (DatabaseObject) other;
            return this.getId().equals(dbObj.getId());
        }

        //for comparing ids directly
        else if (other instanceof String) {
            return this.getId().equals(other);
        }

        return false;
    }
}
