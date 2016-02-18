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
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
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
                EnterNameDialog popup = new EnterNameDialog(getParent(), ride);
                FragmentManager manager = getFragmentManager();
                popup.show(manager, "ride_info_enter_name");
            }
        });

        return fragmentView;
    }
}
