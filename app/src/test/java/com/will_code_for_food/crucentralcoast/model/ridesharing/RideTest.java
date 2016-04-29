package com.will_code_for_food.crucentralcoast.model.ridesharing;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

/**
 * Tests the Ride model class
 */
public class RideTest extends WCFFUnitTest {
    private final String id = "id";
    private final String dName = "driver";
    private final String dNumber = "1234567890";
    private final String gcm = "gcmId";
    private final Location loc = new Location("", "", "", "", "");
    private final double radius = 5.0;
    private final int seats = 3;
    private RideDirection dir = null;
    private final String gen = "female";
    private final String time = "2016-01-28T22:33:00.481Z";

    @Before
    public void setup() {
        dir = RideDirection.ONE_WAY_TO_EVENT;
        dir.setLeaveTimeToEvent(Calendar.getInstance());
    }

    @Test
    public void testConstructors() {
        Ride ride = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, seats, dir, gen));
        Assert.assertEquals(id, ride.getEventId());
        Assert.assertEquals(dName, ride.getDriverName());
        Assert.assertEquals(dNumber, ride.getDriverNumber());
        Assert.assertEquals(gcm, ride.getGcmId());
        Assert.assertEquals(loc, ride.getLocation());
        Assert.assertEquals(radius, ride.getRadius());
        Assert.assertEquals(seats, ride.getNumSeats());
        Assert.assertEquals(dir, ride.getDirection());
        Assert.assertEquals(gen, ride.getGender());
    }

    @Test
    public void testJSON() {
        Ride ride1 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, seats, dir, gen));
        JsonObject obj = ride1.toJSON();
        Ride ride2 = new Ride(obj);
        Assert.assertEquals(ride1, ride2);
    }

    @Test
    public void testEquals() {
        Ride ride1 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, seats, dir, gen));
        Ride ride2 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, seats, dir, gen));
        Assert.assertEquals(ride1, ride2);
        ride2 = new Ride(Ride.toJSON("", "", dName, dNumber, gcm, loc, time, radius, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", "", dNumber, gcm, loc, time, radius, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", dName, "", gcm, loc, time, radius, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", dName, dNumber, "", loc, time, radius, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, new Location("z", "", "", "", ""),
                time, radius, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, "", radius, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, -1.0, seats, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 = new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, 0, dir, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 =  new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, seats,
                RideDirection.TWO_WAY, gen));
        Assert.assertFalse(ride1.equals(ride2));
        ride2 =  new Ride(Ride.toJSON(id, "", dName, dNumber, gcm, loc, time, radius, seats, dir, "male"));
        Assert.assertFalse(ride1.equals(ride2));

        Assert.assertFalse(ride1.equals(null));
        Assert.assertFalse(ride1.equals("cat"));
    }
}
