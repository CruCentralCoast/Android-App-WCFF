package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class DriverForm extends RiderForm {
    private final int maxNumSeats = 10;

    /**
     * Creates a form for drivers to fill out to offer a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     *      TODO maybe replace this dropdown with a location selector (Google maps)
     */
    public DriverForm(final List<Object> possibleLeaveLocations) {
        super(possibleLeaveLocations);
        ArrayList<Object> options = new ArrayList<Object>();
        for (int num = 1; num <= maxNumSeats; num++) {
            options.add(Integer.toString(num));
        }
        addQuestion(new MultiOptionQuestion(Resources.getSystem().getString(
                R.string.ridesharing_seats), options));
    }
}
