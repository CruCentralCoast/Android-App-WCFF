package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by masonstevenson on 2/4/16.
 */
public class RideShareDriverFormFragment extends CruFragment {

    DatePicker datePicker;
    TimePicker timePicker;
    EditText name;
    EditText locations;
    CheckBox oneWayTo;
    CheckBox oneWayFrom;
    CheckBox twoWay;
    EditText numberofSeats;
    Event selectedEvent;
    RideShareActivity parent;
    Button submitButton;
    Button cancelButton;
    RideDirection direction = null;
    Calendar calendar;
    long time;
    long date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        parent = (RideShareActivity) getParent();
        selectedEvent = EventsActivity.getEvent();
        name = (EditText) fragmentView.findViewById(R.id.name_prompt_input);
        datePicker = (DatePicker) fragmentView.findViewById(R.id.departure_date_picker);
        //datePicker.getCalendarView().setDate(0);
        timePicker = (TimePicker) fragmentView.findViewById(R.id.departure_time_picker);
        locations = (EditText) fragmentView.findViewById(R.id.list_of_locations);
        oneWayTo = (CheckBox) fragmentView.findViewById(R.id.One_Way_To_Checkbox);
        oneWayFrom = (CheckBox) fragmentView.findViewById(R.id.One_Way_From_Checkbox);
        twoWay = (CheckBox) fragmentView.findViewById(R.id.Two_Way_Checkbox);
        numberofSeats = (EditText) fragmentView.findViewById(R.id.number_of_seats);
        submitButton = (Button) fragmentView.findViewById(R.id.driver_form_submit);
        cancelButton = (Button) fragmentView.findViewById(R.id.driver_form_cancel);

        /*boolean checked = ((CheckBox) fragmentView).isChecked();
        switch(fragmentView.getId()) {
            case R.id.One_Way_From_Checkbox:
                if (checked) {
                    oneWayTo.setChecked(false);
                    twoWay.setChecked(false);
                    direction = RideDirection.ONE_WAY_FROM_EVENT;
                }
                break;
            case R.id.One_Way_To_Checkbox:
                if (checked) {
                    oneWayFrom.setChecked(false);
                    twoWay.setChecked(false);
                    direction = RideDirection.ONE_WAY_TO_EVENT;
                }
                break;
            case R.id.Two_Way_Checkbox:
                if (checked) {
                    oneWayTo.setChecked(false);
                    oneWayFrom.setChecked(false);
                    direction = RideDirection.TWO_WAY;
                }
                break;
        }*/

        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        0,
                        0);

                date = calendar.getTimeInMillis();
            }});

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar timeCal = new GregorianCalendar(0,
                        0,
                        0,
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                time = timeCal.getTimeInMillis();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "ride canceled", Toast.LENGTH_SHORT);
                //Go back to previous fragment
                //System.out.println("Submit clicked!");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DisplayEventInfoTask().execute();
                DriverForm form = new DriverForm(selectedEvent.getId());
                if (name.getText().length() > 0) {
                    form.answerQuestion(0, name.getText().toString());
                }
                if (datePicker.getCalendarView().getDate() != 0){
                    form.answerQuestion(1, date);
                }
                if (time > 0){
                    form.answerQuestion(2, time);
                }
                if (direction != null){
                    form.answerQuestion(3, direction);
                }

                if (locations.getText().length() > 0) {
                    form.answerQuestion(4, locations.getText().toString());
                }

                if (numberofSeats.getText().length() > 0){
                    form.answerQuestion(5, Integer.parseInt(numberofSeats.getText().toString()));
                }
                FormValidationResult result = form.isFinishedDetailed();
                if (result == FormValidationResult.VALID) {
                    form.submit();
                    Toast.makeText(parent, "ride added", Toast.LENGTH_SHORT);
                } else {
                    //not sure if this works but not sure what to put there.
                    result.getMessage(null);
                }
                System.out.println("Submit clicked!");
            }
        });

        return fragmentView;
    }

    private class DisplayEventInfoTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... params) {
            //RestUtil.create(Ride.toJSON(selectedEvent.getId(), new User("testDriver", "1234567"), 5, "test", RideDirection.ONE_WAY_TO_EVENT), Database.REST_RIDE);
            return null;
        }
    }


}
