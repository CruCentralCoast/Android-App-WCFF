package com.will_code_for_food.crucentralcoast.model.getInvolved;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.model.common.messaging.Notifier;
import com.will_code_for_food.crucentralcoast.values.Database;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests the ministry team object
 */
public class MinistryTeamTest extends WCFFUnitTest {
    @Test
    public void testSignUp() {
        JsonObject json = new JsonObject();
        json.addProperty(Database.JSON_KEY_COMMON_NAME, "MI6");
        MinistryTeam min = new MinistryTeam(null);
        min.signup("James Bond", "007", "bondjamesbond@mi6.uk", new Notifier(null, null) {
            @Override
            public boolean notify(String message) {
                Assert.assertEquals("James Bond has signed up for MI6. Their email " +
                        "is bondjamesbond@mi6.uk,\n" + "        and their number is 007.", message);
                return true;
            }
        });
    }
}
