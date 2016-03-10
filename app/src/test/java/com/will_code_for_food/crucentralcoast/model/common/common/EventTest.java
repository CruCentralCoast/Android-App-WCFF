package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.values.Database;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class EventTest extends TestCase {

    Event event1;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        JsonParser parser = new JsonParser();
        event1 = new Event(parser.parse("{\"_id\":\"1\",\"slug\":\"\",\"url\":\"\",\"description\":\"desc\",\"name\":\"event1\",\"__v\":1,\"notificationDate\":\"2015-10-13T05:00:00.000Z\",\"parentMinistry\":\"\",\"notifications\":[],\"parentMinistries\":[],\"rideSharingEnabled\":true,\"endDate\":\"2015-10-17T12:00:00.000Z\",\"startDate\":\"2015-10-15T19:00:00.000Z\",\"location\":{\"postcode\":\"93001\",\"state\":\"CA\",\"suburb\":\"Ventura\",\"street1\":\"2055 E Harbor Blvd\",\"country\":\"United States\"},\"image\":{\"public_id\":\"devd2faxvvnumpsdkclp\",\"version\":1446711704,\"signature\":\"6537942b4e856bf6acd6ef656f12c6c56d0a2d4f\",\"width\":2048,\"height\":770,\"format\":\"jpg\",\"resource_type\":\"image\",\"url\":\"http://res.cloudinary.com/dcyhqxvmq/image/upload/v1446711704/devd2faxvvnumpsdkclp.jpg\",\"secure_url\":\"https://res.cloudinary.com/dcyhqxvmq/image/upload/v1446711704/devd2faxvvnumpsdkclp.jpg\"}}").getAsJsonObject(), true);
    }

    public void testConstructEvents() {
        ArrayList<Event> events = TestDB.getEvents();

        System.out.println("Found " + events.size() + " events");

        assertTrue(!events.isEmpty());
    }

    public void testFields() {
        assertEquals("desc", event1.getDescription());
        assertTrue(event1.hasRideSharing());
        assertEquals("", event1.getFacebookLink());
        assertFalse(event1.hasFacebook());
        event1.setField(Database.JSON_KEY_COMMON_URL, new JsonPrimitive("facebook.com"));
        assertTrue(event1.hasFacebook());

        //TODO: test dates
    }
}
