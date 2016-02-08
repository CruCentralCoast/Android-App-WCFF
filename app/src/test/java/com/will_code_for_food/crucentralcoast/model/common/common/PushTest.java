package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/28/2016.
 */
public class PushTest extends TestCase {

    /*
    public void testAddRide() {
        if(RestUtil.addPassenger("56b3d0b93c08bb4638977aca", "56abe3b9380cb03577ab0ee2")) {
            System.out.println("action was successful");
        } else {
            System.out.println("action was not successful");
        }
    }*/

    /*
    public void testAddMultiple() {
        RestUtil.addPassenger("56b3d0b93c08bb4638977aca", "56abe35b380cb03577ab0ee0");
        RestUtil.addPassenger("56b3d0b93c08bb4638977aca", "56abe38a380cb03577ab0ee1");
        RestUtil.addPassenger("56b3d0b93c08bb4638977aca", "56abe3b9380cb03577ab0ee2");
    }*/

    /*
    public void testRemoveRide() {
        if(RestUtil.dropPassenger("56b3d0b93c08bb4638977aca", "56abe38a380cb03577ab0ee1")) {
            System.out.println("action was successful");
        }
    }*/

    /*
    public void testPushRide() {
        String result;
        JsonObject myRide = Ride.toJSON("5660ae43adfa94d6d887ca51", "obama", "1111111", "dummy_key", new Location("12345", "CA", "", "123 Main Street", "USA"), "2016-01-01T00:00:00Z", 2.5, 4, RideDirection.TWO_WAY, "male");

        System.out.println("Sending this object:\n" + myRide.toString());

        result = RestUtil.create(myRide, Database.REST_RIDE).toString();
        System.out.println("The database gave the object the following id: " + result);
    }*/

    /*
    public void testUpdate() {
        String result;
        JsonObject myRide = new JsonObject();
        myRide.add("_id", new JsonPrimitive("56b90aa901233bea519f4534"));
        myRide.add("event", new JsonPrimitive("5660ae43adfa94d6d887ca51"));
        myRide.add("driverName", new JsonPrimitive("joe biden"));
        myRide.add("driverNumber", new JsonPrimitive("1111111"));
        myRide.add("gcm_id", new JsonPrimitive("dummy_key"));

        System.out.println("Sending this object:\n" + myRide.toString());

        result = RestUtil.update(myRide, Database.REST_RIDE).toString();
        System.out.println("The database gave the object the following id: " + result);
    }*/
}
