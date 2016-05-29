package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.values.Database;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class CampusTest extends TestCase {

    public void testConstructCampus() {
        TestCampus campus = new TestCampus();
        Location loc = new Location("93403", "California", "San Luis Obispo", "CA-1", "United States");
        assertEquals("www.website.org", campus.getWebsiteUrl());
        assertEquals(loc, campus.getLocation());
    }

    private class TestCampus extends Campus {
        public TestCampus(){
            super(null);
        }

        @Override
        public String grabWebsiteUrl(){
            return "www.website.org";
        }

        @Override
        public Location grabLocation(){
            return new Location("93403", "California", "San Luis Obispo", "CA-1", "United States");
        }
    }
}
