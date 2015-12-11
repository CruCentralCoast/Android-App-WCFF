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
    private HashMap<String, String> fields;

    public DatabaseObject(JsonObject obj) {
        fields = new HashMap<String, String>();
        load(obj);
    }

    public String get(String fieldName) {
        return fields.get(fieldName); //will update this to use resIds later
    }

    public String getId() {
        return id;
    }

    private void load(JsonObject obj) {
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {

            if (entry.getValue().isJsonPrimitive()) {
                fields.put(entry.getKey(), entry.getValue().getAsString()); // this will have to be modified to deal with sub-arrays
            }
        }
    }

    //old
    /*
    public abstract String getId();
    public boolean update() {
        // TODO handles updating the object in the database
        return false;
    }
    public boolean delete() {
        // TODO deletes the object from the database
        return false;
    }*/

    /**
     * Gets ministry teams from the DB API and constructs their corresponding objects.
     * @return
     */
    /*public Collection<MinistryTeam> getMinistryTeams() {
        return null;
    }*/
}
