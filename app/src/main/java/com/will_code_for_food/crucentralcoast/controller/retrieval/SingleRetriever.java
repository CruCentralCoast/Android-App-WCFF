package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
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
    private boolean testMode = false;

    public RetrieverSchema getSchema() {
        return schema;
    }

    public SingleRetriever(RetrieverSchema rSchema) {
        schema = rSchema;
    }

    public SingleRetriever(RetrieverSchema rSchema, boolean testMode) {
        schema = rSchema;
        this.testMode = testMode;
    }

    public Content<T> getAll() {
        Content<T> content;

        if (testMode) {
            return getTestContent();
        } else if ((content = getLiveContent()).getType() == ContentType.LIVE) {
            Log.e("RETRIEVAL", "getting live content");
            return content;
        } else {
            Log.e("RETRIEVAL", "getting cached content");
            return getCachedContent();
        }
    }

    /**
     * Tries to get content from the database, returns null if the database was unreachable.
     */
    private Content<T> getLiveContent() {
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

            // cache new live data
            Cache cache = Cache.getCacheForObjectType(schema.getObjClass());
            if (cache != null) {
                new CacheTool<T>().cache(cache, objects);
            }

            return new Content<>(objects, ContentType.LIVE);
        } else {
            return new Content<T>(new ArrayList<T>(), ContentType.CACHED);
        }
    }

    /**
     * Tries to get cached data of the correct type, returns null if there
     * was an error or if there was no cached data
     */
    private Content<T> getCachedContent() {
        Cache cache = Cache.getCacheForObjectType(schema.getObjClass());
        if (cache != null) {
            return new CacheTool<T>().uncache(cache);
        } else {
            return new Content<>(null, ContentType.CACHED);
        }
    }

    private Content<T> getTestContent() {
        return new Content<>(null, ContentType.TEST);
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
