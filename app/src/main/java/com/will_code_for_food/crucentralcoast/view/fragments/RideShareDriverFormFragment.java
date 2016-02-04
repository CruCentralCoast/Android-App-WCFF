package com.will_code_for_food.crucentralcoast.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.RideShareActivity;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.users.User;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;

/**
 * Created by masonstevenson on 2/4/16.
 */
public class RideShareDriverFormFragment extends CruFragment{

    DatePicker datePicker;
    TimePicker timePicker;
    Event selectedEvent;
    RideShareActivity parent;
    Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        parent = (RideShareActivity) getParent();
        selectedEvent = parent.getEvent();
        datePicker = (DatePicker) fragmentView.findViewById(R.id.departure_date_picker);
        timePicker = (TimePicker) fragmentView.findViewById(R.id.departure_time_picker);
        submitButton = (Button) fragmentView.findViewById(R.id.driver_form_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DisplayEventInfoTask().execute();
                Toast.makeText(parent, "ride added", Toast.LENGTH_SHORT);
                //System.out.println("Submit clicked!");
            }
        });

        return fragmentView;
    }

    private class DisplayEventInfoTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... params) {
            RestUtil.create(Ride.toJSON(selectedEvent.getId(), new User("testDriver", "1234567"), 5, "test", RideDirection.ONE_WAY_TO_EVENT), Database.REST_RIDE);
            return null;
        }
    }


}
