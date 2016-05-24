package com.will_code_for_food.crucentralcoast.model.formTests;

import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.ParameterizedTask;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Gender;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;

import junit.framework.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;

/**
 * Tests the driver form
 */
public class DriverFormTest extends WCFFUnitTest {
    @Test
    public void testSubmit() {
        DriverForm form = new DriverForm("fake_id", new FakeDBTask());
        form.answerQuestion(0, "fake_name");
        form.answerQuestion(1, "123-456-7890");
        form.answerQuestion(2, Gender.FEMALE);
        form.answerQuestion(3, GregorianCalendar.getInstance());
        form.answerQuestion(4, RideDirection.ONE_WAY_TO_EVENT);
        form.answerQuestion(5, new Location("postcode", "state", "suburb", "street", "country"));

        // test submit with missing answer (number of seats)
        Assert.assertFalse(form.submit());

        // test submit with all answers filled out
        form.answerQuestion(6, 5);
        Assert.assertTrue(form.submit());

        // test submit with all answers filled out
        form.answerQuestion(6, 5);
        Assert.assertTrue(form.submit());
    }

    @Test
    public void testSubmitWithBadDatabaseConnection() {
        DriverForm form = new DriverForm("fake_id", new FakeBadConnectionDBTask());
        form.answerQuestion(0, "fake_name");
        form.answerQuestion(1, "123-456-7890");
        form.answerQuestion(2, Gender.FEMALE);
        form.answerQuestion(3, GregorianCalendar.getInstance());
        form.answerQuestion(4, RideDirection.ONE_WAY_TO_EVENT);
        form.answerQuestion(5, new Location("postcode", "state", "suburb", "street", "country"));
        form.answerQuestion(6, 5);

        Assert.assertFalse(form.submit());
    }

    private class FakeDBTask extends ParameterizedTask {
        @Override
        protected Object doInBackground(Void... params) {
            return new Ride("eventId", "driverName", "driverNumber", "gcmId",
                    null, 1.0, 1, RideDirection.ONE_WAY_TO_EVENT, "Male");
        }
    }

    private class FakeBadConnectionDBTask extends ParameterizedTask {
        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }
    }
}
