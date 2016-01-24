package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A ride, stored in the database.
 */
public class Ride extends DatabaseObject {
    private final String eventId;
    private final User driver;
    private final int numSeats;

    private long leaveTimeToEvent;
    private String location;
    private List<User> riders;
    private final boolean oneWayToEvent;

    private final boolean twoWay;
    private final boolean oneWayFromEvent;
    private long leaveTimeFromEvent;

    public Ride(final JsonObject obj) {
        super(obj);
        riders = new ArrayList<User>();

        // TODO get these from JSON Object
        driver = null;
        eventId = "";
        numSeats = 5;
        twoWay = false;             // these should be exclusive
        oneWayFromEvent = false;
        oneWayToEvent = true;
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
        if (!oneWayFromEvent) {
            return leaveTimeToEvent;
        }
        return null;
    }

    public Long getLeaveTimeFromEvent() {
        if (!oneWayToEvent) {
            return leaveTimeFromEvent;
        }
        return null;
    }

    public String getLocation() {
        return location;
    }

    public List<User> getRiders() {
        return riders;
    }

    public boolean isTwoWay() {
        return twoWay;
    }

    public boolean isToEvent() {
        return twoWay || oneWayToEvent;
    }

    public boolean isFromEvent() {
        return twoWay || oneWayFromEvent;
    }
}
