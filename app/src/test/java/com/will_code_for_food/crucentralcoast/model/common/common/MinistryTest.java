package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.Test;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class MinistryTest extends TestCase{

    public void testConstructMinistryWELC(){
        TestMinistry ministry = new TestMinistry();
        ArrayList<String> campuses = new ArrayList<>();
        campuses.add("campus1");
        campuses.add("campus2");
        assertEquals(campuses, ministry.getCampuses());

    }

    private class TestMinistry extends Ministry{

        public TestMinistry() {
            super(null);
        }

        @Override
        public ArrayList<String> grabCampuses(){
            ArrayList<String> campuses = new ArrayList<>();
            campuses.add("campus1");
            campuses.add("campus2");
            return campuses;
        }
    }
}
