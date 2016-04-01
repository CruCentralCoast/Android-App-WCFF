package com.will_code_for_food.crucentralcoast.model.common.common;

import android.location.Address;
import android.provider.ContactsContract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by MasonJStevenson on 1/18/2016.
 * <p/>
 * Holds location data associated with a DatabaseObject
 */
public class Location {
    //private String postcode;
    //private String state;
    //private String suburb;
    //private String street;
    //private String country;
    //private JsonObject fields;

    private Address address;

    //for testing
    /*
    public Location(String postcode, String state, String suburb, String street, String country) {
        this.postcode = postcode;
        this.state = state;
        this.suburb = suburb;
        this.street = street;
        this.country = country;
    }*/

    public Location(JsonElement locElement) {
        JsonObject fields;

        if (locElement.isJsonObject()) {
            fields = locElement.getAsJsonObject();

            address = new Address(Locale.US);
            address.setPostalCode(getFieldAsString(fields, Database.JSON_KEY_COMMON_LOCATION_POSTCODE));
            address.setAdminArea(getFieldAsString(fields, Database.JSON_KEY_COMMON_LOCATION_STATE));
            address.setLocality(getFieldAsString(fields, Database.JSON_KEY_COMMON_LOCATION_SUBURB));
            address.setAddressLine(0, getFieldAsString(fields, Database.JSON_KEY_COMMON_LOCATION_STREET));
            address.setCountryName(getFieldAsString(fields, Database.JSON_KEY_COMMON_LOCATION_COUNTRY));

        }
    }

    public Location(Address address) {
        this.address = address;
    }

    /**
     * Gets a field from this object as a String. If the field can't be represented as a String
     * or if it does not exist, returns null.
     */
    private String getFieldAsString(JsonObject fields, String fieldName) {

        JsonElement fieldValue = fields.get(fieldName);

        if (fieldValue != null && fieldValue.isJsonPrimitive()) {
            return fieldValue.getAsString();
        } else {
            return null;
        }
    }

    public String getPostcode() {
        return address.getPostalCode();
    }

    public String getState() {
        return address.getAdminArea();
    }

    public String getSuburb() {
        return address.getLocality();
    }

    public String getStreet() {
        return address.getAddressLine(0);
    }

    public String getCountry() {
        return address.getCountryName();
    }

    public double getLatitude() {
        return address.getLatitude();
    }

    public double getLongitude() {
        return address.getLongitude();
    }

    /*
    @Override
    public boolean equals(Object other) {

        Location otherLoc;

        if (other != null && other instanceof Location) {
            otherLoc = (Location) other;

            return (this.country.equals(otherLoc.getCountry())) &&
                    (this.street.equals(otherLoc.getStreet())) &&
                    (this.suburb.equals(otherLoc.getSuburb())) &&
                    (this.state.equals(otherLoc.getState())) &&
                    (this.postcode.equals(otherLoc.getPostcode()));
        }

        return false;
    }*/

    public JsonObject toJSON() {
        JsonObject thisObj = new JsonObject();
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_POSTCODE, new JsonPrimitive(getPostcode()));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_STATE, new JsonPrimitive(getState()));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_SUBURB, new JsonPrimitive(getSuburb()));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_STREET, new JsonPrimitive(getStreet()));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_COUNTRY, new JsonPrimitive(getCountry()));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_LATITUDE, new JsonPrimitive(getLatitude()));
        thisObj.add(Database.JSON_KEY_COMMON_LOCATION_LONGITUDE, new JsonPrimitive(getLongitude()));

        return thisObj;
    }
}
