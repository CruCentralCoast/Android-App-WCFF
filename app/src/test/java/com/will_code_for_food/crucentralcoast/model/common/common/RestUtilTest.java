package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.will_code_for_food.crucentralcoast.values.Database;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class RestUtilTest extends TestCase {

    /**
        Checks to see if the Rest util is getting anything. Will fail if the database is not running.
     */
    public void testGet() {
        JsonArray ministriesJson = RestUtil.get(Database.REST_MINISTRY);

        assertNotNull(ministriesJson);
        assertEquals(true, ministriesJson.size() > 0);
    }
}
