package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonParser;

import junit.framework.TestCase;

/**
 * Created by MasonJStevenson on 1/18/2016.
 */
public class ImageDataTest extends TestCase {
    ImageData imageData;
    String imageString = "{" +
            "\"public_id\":\"1\"," +
            "\"version\":0," +
            "\"signature\":\"adf4a6\"," +
            "\"width\":2048," +
            "\"height\":770," +
            "\"format\":\"jpg\"," +
            "\"resource_type\":\"image\"," +
            "\"url\":\"http://blahblahblah\"," +
            "\"secure_url\":\"https://blahblahblah\" }";

    public void testConstructImageData() {
        JsonParser parser = new JsonParser();
        imageData = new ImageData(parser.parse(imageString));

        assertEquals("1", imageData.getId());
        assertEquals("0", imageData.getVersion());
        assertEquals("adf4a6", imageData.getSignature());
        assertEquals(2048, imageData.getWidth());
        assertEquals(770, imageData.getHeight());
        assertEquals("jpg", imageData.getFormat());
        assertEquals("image", imageData.getResourceType());
        assertEquals("http://blahblahblah", imageData.getUrl());
        assertEquals("https://blahblahblah", imageData.getSecureUrl());
    }
}
