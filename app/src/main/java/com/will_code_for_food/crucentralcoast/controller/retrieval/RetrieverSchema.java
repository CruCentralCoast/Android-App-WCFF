package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.model.common.common.*;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * This schema is used by retrievers to get DatabaseObject object from the database.
 * When creating a new database object, a new enum value must be put here with the class
 * and the database table name.
 *
 * Created by Brian on 1/14/2016.
 */
public enum RetrieverSchema {
    CAMPUS (Campus.class, Database.REST_CAMPUS),
    MINISTRY (Ministry.class, Database.REST_MINISTRY),
    EVENT (Event.class, Database.REST_EVENT),
    SUMMER_MISSION (SummerMission.class, Database.REST_SUMMER_MISSION),
    RIDE (Ride.class, Database.REST_RIDE),
    PASSENGER (Passenger.class, Database.REST_PASSENGER),
    RESOURCE(Resource.class, Database.REST_RESOURCE),
    MINISTRY_TEAM (MinistryTeam.class, Database.MINISTRY_TEAM),
    PASSENGER (Passenger.class, Database.REST_PASSENGER);

    public Class<? extends DatabaseObject> getObjClass() {
        return objClass;
    }

    public String getTableName() {
        return tableName;
    }

    private final Class<? extends DatabaseObject> objClass;
    private final String tableName;

    RetrieverSchema(Class<? extends DatabaseObject> objClass, String tableName) {
        this.objClass = objClass;
        this.tableName = tableName;
    }
}
