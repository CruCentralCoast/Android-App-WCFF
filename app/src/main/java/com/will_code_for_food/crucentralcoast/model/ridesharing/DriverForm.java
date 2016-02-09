package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.users.User;
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
                Resources.getSystem().getString(R.string.ridesharing_seats_question_name),
                Resources.getSystem().getString(R.string.ridesharing_seats),
                options);
        addQuestion(numSeats);
    }

    /**
     * Creates a ride from the contents of the form.
     * Returns the new ride, or null if the form was
     * filled out incompletely or with errors.
     */
    public Ride submit() {
        if (isComplete() && isValid()) {
            // TODO create user using user's actual info (from phone)
            User driver = new User("cru_user", "123-456-7890");

            RideDirection dir;
            if (Resources.getSystem().getString(R.string.ridesharing_one_way_to_event)
                    .equals(direction.getAnswer())) {
                dir = RideDirection.ONE_WAY_TO_EVENT;
            } else if (Resources.getSystem().getString(R.string.ridesharing_one_way_from_event)
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
                    (String)nameQuestion.getAnswer());
            Log.e("GAVIN", "Saving " + nameQuestion.getAnswer() + " as username!!!!!");

            // save to database
            Ride origRide = new Ride(eventId, driver, (int) numSeats.getAnswer(),
                    (String) locations.getAnswer(), dir);
            Ride ride = new Ride(RestUtil.create(origRide.toJSON(), Database.REST_RIDE));
            // save to user's rides
            LocalStorageIO.appendToList(ride.getId(), LocalFiles.USER_RIDES);
            return ride;
        }
        return null;
    }
}
