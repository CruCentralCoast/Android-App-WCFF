package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gavin on 1/12/2016.
 */
public abstract class Retriever2 {

    public ArrayList<DatabaseObject> getAll() {
        JsonArray json;
        Iterator<JsonElement> iterator;
        ArrayList<DatabaseObject> objects = new ArrayList<DatabaseObject>();
        JsonObject temp;

        Class objClass = getDatabaseObjectClass();
        Constructor constructor = null;
        try {
            constructor = objClass.getConstructor(JsonObject.class);
        } catch (NoSuchMethodException ex) {
            // ERROR getting constructor from the provided class
            ex.printStackTrace();
        }

        json = RestUtil.getAll(getJSONDatabaseString());

        if (json != null) {
            iterator = json.iterator();

            while (iterator.hasNext()) {
                temp = iterator.next().getAsJsonObject();
                try {
                    objects.add((DatabaseObject)constructor.newInstance(temp));
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
        }

        return objects;
    }

    protected abstract Class getDatabaseObjectClass();
    protected abstract String getJSONDatabaseString();
}
