package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.TestDB;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 3/10/2016.
 */
public class DateComparatorTest extends TestCase {

    public void testCompare() {
        JsonParser parser = new JsonParser();

        Event event1 = new Event(parser.parse("{\"_id\":\"1\",\"slug\":\"\",\"url\":\"\",\"description\":\"\",\"name\":\"event1\",\"__v\":1,\"notificationDate\":\"2015-10-13T05:00:00.000Z\",\"parentMinistry\":\"\",\"notifications\":[],\"parentMinistries\":[],\"rideSharingEnabled\":true,\"endDate\":\"2015-10-17T12:00:00.000Z\",\"startDate\":\"2015-10-15T19:00:00.000Z\",\"location\":{\"postcode\":\"93001\",\"state\":\"CA\",\"suburb\":\"Ventura\",\"street1\":\"2055 E Harbor Blvd\",\"country\":\"United States\"},\"image\":{\"public_id\":\"devd2faxvvnumpsdkclp\",\"version\":1446711704,\"signature\":\"6537942b4e856bf6acd6ef656f12c6c56d0a2d4f\",\"width\":2048,\"height\":770,\"format\":\"jpg\",\"resource_type\":\"image\",\"url\":\"http://res.cloudinary.com/dcyhqxvmq/image/upload/v1446711704/devd2faxvvnumpsdkclp.jpg\",\"secure_url\":\"https://res.cloudinary.com/dcyhqxvmq/image/upload/v1446711704/devd2faxvvnumpsdkclp.jpg\"}}").getAsJsonObject(), true);
        Resource resource1 = new Resource(parser.parse("{\"_id\":\"11\",\"slug\":\"\",\"title\":\"\",\"type\":\"article\",\"url\":\"\",\"__v\":1,\"date\":\"2016-01-25T00:00:00.000Z\",\"tags\":[]}").getAsJsonObject());
        Video video1 = new Video(parser.parse("{\"kind\":\"youtube#playlistItem\",\"etag\":\"\\\"q5k97EMVGxODeKcDgp8gnMu79wM/8kHubTl4iHyAwPRgYPbVWHNH69E\\\"\",\"id\":\"UUjfr5i5EwkZV7SOLUgegvXJ6vEY1Owuzc\",\"snippet\":{\"publishedAt\":\"2016-03-09T06:12:14.000Z\",\"channelId\":\"UCe-RJ-3Q3tUqJciItiZmjdg\",\"title\":\"Weekly Meeting 03/08/16\",\"description\":\"Streamed live on March 8, 2016\\nSpeaker: Panel\\nTopic: iCare Panel\\nPassage: None\",\"thumbnails\":{\"default\":{\"url\":\"https://i.ytimg.com/vi/FLMZRBlxJxM/default.jpg\",\"width\":120,\"height\":90},\"medium\":{\"url\":\"https://i.ytimg.com/vi/FLMZRBlxJxM/mqdefault.jpg\",\"width\":320,\"height\":180},\"high\":{\"url\":\"https://i.ytimg.com/vi/FLMZRBlxJxM/hqdefault.jpg\",\"width\":480,\"height\":360},\"standard\":{\"url\":\"https://i.ytimg.com/vi/FLMZRBlxJxM/sddefault.jpg\",\"width\":640,\"height\":480},\"maxres\":{\"url\":\"https://i.ytimg.com/vi/FLMZRBlxJxM/maxresdefault.jpg\",\"width\":1280,\"height\":720}},\"channelTitle\":\"SLO Cru\",\"playlistId\":\"UUe-RJ-3Q3tUqJciItiZmjdg\",\"position\":0,\"resourceId\":{\"kind\":\"youtube#video\",\"videoId\":\"FLMZRBlxJxM\"}}}").getAsJsonObject());

        Resource nullDate = new Resource(parser.parse("{\"_id\":\"11\",\"slug\":\"\",\"title\":\"\",\"type\":\"article\",\"url\":\"\",\"__v\":1,\"tags\":[]}").getAsJsonObject());

        DateComparator comparator = new DateComparator(SortMethod.DESCENDING);

        assertTrue(comparator.compare(event1, event1) == 0);
        assertTrue(comparator.compare(resource1, event1) < 0);
        assertTrue(comparator.compare(event1, resource1) > 0);
        assertTrue(comparator.compare(video1, resource1) < 0);
        assertTrue(comparator.compare(resource1, video1) > 0);

        assertTrue(comparator.compare(nullDate, nullDate) == 0);
        assertTrue(comparator.compare(nullDate, event1) > 0);
        assertTrue(comparator.compare(event1, nullDate) < 0);

        comparator = new DateComparator(SortMethod.ASCENDING);

        assertTrue(comparator.compare(event1, event1) == 0);
        assertTrue(comparator.compare(resource1, event1) > 0);
        assertTrue(comparator.compare(event1, resource1) < 0);
        assertTrue(comparator.compare(video1, resource1) > 0);
        assertTrue(comparator.compare(resource1, video1) < 0);

        assertTrue(comparator.compare(nullDate, nullDate) == 0);
        assertTrue(comparator.compare(nullDate, event1) < 0);
        assertTrue(comparator.compare(event1, nullDate) > 0);

    }
}
