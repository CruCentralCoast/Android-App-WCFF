package com.will_code_for_food.crucentralcoast.view.ridesharing;


import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Gender;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
public class RideInfoFragment extends CruFragment {

    TextView eventView;
    TextView driver;
    TextView pickup;
    TextView leaveTime;
    TextView direction;
    TextView rideInfo;
    Button actionButton;
    ImageButton mapButton;
    ImageButton passengerButton;
    Event event;
    Ride ride;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        ride = RideShareActivity.getRide();
        event = RideShareActivity.getEvent(ride);
        eventView = (TextView) fragmentView.findViewById(R.id.ride_info_event);
        driver = (TextView) fragmentView.findViewById(R.id.ride_info_driver);
        pickup = (TextView) fragmentView.findViewById(R.id.ride_info_loc);
        leaveTime = (TextView) fragmentView.findViewById(R.id.ride_info_time);
        direction = (TextView) fragmentView.findViewById(R.id.ride_info_direction);
        rideInfo = (TextView) fragmentView.findViewById(R.id.ride_info_more);
        actionButton = (Button) fragmentView.findViewById(R.id.btn_ride_info);
        mapButton = (ImageButton) fragmentView.findViewById(R.id.ride_info_map);
        passengerButton = (ImageButton) fragmentView.findViewById(R.id.ride_info_car);

        try {
            new SetButtonTask().execute().get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setRideInfo();
        setRideLocationInfo();
        setRideLeaveInfo();
        setRidePassengerInfo();

        return fragmentView;
    }

    private void setRideInfo() {
        // Set text for event info
        String eventText = Util.getString(R.string.ridesharing_event) + event.getName();
        eventView.setText(eventText);

        // Set text for driver info
        String driverText = Util.getString(R.string.ridesharing_driver) + ride.getDriverName();
        if (ride.getGender() != null) {
            driverText += " (" + getGenderFromEnum(ride.getGender()) + ")";
        }
        driver.setText(driverText);
    }

    private void setRideLocationInfo() {
        // Set text for pickup location
        String locText = Util.getString(R.string.ridesharing_pickup_location);
        final String mapUrl;
        if (ride.getLocation().getStreet() != null) {
            mapUrl = Database.GOOGLE_MAP + ride.getLocation().getStreet();
            locText += ride.getLocation().getStreet();
        } else {
            mapUrl = "";
            locText += Util.getString(R.string.ridesharing_unknown_location);
        }
        pickup.setText(locText);

        // Link location to Google maps
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap(mapUrl);
            }
        });
    }

    private void setRideLeaveInfo() {
        // Leaving time
        String timeText = Util.getString(R.string.ridesharing_time) + String.format(Util.getString(R.string.ridesharing_leaving_date),
                ride.getLeaveTime(), ride.getLeaveDate());
        leaveTime.setText(timeText);

        // Direction
        String dirText = Util.getString(R.string.ridesharing_direction) + ride.getDirection().toString();
        direction.setText(dirText);
    }

    private void setRidePassengerInfo() {
        String passengerInfo = getRideInfo();
        rideInfo.setText(passengerInfo);
    }

    private String getRideInfo() {
        String info = "";
        String myPhoneNumber = Util.getPhoneNum();
        Passenger me = RestUtil.getPassenger(myPhoneNumber);

        // Displays driver's information (if joined ride)
        if (me != null && ride.hasPassenger(me.getId())) {
            info += "Driver #: " + ride.getDriverNumber();
        }
        // Display passenger information (if my ride)
        else if (myPhoneNumber.equals(ride.getDriverNumber())) {
            info += "Passengers: ";
            JsonArray passengers = ride.getField(Database.JSON_KEY_RIDE_PASSENGERS).getAsJsonArray();

            if (passengers == null || passengers.size() < 1) {
                info += "none";
            } else {
                info += passengers.size();
            }
            passengerButton.setImageResource(R.drawable.car_yes);
            passengerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPassengers();
                }
            });
        }
        // Displays seats available (if not your ride)
        else {
            info += Util.getString(R.string.ridesharing_seats_available);
            if ((ride.getDirection() == RideDirection.ONE_WAY_TO_EVENT) || (ride.getDirection() == RideDirection.TWO_WAY)) {
                info += ride.getNumAvailableSeatsToEvent() + Util.getString(R.string.ridesharing_to_event);
            }
            if ((ride.getDirection() == RideDirection.ONE_WAY_FROM_EVENT) || (ride.getDirection() == RideDirection.TWO_WAY)) {
                info += ride.getNumAvailableSeatsFromEvent() + Util.getString(R.string.ridesharing_from_event);
            }
        }

        return info;
    }

    private void viewMap(String url) {
        // No map for this location
        if (url.equals("")) {
            Toast.makeText(getActivity(), Util.getString(R.string.toast_no_map),
                    Toast.LENGTH_LONG).show();
        }
        // Link to the Google Map page
        else {
            String map = url;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
        }
    }

    private void viewPassengers() {
        MainActivity currentActivity = getParent();
        currentActivity.loadFragmentById(R.layout.fragment_card_list, name + " > Passengers",
                new MyPassengersFragment(ride), currentActivity);
    }

    private String getGenderFromEnum(String num) {
        if (String.valueOf(Gender.FEMALE.getValue()).equals(num))
            return Util.getString(Gender.FEMALE.getNameId());
        else if (String.valueOf(Gender.MALE.getValue()).equals(num))
            return Util.getString(Gender.MALE.getNameId());
        else
            return "";
    }

    private class SetButtonTask extends AsyncTask<Void, Void, Void> {
        Passenger thisPassenger;
        String phoneNum;
        boolean rideJoined = false;
        boolean myRide = false;

        @Override
        protected Void doInBackground(Void... params) {
            phoneNum = Util.getPhoneNum();
            thisPassenger = RestUtil.getPassenger(phoneNum);
            rideJoined = thisPassenger != null && ride.hasPassenger(thisPassenger.getId());
            myRide = (phoneNum.equals(ride.getDriverNumber()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (rideJoined) {
                actionButton.setText(Util.getString(R.string.ridesharing_leave));
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //drop ride
                        new DropPassenger(thisPassenger).execute();
                        DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT);
                        setToJoin();
                    }
                });
            } else if (myRide) {
                actionButton.setText(Util.getString(R.string.ridesharing_cancel));
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cancel ride
                        new DropRide().execute();
                        DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT);
                    }
                });
            } else {
                setToJoin();
            }
        }
    }

    public void setToJoin() {
        actionButton.setText(Util.getString(R.string.ridesharing_join));

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNameDialog popup = new EnterNameDialog(getParent(), ride, event);
                FragmentManager manager = getFragmentManager();
                popup.show(manager, "ride_info_enter_name");
            }
        });
    }

    private class DropRide extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JsonArray passengers = ride.getField(Database.JSON_KEY_RIDE_PASSENGERS).getAsJsonArray();
            for (int i = 0; i < passengers.size(); i++) {
                RestUtil.dropPassenger(ride.getId(), passengers.get(i).getAsString());
            }
            RestUtil.delete(ride, Database.REST_RIDE);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getParent(), "Cancelled Ride", Toast.LENGTH_SHORT).show();
        }
    }

    private class DropPassenger extends AsyncTask<Void, Void, Void> {
        Passenger thisPassenger;

        public DropPassenger(Passenger thisPassenger) {
            this.thisPassenger = thisPassenger;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestUtil.dropPassenger(ride.getId(), thisPassenger.getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getParent(), "Left Ride", Toast.LENGTH_SHORT).show();
        }
    }
}