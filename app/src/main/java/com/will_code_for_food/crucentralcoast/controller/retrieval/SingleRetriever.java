package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Retrieves a single type of DatabaseObject.
 */
public class SingleRetriever<T extends DatabaseObject> implements Retriever {
    private RetrieverSchema schema;

    public RetrieverSchema getSchema() {
        return schema;
    }

    public SingleRetriever(RetrieverSchema rSchema)
    {
        schema = rSchema;
    }

    public List<T> getAll() {
        JsonArray json;
        List<T> objects = new ArrayList<T>();

        Class objClass = schema.getObjClass();
        Constructor constructor = null;
        try {
            constructor = objClass.getConstructor(JsonObject.class);
        } catch (NoSuchMethodException ex) {
            // ERROR getting constructor from the provided class
            ex.printStackTrace();
        }

        json = RestUtil.get(schema.getTableName());

        if (json != null) {
            for (JsonElement jsonElement : json) {
                T dbObject = getObjectFromJson(jsonElement, constructor);
                objects.add(dbObject);
            }
        }

        return objects;
    }

    private T getObjectFromJson(JsonElement jsonElement, Constructor constructor)
    {
        if (jsonElement.isJsonObject())
        {
            try {
                return (T)constructor.newInstance(jsonElement.getAsJsonObject());
            } catch (InstantiationException ex) {
                // ERROR instantiating database object
                // Note: Can't multi-catch for reflection because we support old phones
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                // ERROR instantiating database object
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                // ERROR instantiating database object
                ex.printStackTrace();
            }
        }
        return null;
    }

}