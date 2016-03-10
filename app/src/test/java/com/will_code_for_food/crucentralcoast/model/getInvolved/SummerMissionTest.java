package com.will_code_for_food.crucentralcoast.model.getInvolved;

import com.google.gson.JsonObject;

import junit.framework.TestCase;

/**
 * Created by Brian on 3/9/2016.
 */
public class SummerMissionTest extends TestCase {
    SummerMission mission1;

    public void setUp() throws Exception {
        super.setUp();
        JsonObject missionJson = new JsonObject();
        missionJson.addProperty("_id", "1");
        missionJson.addProperty("slug", "santa-monica");
        missionJson.addProperty("name", "Santa Monica");
        missionJson.addProperty("description", "The Santa Monica Summer Mission is a great trip for " +
                "quarter and semester students! Have to take SUMMER SCHOOL?");
        missionJson.addProperty("url", "http://santamonicasummermission.com/");
        missionJson.addProperty("leaders", "Paul Nunez");
        missionJson.addProperty("endDate", "2016-08-03T00:00:00.000Z");
        missionJson.addProperty("startDate", "2016-06-08T00:00:00.000Z");
        missionJson.addProperty("cost", "3300");
        mission1 = new SummerMission(missionJson);
    }

    public void testSummerMission(){
        assertEquals("1", mission1.getId());
        assertEquals("Santa Monica", mission1.getName());
        assertEquals("June 08, 2016", mission1.getMissionDateString());
        assertEquals("June 08, 2016 - August 03, 2016", mission1.getMissionFullDate());
        assertEquals("http://santamonicasummermission.com/", mission1.getWebsiteUrl());
        assertEquals("The Santa Monica Summer Mission is a great trip for quarter and semester " +
                "students! Have to take SUMMER SCHOOL?", mission1.getDescription());
    }
}