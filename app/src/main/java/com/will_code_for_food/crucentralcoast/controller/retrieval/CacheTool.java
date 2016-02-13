package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Caches objects of a certain type
 */
public class CacheTool<T extends DatabaseObject> {
    /**
     * Caches a list of database objects to a file, specified by the cache
     */
    public boolean cache(final Cache cache,
                                final List<T> list) {
        List<String> stringList = new ArrayList<>();
        for (T obj : list) {
            HashMap<String, JsonElement> map = obj.getJsonEntrySet();
            for (String key : map.keySet()) {
                stringList.add(key + LocalStorageIO.HASHMAP_DELIMITER + map.get(key));
            }
            stringList.add(" ");
        }
        return LocalStorageIO.writeList(stringList, cache.fname);
    }

    /**
     * Gets cached data from a cache, or null if there was an error
     */
    public Content<T> uncache(final Cache cache) {
        List<String> stringList = LocalStorageIO.readList(cache.fname);
        List<T> objects = new ArrayList<>();
        if (stringList != null) {
            // get constructor for the type of cached object
            Constructor<T> constructor = null;
            try {
                constructor = cache.objectType.getConstructor(JsonObject.class);
            } catch (NoSuchMethodException ex) {
                Log.e("Cache Error", "Could not get JSON constructor for " + cache.objectType);
            } finally {
                if (constructor == null) {
                    Log.e("Cache Error", "Error getting constructor for " + cache.objectType);
                    return null;
                }
            }
            JsonObject json = new JsonObject();
            // create objects
            for (String string : stringList) {
                if (string.trim().length() > 0) {
                    // add fields to current object
                    String[] line = string.split("\\" + LocalStorageIO.HASHMAP_DELIMITER);
                    json.add(line[0], new JsonParser().parse(line[1]));
                } else {
                    // object finished
                    try {
                        objects.add(constructor.newInstance(json));
                    } catch (Exception ex) {
                        Log.e("Cache Error", "Could not create " + cache.objectType + " object");
                    }
                    json = new JsonObject();
                }
            }
            return new Content<>(objects, ContentType.CACHED);
        } else {
            Log.e("Cache Error", "Could not read from cache " + cache);
            return null;
        }
    }
}
