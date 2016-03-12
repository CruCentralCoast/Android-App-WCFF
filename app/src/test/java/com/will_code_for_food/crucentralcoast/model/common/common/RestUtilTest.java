package com.will_code_for_food.crucentralcoast.model.common.common;

import android.support.v4.util.Pair;

import com.google.gson.JsonArray;
import com.will_code_for_food.crucentralcoast.values.Database;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class RestUtilTest extends TestCase {
    private static final String TEST_MINISTRY_NAME = "Slo Cru";

    /**
        Checks to see if the Rest util is getting anything. Will fail if the database is not running.
     */
    public void testGet() {
        JsonArray ministriesJson = RestUtil.get(Database.REST_MINISTRY);

        assertNotNull(ministriesJson);
        assertEquals(true, ministriesJson.size() > 0);
    }

    public void testFind() {
        Pair<String, String> field = new Pair<String, String>("name", TEST_MINISTRY_NAME);
        JsonArray ministriesJson = RestUtil.getWithConditions(Database.REST_MINISTRY, field);
        assertNotNull(ministriesJson);
        assertTrue(ministriesJson.size() > 0);
        assertTrue(ministriesJson.get(0).isJsonObject());
        Ministry ministry = new Ministry(ministriesJson.get(0).getAsJsonObject());
        assertEquals(ministry.getName(), TEST_MINISTRY_NAME);
    }
}
