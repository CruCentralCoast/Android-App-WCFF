package com.will_code_for_food.crucentralcoast.model.common.common;

import android.provider.ContactsContract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MasonJStevenson on 1/18/2016.
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

            postcode = fields.get(Database.JSON_KEY_COMMON_LOCATION_POSTCODE).getAsString();
            state = fields.get(Database.JSON_KEY_COMMON_LOCATION_STATE).getAsString();
            suburb = fields.get(Database.JSON_KEY_COMMON_LOCATION_SUBURB).getAsString();
            street = fields.get(Database.JSON_KEY_COMMON_LOCATION_STREET).getAsString();
            country = fields.get(Database.JSON_KEY_COMMON_LOCATION_COUNTRY).getAsString();

        }
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}
