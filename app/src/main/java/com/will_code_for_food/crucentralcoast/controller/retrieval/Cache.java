package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;

/**
 * Cache locations for various types of database objects
 */
public enum Cache {
    EVENTS("cache-events", Event.class),
    RIDES("cache-rides", Ride.class);

    public final String fname;
    public final Class objectType;

    private Cache(final String fname, final Class type) {
        this.fname = fname;
        this.objectType = type;
    }
}
