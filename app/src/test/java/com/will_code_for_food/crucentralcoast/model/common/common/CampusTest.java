package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class CampusTest extends TestCase {

    Campus campus;
    String campusString =
            "{\"_id\":\"563b04532930ae0300fbc097\"," +
                    "\"slug\":\"cuesta\"," +
                    "\"name\":\"Cuesta\"," +
                    "\"__v\":0," +
                    "\"url\":\"cuesta.edu\"," +
                    "\"location\":{\"postcode\":\"93403\",\"state\":\"California\",\"suburb\":\"San Luis Obispo\",\"street1\":\"CA-1\",\"country\":\"United States\"}}";

    public void testConstructCampus() {
        JsonParser parser = new JsonParser();
        JsonObject campusJson = parser.parse(campusString).getAsJsonObject();
        campus = new Campus(campusJson);
        Location loc = new Location("93403", "California", "San Luis Obispo", "CA-1", "United States");

        assertEquals("563b04532930ae0300fbc097", campus.getId());
        assertEquals("Cuesta", campus.getName());
        assertEquals("cuesta.edu", campus.getWebsiteUrl());
        assertEquals(loc, campus.getLocation());

    }
}
