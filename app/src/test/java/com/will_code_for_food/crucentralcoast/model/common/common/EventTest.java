package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class EventTest extends TestCase {
    Event event1;

    public void setUp() throws Exception {
        super.setUp();
        JsonObject eventJson = new JsonObject();
        eventJson.addProperty("_id", "1");
        eventJson.addProperty("name", "Fall Retreat");
        eventJson.addProperty("startDate", "2016-06-08T00:00:00.000Z");
        eventJson.addProperty("endDate", "2016-08-03T00:00:00.000Z");
        eventJson.addProperty("description", "Cru Central Coast's Fall Retreat");
        eventJson.addProperty("url", "https://www.facebook.com/events/1");
        eventJson.addProperty("rideSharingEnabled", "false");

        //Add location
        JsonObject location = new JsonObject();
        location.addProperty("postcode", "90000");
        location.addProperty("state", "CA");
        location.addProperty("suburb", "Ventura");
        location.addProperty("street1", "Harbor Blvd");
        location.addProperty("country", "United States");

        eventJson.add("location", location);
        event1 = new Event(eventJson, true);
    }

    //placeholder test so we can test the whole package
    public void testConstructEvents() {
        ArrayList<Event> events = TestDB.getEvents();

        System.out.println("Found " + events.size() + " events");

        assertTrue(!events.isEmpty());
    }

    public void testEvent() {
        assertEquals("1", event1.getId());
        assertEquals("Fall Retreat", event1.getName());
        assertEquals("Cru Central Coast's Fall Retreat", event1.getDescription());
        assertEquals("Jun 08, 12:00AM", event1.getEventDate());
        assertEquals("Jun 08, 12:00AM - Aug 03, 12:00AM", event1.getEventFullDate());
        assertTrue(event1.hasLocation());
        assertEquals("Harbor Blvd, Ventura CA", event1.getEventLocation());
        assertTrue(event1.hasFacebook());
        assertEquals("https://www.facebook.com/events/1", event1.getFacebookLink());
        assertFalse(event1.hasRideSharing());
    }
}
