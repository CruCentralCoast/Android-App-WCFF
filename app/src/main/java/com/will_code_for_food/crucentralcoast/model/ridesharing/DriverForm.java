package com.will_code_for_food.crucentralcoast.model.ridesharing;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class DriverForm extends RiderForm {
    public static final int maxNumSeats = 10; // upper limit of dropdown options
    private final Question numSeats;

    /**
     * Creates a form for drivers to fill out to offer a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     *      TODO maybe replace this dropdown with a location selector (Google maps)
     */
    public DriverForm(final String eventId) {
        super(eventId);
        ArrayList<Object> options = new ArrayList<Object>();
        for (int num = 1; num <= maxNumSeats; num++) {
            options.add(Integer.toString(num));
        }
        numSeats = new MultiOptionQuestion(
                Util.getString(R.string.ridesharing_seats_question_name),
                Util.getString(R.string.ridesharing_seats),
                options);
        addQuestion(numSeats);
    }

    /**
     * Creates a ride from the contents of the form.
     * Returns the new ride, or null if the form was
     * filled out incompletely or with errors.
     */
    public boolean submit() {
        if (isComplete() && isValid()) {
            // TODO create user using user's actual info (from phone)
            String driverName = "cru_driver";
            String driverNumber = "1234567890";

            RideDirection dir;
            if (Util.getString(R.string.ridesharing_one_way_to_event)
                    .equals(direction.getAnswer())) {
                dir = RideDirection.ONE_WAY_TO_EVENT;
            } else if (Util.getString(R.string.ridesharing_one_way_from_event)
                    .equals(direction.getAnswer())) {
                dir = RideDirection.ONE_WAY_FROM_EVENT;
            } else {
                dir = RideDirection.TWO_WAY;
            }

            dir.setLeaveTimeFromEvent(((GregorianCalendar) leaveTimeFromEvent.getAnswer()).getTimeInMillis());
            dir.setLeaveTimeToEvent(((GregorianCalendar)
                    leaveTimeToEvent.getAnswer()).getTimeInMillis());
            // save username
            LocalStorageIO.writeSingleLineFile(LocalFiles.USER_NAME,
                    (String) nameQuestion.getAnswer());

            //TODO Fill in with real data
            // save to database
            Ride origRide = new Ride(eventId, driverName, driverNumber, "dummy_gcm_id", new Location("12345", "CA", "", "123 Main Street", "USA"), "2016-01-01T00:00:00Z", 1.0, (int) numSeats.getAnswer(), dir, "male");
            Ride ride = new Ride(RestUtil.create(origRide.toJSON(), Database.REST_RIDE));

            // save to user's rides
            LocalStorageIO.appendToList(ride.getId(), LocalFiles.USER_RIDES);
            return true;
        }
        return false;
    }
}
