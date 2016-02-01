package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.users.User;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * A ride, stored in the database.
 */
public class Ride extends DatabaseObject {
    /**
     * these could be final, but it would mean
     * creating a JSON object in the second
     * constructor to satisfy the need to call
     * super()
     */
    private String eventId;
    private User driver;
    private int numSeats;
    private RideDirection direction;
    private String location;
    private List<User> riders;

    /**
     * The inherited constructor, used to build a ride when
     * it's pulled from the database.
     */
    public Ride(final JsonObject obj) {
        super(obj);
        riders = new ArrayList<User>();

        // TODO get these from JSON Object (placeholders for now)
        driver = null;
        eventId = "";
        numSeats = 5;
        direction = RideDirection.ONE_WAY_FROM_EVENT;
        location = "";
    }

    /**
     * Manually building a ride based on it's fields, which will
     * be converted into a JSON object like the ones stored in
     * the database, and created using the inherited constructor.
     */
    public Ride(final Event event, final User driver, final int numSeats, final String location,
                final RideDirection direction) {
        super(new JsonObject()); // satisfies need to call super

        this.eventId = event.getId();
        this.driver = driver;
        this.numSeats = numSeats;
        this.location = location;
        this.direction = direction;
        riders = new ArrayList<User>();
    }

    public boolean isFull() {
        return getNumAvailableSeats() > 0;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumAvailableSeats() {
        return numSeats - 1 - riders.size();
    }

    public boolean addRider(final User rider) {
        if (!isFull()) {
            riders.add(rider);
        }
        driver.notify(Resources.getSystem().getString(R.string.ridesharing_driver_notification));
        return false;
    }

    public String getEventId() {
        return eventId;
    }

    public User getDriver() {
        return driver;
    }

    public Long getLeaveTimeToEvent() {
        return direction.getLeaveTimeToEvent();
    }

    public Long getLeaveTimeFromEvent() {
        return direction.getLeaveTimeFromEvent();
    }

    public String getLocation() {
        return location;
    }

    public List<User> getRiders() {
        return riders;
    }

    public boolean isTwoWay() {
        return direction.equals(RideDirection.TWO_WAY);
    }

    public boolean isToEvent() {
        return direction.hasTimeLeavingToEvent();
    }

    public boolean isFromEvent() {
        return direction.hasTimeLeavingFromEvent();
    }

    //Example of how to add a new Ride to the database and store it in a ride object for later use:
    // Ride newRide = new Ride(RestUtil.create(Ride.toJSON(event, driver, seats, loc, dir)));
    public static JsonObject toJSON(final Event event, final User driver, final int numSeats, final String location, final RideDirection direction) {

        JsonObject thisObj = new JsonObject();

        //TODO add missing fields
        //TODO implement gcm key retrieval

        thisObj.add(Database.JSON_KEY_RIDE_EVENT, new JsonPrimitive(event.getId()));
        thisObj.add(Database.JSON_KEY_RIDE_DRIVER_NAME, new JsonPrimitive(driver.getName()));
        thisObj.add(Database.JSON_KEY_RIDE_DRIVER_NUMBER, new JsonPrimitive(driver.getPhoneNumber()));
        thisObj.add(Database.JSON_KEY_RIDE_GCM, new JsonPrimitive("dummy_key"));
        thisObj.add(Database.JSON_KEY_RIDE_LOCATION, new JsonPrimitive(location));
        //thisObj.add(Database.JSON_KEY_RIDE_TIME, );
        //thisObj.add(Database.JSON_KEY_RIDE_RADIUS, );
        thisObj.add(Database.JSON_KEY_RIDE_SEATS, new JsonPrimitive(Integer.toString(numSeats)));
        thisObj.add(Database.JSON_KEY_RIDE_DIRECTION, new JsonPrimitive(direction.toString()));
        //thisObj.add(Database.JSON_KEY_RIDE_GENDER, );
        return thisObj;
    }
}
