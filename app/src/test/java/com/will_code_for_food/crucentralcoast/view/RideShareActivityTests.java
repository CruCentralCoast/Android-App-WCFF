package com.will_code_for_food.crucentralcoast.view;

import android.provider.CalendarContract;
import android.test.ActivityInstrumentationTestCase2;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareActivity;

import java.util.ArrayList;

/**
 * Created by ShelliCrispen on 3/10/16.
 */
public class RideShareActivityTests extends ActivityInstrumentationTestCase2<RideShareActivity> {

    RideShareActivity activity;
    ArrayList<Event> events;
    Ride ride;
    String eventId = "00000";
    String driverName = "Bob Mean";
    String driverNumber = "9999998888";
    String gcmId = "5";
    Location location = null;
    Double radius = 10.0;
    Integer numSeats = 5;
    int numSeat = numSeats;
    RideDirection direction = RideDirection.TWO_WAY;
    String gender = "MALE";

    public RideShareActivityTests() {super(RideShareActivity.class);}

    public RideShareActivityTests(Class<RideShareActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testSetRide(){
        String eventId = "11111";
        String driverName = "Bob Nice";
        String driverNumber = "7777777777";
        String gcmId = "4";
        Location location = null;
        Double radius = 5.0;
        Integer numSeats = 4;
        RideDirection direction = RideDirection.TWO_WAY;
        String gender = "MALE";
        assertNotNull(activity);

        Ride testRide = new Ride(eventId, driverName, driverNumber, gcmId,
                location, radius, numSeats, direction, gender);
        assertNotNull(testRide);
        activity.setRide(ride);
        assertNotNull(activity.getRide());
        assertEquals(testRide, activity.getRide());
    }

    public void testGetRide(){
        Ride testGetRide = new Ride(eventId, driverName, driverNumber, gcmId,
                location, radius, numSeats, direction, gender);

        activity.setRide(testGetRide);
        assertNotNull(activity.getRide());
        Ride test = activity.getRide();
        assertEquals(eventId, test.getEventId());
        assertEquals(driverName, test.getDriverName());
        assertEquals(driverNumber, test.getDriverNumber());
        assertEquals(gcmId, test.getGcmId());
        assertEquals(location, test.getLocation());
        assertEquals(radius, test.getRadius());
        assertEquals(numSeat, test.getNumSeats());
        assertEquals(direction, test.getDirection());
        assertEquals(gender, test.getGender());
    }
}
