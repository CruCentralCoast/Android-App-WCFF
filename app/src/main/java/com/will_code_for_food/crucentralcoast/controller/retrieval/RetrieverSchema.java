package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.*;

/**
 * This schema is used by retrievers to get DatabaseObject object from the database.
 * When creating a new database object, a new enum value must be put here with the class
 * and the database table name.
 *
 * Created by Brian on 1/14/2016.
 */
public enum RetrieverSchema {
    CAMPUS (Campus.class, Util.getString(R.string.rest_campus)),
    MINISTRY (Ministry.class, Util.getString(R.string.rest_ministry)),
    EVENT (Event.class, Util.getString(R.string.rest_event));


    public Class<? extends DatabaseObject> getObjClass() {
        return objClass;
    }

    public String getTableName() {
        return tableName;
    }

    private final Class<? extends DatabaseObject> objClass;
    private final String tableName;

    private RetrieverSchema(Class<? extends DatabaseObject> objClass, String tableName) {
        this.objClass = objClass;
        this.tableName = tableName;
    }
}
