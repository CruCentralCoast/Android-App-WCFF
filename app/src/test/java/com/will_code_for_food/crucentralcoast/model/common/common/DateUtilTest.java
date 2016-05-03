package com.will_code_for_food.crucentralcoast.model.common.common;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MasonJStevenson on 4/24/2016.
 */
public class DateUtilTest extends TestCase {

    ArrayList<Event> events;
    ArrayList<Video> videos;
    ArrayList<Resource> resources;
    ArrayList<SummerMission> summerMissions;
    ArrayList<Ministry> ministries;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Logger.testMode = true;

        events = TestDB.getEvents();
        videos = TestDB.getVideos();
        resources = TestDB.getResources();
        summerMissions = TestDB.getSummerMissions();
        ministries = TestDB.getMinistries();
    }

    public void testGetDate() {

        Date result;

        result = DateUtil.getDate(events.get(0));
        assertNotNull(result);
        assertEquals("Thu Oct 15 19:00:00 PDT 2015", result.toString());

        result = DateUtil.getDate(videos.get(0));
        assertNotNull(result);
        assertEquals("Wed Mar 09 06:12:14 PST 2016", result.toString());

        result = DateUtil.getDate(resources.get(0));
        assertNotNull(result);
        assertEquals("Sun Jan 24 00:00:00 PST 2016", result.toString());

        result = DateUtil.getDate(summerMissions.get(0));
        assertNotNull(result);
        assertEquals("Wed Jun 08 00:00:00 PDT 2016", result.toString());

        result = DateUtil.getDate(ministries.get(0));
        assertNull(result);
    }

    public void testParseISO() {

        Date result = DateUtil.parseISO("2015-10-15T19:00:00.000Z");
        assertNotNull(result);
        assertEquals("Thu Oct 15 19:00:00 PDT 2015", result.toString());
        assertNull(DateUtil.parseISO(""));
        assertNull(DateUtil.parseISO("HI :D"));
        assertNull(DateUtil.parseISO(null));
    }
}
