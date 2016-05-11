package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by Brian on 5/10/2016.
 */
public interface DatabaseObject {
    void update(JsonObject obj);

    String getFieldAsString(String fieldName);

    Double getFieldAsDouble(String fieldName);

    Integer getFieldAsInt(String fieldName);

    JsonElement getField(String fieldName);

    String getId();

    String getName();

    String getImage();

    ImageData getImageData();

    String getDescription();

    Map<String, String> getEntrySet();

    @Override
    boolean equals(Object other);
}
