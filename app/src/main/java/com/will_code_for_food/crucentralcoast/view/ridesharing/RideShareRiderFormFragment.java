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
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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
public class RideShareRiderFormFragment extends CruFragment implements OnMapReadyCallback {
    private RiderForm form;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText name;
    EditText number;
    //EditText locations;
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

    private ScrollView scrollView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        scrollView = (ScrollView) fragmentView.findViewById(R.id.rider_form_scroll);
        loadMap();

        parent = getParent();
        selectedEvent = EventsActivity.getEvent();
        name = (EditText) fragmentView.findViewById(R.id.name_prompt_input);
        number = (EditText) fragmentView.findViewById(R.id.number_prompt_input);
        male = (RadioButton) fragmentView.findViewById(R.id.male);
        female = (RadioButton) fragmentView.findViewById(R.id.female);
        datePicker = (DatePicker) fragmentView.findViewById(R.id.departure_date_picker);
        timePicker = (TimePicker) fragmentView.findViewById(R.id.departure_time_picker);
        //locations = (EditText) fragmentView.findViewById(R.id.list_of_locations);
        oneWayTo = (RadioButton) fragmentView.findViewById(R.id.One_Way_To_Checkbox);
        oneWayFrom = (RadioButton) fragmentView.findViewById(R.id.One_Way_From_Checkbox);
        twoWay = (RadioButton) fragmentView.findViewById(R.id.Two_Way_Checkbox);
        submitButton = (Button) fragmentView.findViewById(R.id.driver_form_submit);
        cancelButton = (Button) fragmentView.findViewById(R.id.driver_form_cancel);

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
                //if (hasValidTextInput(locations)) {
                //    form.answerQuestion(5, locations.getText().toString());
                //}
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

    /**
     * The mapfragment's id must be removed from the FragmentManager or else the app will crash when
     * you go back to the page with the map and it tries to load a duplicate fragment id.
     **/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            getParent().destroyMapFragment(R.id.rider_form_map);
            mMap = null;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng slo = new LatLng(35.2828, -120.659485);
        //mMap.addMarker(new MarkerOptions().position(slo).title("Marker in San Luis Obispo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(slo));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
    }

    public void loadMap() {

        if (mMap == null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            WorkaroundMapFragment mapFragment = getParent().getMapFragment(R.id.rider_form_map);

            mapFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });

            mapFragment.getMapAsync(this);
        }
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
