package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class DatabaseObjectTest extends TestCase {

    DatabaseObject object;
    String objectString =
            "{\"_id\":\"12345\"," +
            "\"name\":\"test\"," +
            "\"image\":{" +
                    "\"public_id\":\"1\"," +
                    "\"version\":0," +
                    "\"signature\":\"adf4a6\"," +
                    "\"width\":2048," +
                    "\"height\":770," +
                    "\"format\":\"jpg\"," +
                    "\"resource_type\":\"image\"," +
                    "\"url\":\"http://blahblahblah\"," +
                    "\"secure_url\":\"https://blahblahblah\"}," +
            "\"description\":\"this is a test\"}";

    public void testConstructDatabaseObject() {
        JsonParser parser = new JsonParser();
        JsonObject objectJson = parser.parse(objectString).getAsJsonObject();
        object = new DbObj(objectJson);

        assertEquals("12345", object.getId());
        assertEquals("test", object.getName());
        assertEquals("http://blahblahblah", object.getImage());
        assertEquals("this is a test", object.getDescription());
    }

    private class DbObj extends DatabaseObject {
        public DbObj(JsonObject obj) {
            super(obj);
        }
    }
}
