package com.will_code_for_food.crucentralcoast.model.common.common;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class EventTest extends TestCase {

    //placeholder test so we can test the whole package
    public void testConstructEvents() {
        ArrayList<Event> events = TestDB.getEvents();

        System.out.println("Found " + events.size() + " events");

        assertTrue(!events.isEmpty());
    }
}
