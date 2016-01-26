package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.users.User;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class DriverForm extends RiderForm {
    private final int maxNumSeats = 10; // upper limit of dropdown options
    private final Question numSeats;

    /**
     * Creates a form for drivers to fill out to offer a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     *      TODO maybe replace this dropdown with a location selector (Google maps)
     */
    public DriverForm(final Event event, final List<String> possibleLeaveLocations) {
        super(event, possibleLeaveLocations);
        ArrayList<Object> options = new ArrayList<Object>();
        for (int num = 1; num <= maxNumSeats; num++) {
            options.add(Integer.toString(num));
        }
        numSeats = new MultiOptionQuestion(Resources.getSystem().getString(
                R.string.ridesharing_seats), options);
        addQuestion(numSeats);
    }

    /**
     * Creates a ride from the contents of the form.
     * Returns the new ride, or null if the form was
     * filled out incompletely or with errors.
     */
    public Ride createRide() {
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

            dir.setLeaveTimeFromEvent(
                    ((GregorianCalendar)leaveTimeFromEvent.getAnswer()).getTimeInMillis());
            dir.setLeaveTimeToEvent(
                    ((GregorianCalendar)leaveTimeToEvent.getAnswer()).getTimeInMillis());

            return new Ride(event, driver, (int)numSeats.getAnswer(),
                    (String)locations.getAnswer(), dir);
        }
        return null;
    }
}
