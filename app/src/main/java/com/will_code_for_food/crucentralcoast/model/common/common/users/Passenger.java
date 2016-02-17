package com.will_code_for_food.crucentralcoast.model.common.common.users;

import android.graphics.Path;
import android.provider.ContactsContract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;

/**
 * Created by Gavin on 11/12/2015.
 */
public class Passenger extends DatabaseObject{
    private String name;
    private String number;
    private String gcmId;
    private RideDirection direction;

    public Passenger(JsonObject obj) {
        super(obj);

        refreshFields();;
    }

    public Passenger(final String name, final String number, String gcmId, RideDirection direction) {
        super(new JsonObject());
        this.name = name;
        this.number= number;
        this.gcmId = gcmId;
        this.direction = direction;
    }

    public void refreshFields() {
        this.name = getFieldAsString("name");
        this.number = getFieldAsString("phone");
        this.gcmId = getFieldAsString("gcm_id");
        this.direction = RideDirection.fromString(getFieldAsString("direction"));
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

    public void addToDb() {
        this.update(RestUtil.create(this.toJSON(), Database.REST_RIDE)); //updates the JSON object held by the parent class
        this.refreshFields(); //updates the fields for this class based on the parent class
    }

    public JsonObject toJSON() {
        JsonObject thisObj = new JsonObject();

        thisObj.add(Database.JSON_KEY_USER_NAME, new JsonPrimitive(name));
        thisObj.add(Database.JSON_KEY_USER_PHONE, new JsonPrimitive(number));
        thisObj.add(Database.JSON_KEY_USER_GCM, new JsonPrimitive(gcmId));
        thisObj.add(Database.JSON_KEY_USER_DIRECTION, new JsonPrimitive(direction.toString()));

        return thisObj;
    }

    public static JsonObject toJSON(String name, String phone, String gcm_id, String direction) {
        JsonObject thisObj = new JsonObject();

        thisObj.add(Database.JSON_KEY_USER_NAME, new JsonPrimitive(name));
        thisObj.add(Database.JSON_KEY_USER_PHONE, new JsonPrimitive(phone));
        thisObj.add(Database.JSON_KEY_USER_GCM, new JsonPrimitive(gcm_id));
        thisObj.add(Database.JSON_KEY_USER_DIRECTION, new JsonPrimitive(direction));

        return thisObj;
    }

    public static JsonObject toJSON(String id, String name, String phone, String gcm_id, String direction) {
        JsonObject thisObj = toJSON(name, phone, gcm_id, direction);
        thisObj.add(Database.JSON_KEY_COMMON_ID, new JsonPrimitive(id));

        return thisObj;
    }
}
