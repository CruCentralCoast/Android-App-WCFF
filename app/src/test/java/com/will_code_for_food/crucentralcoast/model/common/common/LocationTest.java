package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonParser;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class LocationTest extends TestCase {

    Location loc;
    String locString = "{\"postcode\":\"93403\",\"state\":\"California\",\"suburb\":\"San Luis Obispo\",\"street1\":\"CA-1\",\"country\":\"United States\"}";

    public void testConstructLocation() {
        JsonParser parser = new JsonParser();
        loc = new Location(parser.parse(locString));

        assertEquals("93403", loc.getPostcode());
        assertEquals("California", loc.getState());
        assertEquals("San Luis Obispo", loc.getSuburb());
        assertEquals("CA-1", loc.getStreet());
        assertEquals("United States", loc.getCountry());

        assertNull(loc.getFieldAsString("NOTAFIELD"));
        assertTrue(loc.equals(new Location(loc.toJSON())));
    }
}
