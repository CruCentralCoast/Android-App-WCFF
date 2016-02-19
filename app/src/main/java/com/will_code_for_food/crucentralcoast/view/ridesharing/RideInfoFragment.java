package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
public class RideInfoFragment extends CruFragment {

    TextView title;
    TextView driver;
    TextView main;
    Button actionButton;
    Event event;
    Ride ride;
    String passengerName = "test";
    String directionPreference = "both";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        ride = RideShareActivity.getRide();
        event = RideShareActivity.getEvent(ride);

        title = (TextView) fragmentView.findViewById(R.id.ride_info_title);
        driver = (TextView) fragmentView.findViewById(R.id.ride_info_driver);
        main = (TextView) fragmentView.findViewById(R.id.ride_info_main);
        actionButton = (Button) fragmentView.findViewById(R.id.btn_ride_info);

        title.setText("Event: " + event.getName());

        String driverText = "Driver: " + ride.getDriverName();
        if (ride.getGender() != null) {
            driverText += " (" + ride.getGender() + ")";
        }
        driver.setText(driverText);

        String mainText = "";
        mainText += "Pickup Location: ";
        if (ride.getLocation().getStreet() != null) {
         mainText += ride.getLocation().getStreet();
        } else {
            mainText += "unknown";
        }
        mainText += "\nTime: " + ride.getTime() + "\n";
        mainText += "Seats Available: ";

        if ((ride.getDirection() == RideDirection.ONE_WAY_TO_EVENT) || (ride.getDirection() == RideDirection.TWO_WAY)) {
            mainText += ride.getNumAvailableSeatsToEvent() + " to event -- ";
        }

        if ((ride.getDirection() == RideDirection.ONE_WAY_FROM_EVENT) || (ride.getDirection() == RideDirection.TWO_WAY)) {
            mainText += ride.getNumAvailableSeatsFromEvent() + " from event";
        }

        mainText += "\nDirection: " + ride.getDirection().toString() + "\n";

        main.setText(mainText);

        try {
            new SetButtonTask().execute().get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragmentView;
    }

    private class SetButtonTask extends AsyncTask<Void, Void, Void> {

        Passenger thisPassenger;
        String phoneNum;
        boolean rideJoined = false;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //get this phone number
                TelephonyManager tMgr = (TelephonyManager) getParent().getSystemService(Context.TELEPHONY_SERVICE);
                phoneNum = tMgr.getLine1Number();
            } catch (java.lang.SecurityException e) {
                //triggered if emulator is in use
                phoneNum = "EMULATOR";
            }

            thisPassenger = RestUtil.getPassenger(phoneNum);

            rideJoined = ((thisPassenger != null) && ride.hasPassenger(thisPassenger.getId()));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (rideJoined) {

                actionButton.setText("Leave Ride");

                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //drop ride
                        new DropPassenger(thisPassenger).execute();
                        setToJoin();
                    }
                });
            } else {
                setToJoin();
            }
        }
    }

    public void setToJoin() {
        actionButton.setText("Join Ride");

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNameDialog popup = new EnterNameDialog(getParent(), ride);
                FragmentManager manager = getFragmentManager();
                popup.show(manager, "ride_info_enter_name");
            }
        });
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
