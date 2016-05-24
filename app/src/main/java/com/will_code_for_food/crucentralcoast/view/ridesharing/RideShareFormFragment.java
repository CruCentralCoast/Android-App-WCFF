package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.PhoneNumberAccessor;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Gender;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.common.MyApplication;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by MasonJStevenson on 4/1/2016.
 */
public abstract class RideShareFormFragment extends CruFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, PlaceSelectionListener {

    Form form;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText name;
    EditText number;
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
    private Location selectedLocation = null;
    private PlaceAutocompleteFragment locationSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        Logger.i("RideShareFormFragment", "*");
        initComponents(fragmentView);
        Logger.i("RideShareFormFragment", "**");
        loadMap();
        Logger.i("RideShareFormFragment", "***");
        autoFill();
        Logger.i("RideShareFormFragment", "****");
        setListeners();
        Logger.i("RideShareFormFragment", "*****");

        return fragmentView;
    }

    private void initComponents(View fragmentView) {
        scrollView = (ScrollView) fragmentView.findViewById(R.id.rideshare_form_scroll);
        parent = getParent();
        selectedEvent = EventsActivity.getEvent();
        name = (EditText) fragmentView.findViewById(R.id.name_prompt_input);
        number = (EditText) fragmentView.findViewById(R.id.number_prompt_input);
        male = (RadioButton) fragmentView.findViewById(R.id.male);
        female = (RadioButton) fragmentView.findViewById(R.id.female);
        datePicker = (DatePicker) fragmentView.findViewById(R.id.departure_date_picker);
        timePicker = (TimePicker) fragmentView.findViewById(R.id.departure_time_picker);
        oneWayTo = (RadioButton) fragmentView.findViewById(R.id.One_Way_To_Checkbox);
        oneWayFrom = (RadioButton) fragmentView.findViewById(R.id.One_Way_From_Checkbox);
        twoWay = (RadioButton) fragmentView.findViewById(R.id.Two_Way_Checkbox);
        submitButton = (Button) fragmentView.findViewById(R.id.driver_form_submit);
        cancelButton = (Button) fragmentView.findViewById(R.id.driver_form_cancel);

        hideKeyboardOnUnfocus(name, number);
        unfocusOnEnterKey(name, number);

        form = getForm(selectedEvent.getId());
        form.print();

        locationSelector = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.location_selector);
        locationSelector.setHint(Util.getString(R.string.ridesharing_location));
    }

    /**
     * Gets the correct form from the child class
     */
    public abstract Form getForm(String eventId);

    private void autoFill() {
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
    }

    private void setListeners() {
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
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = Gender.MALE;
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = Gender.FEMALE;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "ride canceled", Toast.LENGTH_SHORT).show();
                //Go back to previous fragment
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerQuestions();
                submitForm();
            }
        });

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        locationSelector.setOnPlaceSelectedListener(this);
    }

    private void answerQuestions() {
        //answer name question
        if (hasValidTextInput(name)) {
            form.answerQuestion(0, name.getText().toString());
        }

        //answer number question
        if (hasValidTextInput(number)) {
            form.answerQuestion(1, number.getText().toString());
        }

        //answer gender question
        if (gender != null){
            form.answerQuestion(2, gender);
        }

        // set time and answer date/time question
        Calendar cal =
                new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        form.answerQuestion(3, cal);

        //answer direction question
        if (direction != null){
            form.answerQuestion(4, direction);
        }

        if (selectedLocation != null) {
            form.answerQuestion(5, selectedLocation);
        }

        //answer any other questions the child form might have
        answerAdditionalQuestions();
    }

    /**
     * Answer any additional questions the child form might have
     */
    public abstract void answerAdditionalQuestions();

    private void submitForm() {

        if (form.isFinished()) {
            Toast.makeText(parent, "Submitted Form", Toast.LENGTH_SHORT).show();
            form.submit();
            submitFormAdditionalActions();
            DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT);
        } else {
            // error
            Toast.makeText(parent, "Error!", Toast.LENGTH_SHORT).show();
            form.print();
            List<FormValidationResult> results = form.isFinishedDetailed();
            // TODO these are all the errors returned by the form validation
            // TODO we should do this in the same way as dynamic forms
            for(FormValidationResult result : results) {
                Logger.e("Form Error:", result.getMessage(parent));
            }
        }
    }

    /**
     * Any additional behavior that a child wants to execute when the form is submitted
     */
    public abstract void submitFormAdditionalActions();

    /**
     * The mapfragment's id must be removed from the FragmentManager or else the app will crash when
     * you go back to the page with the map and it tries to load a duplicate fragment id.
     **/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            getParent().destroySupportFragment(R.id.rideshare_form_map);
            getParent().destroyFragment(R.id.location_selector);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(slo));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));

        mMap.setOnMapClickListener(this);
    }

    public void loadMap() {

        if (mMap == null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getParent().getSupportFragmentManager().findFragmentById(R.id.rideshare_form_map);

            Logger.i("RideShareFormFragment", "setting map fragment listener");
            mapFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });

            mapFragment.getMapAsync(this);
        } else {
            Logger.i("RideShareFormFragment", "mMap was not null");
        }
    }

    public boolean hasValidTextInput(final EditText e) {
        return e.getText() != null && e.getText().toString().length() > 0;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        Geocoder geocoder = new Geocoder(MyApplication.getContext());
        Address selectedAddress;
        LatLng displayLoc = latLng;
        String locTitle = "custom location";

        //find the nearest address
        selectedAddress = getNearestAddress(latLng);

        if (selectedAddress != null) {
            displayLoc = new LatLng(selectedAddress.getLatitude(), selectedAddress.getLongitude());
            locTitle = selectedAddress.getAddressLine(0);

            selectedLocation = new Location(selectedAddress);

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(displayLoc).title(locTitle)).showInfoWindow();

            locationSelector.setText("");
        }
    }

    private Address getNearestAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(MyApplication.getContext());
        Address selectedAddress;

        try {
            //find the nearest address
            selectedAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            return selectedAddress;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Address selectedAddress;

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title((String) place.getName()));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));


        selectedAddress = getNearestAddress(place.getLatLng());

        if (selectedAddress != null) {
            selectedLocation = new Location(selectedAddress);
        } else {
            Logger.e("RideShareFormFragment", "Couldn't find nearest address");
            Toast.makeText(getParent(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Logger.e("RideShareFormFragment", "onError: Status = " + status.toString());

        Toast.makeText(getParent(), "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }
}
