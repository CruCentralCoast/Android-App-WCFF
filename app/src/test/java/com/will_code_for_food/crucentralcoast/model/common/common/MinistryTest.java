package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class MinistryTest extends TestCase{
    Ministry ministry;
    String ministryString =
            "{\"_id\":\"12345\"," +
            "\"name\":\"test\"," +
            "\"image\":{" +
                "\"public_id\":\"1\"," +
                "\"version\":0," +
                "\"signature\":\"adf4a6\"," +
                "\"width\":2048," +
                "\"height\":770," +
                "\"format\":\"jpg\"," +
                "\"resource_type\":\"image\"," +
                "\"url\":\"http://blahblahblah\"," +
                "\"secure_url\":\"https://blahblahblah\"}," +
            "\"description\":\"this is a test\"," +
            "\"campuses\":[\"camp1\", \"camp2\"]}";

    public void testConstructMinistry() {
        JsonParser parser = new JsonParser();
        JsonObject ministyJson = parser.parse(ministryString).getAsJsonObject();
        ministry = new Ministry(ministyJson);

        assertEquals("camp1", ministry.getCampuses().get(0));
        assertEquals("camp2", ministry.getCampuses().get(1));
    }
}
