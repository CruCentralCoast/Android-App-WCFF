package com.will_code_for_food.crucentralcoast.model.common.common.users;

import android.graphics.Path;
import android.provider.ContactsContract;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Created by Gavin on 11/12/2015.
 */
public class User extends DatabaseObject{
    private final String name;
    private final String number;

    public User(final String name, final String number) {
        super(new JsonObject());
        this.name = name;
        this.number= number;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return number;
    }

    public boolean notify(final String msg) {
        // TODO send a text to the user, containing the message
        return true;
    }

    public static JsonObject toJSON(String name, String phone, String gcm_id, String direction) {
        JsonObject thisObj = new JsonObject();

        thisObj.add(Database.JSON_KEY_USER_NAME, new JsonPrimitive(name));
        thisObj.add(Database.JSON_KEY_USER_PHONE, new JsonPrimitive(phone));
        thisObj.add(Database.JSON_KEY_USER_GCM, new JsonPrimitive(gcm_id));
        thisObj.add(Database.JSON_KEY_USER_DIRECTION, new JsonPrimitive(direction));

        return thisObj;
    }
}
