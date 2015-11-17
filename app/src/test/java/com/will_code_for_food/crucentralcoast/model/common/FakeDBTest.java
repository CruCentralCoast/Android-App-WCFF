package com.will_code_for_food.crucentralcoast.model.common;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.components.FakeDB;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 11/16/2015.
 */
public class FakeDBTest extends TestCase{
    FakeDB fakeDB;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        fakeDB = new FakeDB();
    }

    public void testMinistryTeamJson() {
        JsonObject jObj = fakeDB.ministryTeamJson();
        //System.out.println("List Elements are  : " + jObj.toString());

        Assert.assertTrue(jObj.has("_id"));
        Assert.assertTrue(jObj.has("slug"));
        Assert.assertTrue(jObj.has("parentMinistry"));
        Assert.assertTrue(jObj.has("description"));
        Assert.assertTrue(jObj.has("name"));
        Assert.assertTrue(jObj.has("__v"));
    }

    public void testGetMinistryTeam() {
        MinistryTeam team = fakeDB.getMinistryTeam();

        Assert.assertEquals("563b0a832930ae0300fbc0a8", team.getId());
        Assert.assertEquals(null, team.getParentMinistry());
        Assert.assertEquals(
                "A passionate community of women encouraging each other to be fully" +
                " surrendered to our King through prayer, worship, and gatherings.",
                team.getDescription());
        Assert.assertEquals("Women's Team", team.getName());
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
