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

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
public class RideInfoFragment extends CruFragment {

    TextView title;
    TextView driver;
    TextView main;
    Button joinButton;
    Event event;
    Ride ride;
    String passengerName = "test";
    String directionPreference = "both";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        ride = RideShareActivity.getRide();
        event = EventsActivity.getEvent();

        title = (TextView) fragmentView.findViewById(R.id.ride_info_title);
        driver = (TextView) fragmentView.findViewById(R.id.ride_info_driver);
        main = (TextView) fragmentView.findViewById(R.id.ride_info_main);
        joinButton = (Button) fragmentView.findViewById(R.id.ride_info_join);

        title.setText("Event: " + event.getName());

        String driverText = "";
        driverText += "Driver: " + ride.getDriverName() + " (" + ride.getGender() + ")\n";
        driver.setText(driverText);

        String mainText = "";
        mainText += "Pickup Location: " + ride.getLocation().getStreet() + "\n";
        mainText += "Time: " + ride.getTime() + "\n";
        mainText += "Seats Available: ";

        if ((ride.getDirection() == RideDirection.ONE_WAY_TO_EVENT) || (ride.getDirection() == RideDirection.TWO_WAY)) {
            mainText += ride.getNumAvailableSeatsToEvent() + " to event -- ";
        }

        if ((ride.getDirection() == RideDirection.ONE_WAY_FROM_EVENT) || (ride.getDirection() == RideDirection.TWO_WAY)) {
            mainText += ride.getNumAvailableSeatsFromEvent() + " from event";
        }

        mainText += "\nDirection: " + ride.getDirection().toString() + "\n";

        main.setText(mainText);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNameDialog popup = new EnterNameDialog();
                FragmentManager manager = getFragmentManager();
                popup.show(manager, "ride_info_enter_name");
            }
        });

        return fragmentView;
    }

    private class RegisterForRide extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String phoneNum;
            JsonObject result;

            try {
                //get this phone number
                TelephonyManager tMgr = (TelephonyManager)getParent().getSystemService(Context.TELEPHONY_SERVICE);
                phoneNum = tMgr.getLine1Number();
            } catch (java.lang.SecurityException e) {
                //triggered if emulator is in use
                phoneNum = "EMULATOR";
            }

            //push passenger into db
            result = RestUtil.create(Passenger.toJSON(passengerName, phoneNum, "dummy_id", directionPreference), Database.REST_PASSENGER);

            //update ride
            if ((result != null) && (RestUtil.addPassenger(ride.getId(), new Passenger(result).getId()))) {
                //TODO: Notify driver

                getParent().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getParent(), "You have been added as a passenger", Toast.LENGTH_LONG).show();
                    }
                });

                //TODO: load "MyRides" fragment
            } else {
                getParent().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getParent(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    //I will fix the warning for this class later -Mason
    public class EnterNameDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Set up the input
            final EditText input = new EditText(getParent());

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(input)
                    .setTitle("Enter Your Name")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //if the ride is two-way, the passenger can choose to only go one direction
                            if (ride.getDirection() == RideDirection.TWO_WAY) {

                                //store the passenger's name
                                passengerName = input.getText().toString();

                                SelectDirectionDialog popup = new SelectDirectionDialog();
                                FragmentManager manager = getFragmentManager();
                                popup.show(manager, "ride_info_select_direction");
                            } else {
                                new RegisterForRide().execute();
                            }
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dismiss();
                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    //I will fix the warning for this class later -Mason
    public class SelectDirectionDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final CharSequence[] choices = {"to", "from", "both"};

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Direction")
                    .setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            directionPreference = choices[which].toString();
                        }
                    })
                    .setPositiveButton("join", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new RegisterForRide().execute();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dismiss();
                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
