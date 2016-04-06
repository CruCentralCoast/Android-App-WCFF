package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.PhoneNumberAccessor;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Gender;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResultType;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RiderForm;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ShelliCrispen on 2/16/16.
 */
public class RideShareRiderFormFragment extends CruFragment{
    private RiderForm form;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText name;
    EditText number;
    EditText locations;
    RadioButton oneWayTo;
    RadioButton oneWayFrom;
    RadioButton twoWay;
    Event selectedEvent;
    MainActivity parent;
    Button submitButton;
    Button cancelButton;
    RideDirection direction = null;

    RadioButton male;
    RadioButton female;
    Gender gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        parent = getParent();
        selectedEvent = EventsActivity.getEvent();
        name = (EditText) fragmentView.findViewById(R.id.name_prompt_input);
        number = (EditText) fragmentView.findViewById(R.id.number_prompt_input);
        male = (RadioButton) fragmentView.findViewById(R.id.male);
        female = (RadioButton) fragmentView.findViewById(R.id.female);
        datePicker = (DatePicker) fragmentView.findViewById(R.id.departure_date_picker);
        timePicker = (TimePicker) fragmentView.findViewById(R.id.departure_time_picker);
        locations = (EditText) fragmentView.findViewById(R.id.list_of_locations);
        oneWayTo = (RadioButton) fragmentView.findViewById(R.id.One_Way_To_Checkbox);
        oneWayFrom = (RadioButton) fragmentView.findViewById(R.id.One_Way_From_Checkbox);
        twoWay = (RadioButton) fragmentView.findViewById(R.id.Two_Way_Checkbox);
        submitButton = (Button) fragmentView.findViewById(R.id.driver_form_submit);
        cancelButton = (Button) fragmentView.findViewById(R.id.driver_form_cancel);

        hideKeyboardOnUnfocus(name, number, locations);

        form = new RiderForm(selectedEvent.getId());
        form.print();

        // auto-fill name
        if (form.getQuestion(0).isAnswered()) {
            name.setText((String) form.getQuestion(0).getAnswer());
        }

        // auto-fill number if possible
        String number_str = PhoneNumberAccessor.getUserPhoneNumber(parent);
        if (number_str != null) {
            form.getQuestion(1).answerQuestion(number_str);
            number.setText(number_str);
        }

        // auto-fill gender
        if (form.getQuestion(2).isAnswered()) {
            Gender userGender = (Gender) form.getQuestion(2).getAnswer();
            switch (userGender) {
                case MALE:
                    male.setChecked(true);
                    break;
                case FEMALE:
                    female.setChecked(true);
                    break;
                default:
                    // default is male (could be decline to state or something)
                    male.setChecked(true);
            }
        }

        oneWayFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = RideDirection.ONE_WAY_FROM_EVENT;
            }});
        oneWayTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = RideDirection.ONE_WAY_TO_EVENT;
            }});
        twoWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = RideDirection.TWO_WAY;
            }});

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = Gender.MALE;
            }});
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = Gender.FEMALE;
            }});

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "ride canceled", Toast.LENGTH_SHORT).show();
                //Go back to previous fragment
                //System.out.println("Submit clicked!");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DisplayEventInfoTask().execute();
                if (hasValidTextInput(name)) {
                    form.answerQuestion(0, name.getText().toString());
                }
                if (gender != null){
                    form.answerQuestion(2, gender);
                }
                // set time
                Calendar cal =
                        new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(), datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                form.answerQuestion(3, cal);

                if (direction != null){
                    form.answerQuestion(4, direction);
                }
                if (hasValidTextInput(locations)) {
                    form.answerQuestion(5, locations.getText().toString());
                }
                if (form.isFinished()) {
                    Toast.makeText(parent, "Submitted Rider Form", Toast.LENGTH_SHORT).show();
                    form.submit();
                } else {
                    // error
                    Toast.makeText(parent, "Error!", Toast.LENGTH_SHORT).show();
                    form.print();
                    List<FormValidationResult> results = form.isFinishedDetailed();
                    // TODO these are all the errors returned by the form validation
                    // TODO we should do this in the same way as dynamic forms
                    for(FormValidationResult result : results) {
                        Log.e("Form Error:", result.getMessage(parent));
                    }
                }
            }
        });

        return fragmentView;
    }

    private boolean hasValidTextInput(final EditText e) {
        return e.getText() != null && e.getText().toString().length() > 0;
    }

    private class DisplayEventInfoTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... params) {
            //RestUtil.create(Ride.toJSON(selectedEvent.getId(), new User("testDriver", "1234567"), 5, "test", RideDirection.ONE_WAY_TO_EVENT), Database.REST_RIDE);
            return null;
        }
    }


}
