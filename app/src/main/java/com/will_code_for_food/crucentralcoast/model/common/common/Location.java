package com.will_code_for_food.crucentralcoast.model.common.common;

import android.provider.ContactsContract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MasonJStevenson on 1/18/2016.
 * <p/>
 * Holds location data associated with a DatabaseObject
 */
public class Location {
    private String postcode;
    private String state;
    private String suburb;
    private String street;
    private String country;
    private JsonObject fields;

    //for testing
    public Location(String postcode, String state, String suburb, String street, String country) {
        this.postcode = postcode;
        this.state = state;
        this.suburb = suburb;
        this.street = street;
        this.country = country;
    }

    public Location(JsonElement locElement) {

        if (locElement.isJsonObject()) {
            fields = locElement.getAsJsonObject();

            postcode = getFieldAsString(Database.JSON_KEY_COMMON_LOCATION_POSTCODE);
            state = getFieldAsString(Database.JSON_KEY_COMMON_LOCATION_STATE);
            suburb = getFieldAsString(Database.JSON_KEY_COMMON_LOCATION_SUBURB);
            street = getFieldAsString(Database.JSON_KEY_COMMON_LOCATION_STREET);
            country = getFieldAsString(Database.JSON_KEY_COMMON_LOCATION_COUNTRY);

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
        } else {
            return null;
        }
    }

    public String getPostcode() {
        return postcode;
    }

    public String getState() {
        return state;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getStreet() {
        return street;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object other) {

        Location otherLoc;

        if (other instanceof Location) {
            otherLoc = (Location) other;

            return (this.country.equals(otherLoc.getCountry())) &&
                    (this.street.equals(otherLoc.getStreet())) &&
                    (this.suburb.equals(otherLoc.getSuburb())) &&
                    (this.state.equals(otherLoc.getState())) &&
                    (this.postcode.equals(otherLoc.getPostcode()));
        }

        return false;
    }

    public JsonObject toJSON() {
        JsonObject thisObj = new JsonObject();
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_POSTCODE, new JsonPrimitive(postcode));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_STATE, new JsonPrimitive(state));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_SUBURB, new JsonPrimitive(suburb));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_STREET, new JsonPrimitive(street));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_COUNTRY, new JsonPrimitive(country));
        return thisObj;
    }
}
