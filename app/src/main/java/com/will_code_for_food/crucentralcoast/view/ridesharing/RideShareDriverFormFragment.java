package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;


/**
 * Created by masonstevenson on 2/4/16.
 */
public class RideShareDriverFormFragment extends RideShareFormFragment {
    private static final int MAX_SEATS = 12;
    private DriverForm form;

    NumberPicker numberofSeats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        initComponents(fragmentView);
        return fragmentView;
    }

    public void initComponents(View fragmentView) {
        numberofSeats = (NumberPicker) fragmentView.findViewById(R.id.number_of_seats);

        numberofSeats.setMinValue(1);
        numberofSeats.setMaxValue(MAX_SEATS);
        numberofSeats.setWrapSelectorWheel(false);
    }

    @Override
    public Form getForm(String eventId) {
        form = new DriverForm(eventId);
        return form;
    }

    @Override
    public void answerAdditionalQuestions() {
        if (numberofSeats.getValue() > 0) {
            form.answerQuestion(6, numberofSeats.getValue());
        }
    }

    @Override
    public void submitFormAdditionalActions() {
        new PushNewRideTask().execute();

        //TODO: go to a new screen here
    }

    private class PushNewRideTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... params) {
            //RestUtil.create(Ride.toJSON(selectedEvent.getId(), new User("testDriver", "1234567"), 5, "test", RideDirection.ONE_WAY_TO_EVENT), Database.REST_RIDE);
            return null;
        }
    }
}
