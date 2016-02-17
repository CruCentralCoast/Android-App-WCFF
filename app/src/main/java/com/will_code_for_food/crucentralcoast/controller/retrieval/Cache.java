package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;

/**
 * Cache locations for various types of database objects
 */
public enum Cache {
    EVENTS("cache-events", Event.class),
    RIDES("cache-rides", Ride.class),
    MISSIONS("cache-missions", SummerMission.class);

    public final String fname;
    public final Class objectType;

    Cache(final String fname, final Class type) {
        this.fname = fname;
        this.objectType = type;
    }

    public static Cache getCacheForObjectType(Class objType) {
        for (Cache cache : values()) {
            if (cache.objectType == objType) {
                return cache;
            }
        }
        return null;
    }
}
