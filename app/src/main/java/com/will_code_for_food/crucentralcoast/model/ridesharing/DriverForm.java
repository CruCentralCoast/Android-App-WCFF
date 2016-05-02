package com.will_code_for_food.crucentralcoast.model.ridesharing;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.tasks.ParameterizedTask;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Gender;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;
import com.will_code_for_food.crucentralcoast.model.common.messaging.PushUtil;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class DriverForm extends RiderForm {
    private final Question numSeats;
    private final ParameterizedTask submitTask;

    /**
     * Creates a form for drivers to fill out to offer a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     *      TODO maybe replace this dropdown with a location selector (Google maps)
     */
    public DriverForm(final String eventId, final ParameterizedTask submitTask) {
        super(eventId);
        this.submitTask = submitTask;
        numSeats = new Question(
                Util.getString(R.string.ridesharing_seats_question_name),
                Util.getString(R.string.ridesharing_seats),
                QuestionType.NUMBER_SELECT);
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
            String driverName =  (String) nameQuestion.getAnswer();
            String driverNumber = (String) numberQuestion.getAnswer();

            RideDirection dir;

            if (direction.getAnswer() == RideDirection.ONE_WAY_TO_EVENT) {
                dir = RideDirection.ONE_WAY_TO_EVENT;
            } else if (direction.getAnswer() == RideDirection.ONE_WAY_FROM_EVENT) {
                dir = RideDirection.ONE_WAY_FROM_EVENT;
            } else {
                dir = RideDirection.TWO_WAY;
            }

            dir.setLeaveTimeFromEvent((GregorianCalendar) leaveTimeFromEvent.getAnswer());
            dir.setLeaveTimeToEvent((GregorianCalendar) leaveTimeToEvent.getAnswer());
            // save user info
            saveUserInfo();

            // save to database
            Logger.i("Ride Creation", "Buildign ride from user input");
            Ride origRide = new Ride(eventId, driverName, driverNumber, PushUtil.getGCMId(),
                    (Location) location.getAnswer(), 1.0,
                    (int) numSeats.getAnswer(), dir, ((Gender)genderQuestion.getAnswer()).getValue() + "");
            Ride ride = null;
            try {
                Logger.i("Ride Creation", "Sending ride to the database");
                submitTask.putExtra(origRide);
                ride = (Ride) submitTask.execute().get(2000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // save to user's rides
            if (ride != null) {
                Logger.i("Ride Creation", "Ride returned from database correctly");
                LocalStorageIO.appendToList(ride.getId(), LocalFiles.USER_RIDES);
                return true;
            }
        }
        return false;
    }
}
