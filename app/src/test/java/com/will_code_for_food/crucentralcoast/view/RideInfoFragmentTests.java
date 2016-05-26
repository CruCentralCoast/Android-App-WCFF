package com.will_code_for_food.crucentralcoast.view;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideInfoFragment;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareActivity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kayla on 4/29/2016.
 */
public class RideInfoFragmentTests extends ActivityInstrumentationTestCase2<RideShareActivity> {

    RideShareActivity activity;

    public RideInfoFragmentTests() {
        super(RideShareActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Test
    public void testEventInfo() {
        RideInfoFragment fragment = new RideInfoFragment();
        TextView textView = new TextView(activity);
        Event event = new Event("");
        event.setField(Database.JSON_KEY_COMMON_NAME, new JsonPrimitive("TEST EVENT"));

        fragment.setRideEventInfo(textView, event);
        Assert.assertEquals("Event: TEST EVENT", textView.getText());
    }

    @Test
    public void testDriverInfo() {
        RideInfoFragment fragment = new RideInfoFragment();
        TextView textView = new TextView(activity);
        Ride ride = new Ride("", "driver-name", "", "", null, 0.0, 0, null, "");

        fragment.setRideDriverInfo(textView, ride);
        Assert.assertEquals("Driver: driver-name", textView.getText());
    }

    @Test
    public void testLocationInfo() {
        RideInfoFragment fragment = new RideInfoFragment();
        TextView textView = new TextView(activity);
        Location location = new Location("", "", "", "street", "");
        Ride ride = new Ride("", "", "", "", location, 0.0, 0, null, "");

        String url = fragment.setRideLocationInfo(textView, ride);
        Assert.assertEquals("Location: street", textView.getText());
        Assert.assertEquals(Database.GOOGLE_MAP + "street", url);

        location = new Location(null, null, null, null, null);
        ride = new Ride("", "", "", "", location, 0.0, 0, null, "");
        url = fragment.setRideLocationInfo(textView, ride);
        Assert.assertEquals("Location: unknown", textView.getText());
        Assert.assertEquals(Database.GOOGLE_MAP + "", url);
    }
}