package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.common.messaging.PushUtil;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
public class RegisterForRideTask extends AsyncTask<Void, Void, Void> {

    private MainActivity parent;
    private String passengerName;
    private String phoneNum;
    private String directionPreference;
    private Ride ride;
    private Event event;

    public RegisterForRideTask(final MainActivity parent, final String passengerName,
                               final String number, final String directionPreference,
                               final Ride ride, final Event event) {
        this.parent = parent;
        this.passengerName = passengerName;
        this.directionPreference = directionPreference;
        this.ride = ride;
        this.phoneNum = number;
        this.event = event;
    }

    @Override
    protected Void doInBackground(Void... params) {
        JsonObject result;
        Passenger passenger;

        //try to find passenger in db
        passenger = RestUtil.getPassenger(phoneNum);

        //if passenger doesn't exist
        if (passenger == null) {
            //push new passenger into db
            result = RestUtil.create(Passenger.toJSON(passengerName, phoneNum, PushUtil.getGCMId(), directionPreference), Database.REST_PASSENGER);
        } else {
            //update existing passenger
            result = RestUtil.update(Passenger.toJSON(passenger.getId(), passengerName, phoneNum, PushUtil.getGCMId(), directionPreference), Database.REST_PASSENGER);
        }

        //update ride
        if ((ride.hasPassenger(new Passenger(result).getId()))) {
            System.out.println("Ride has passenger");
        }

        if ((result != null) && ((ride.hasPassenger(new Passenger(result).getId()))
                || (RestUtil.addPassenger(ride.getId(), new Passenger(result).getId())))) {
            notifyDriver(passengerName, ride, directionPreference);

            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "Ride Joined", Toast.LENGTH_SHORT).show();
                    DBObjectLoader.loadRides(Database.DB_TIMEOUT);
                    parent.loadFragmentById(R.layout.fragment_my_rides_list,
                            Util.getString(R.string.ridesharing_my_rides_title),
                            new MyRidesFragment(), parent);
                }
            });

            //TODO: load "MyRides" fragment
        } else {
            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "Error in joining ride", Toast.LENGTH_SHORT).show();
                }
            });
        }

        LocalStorageIO.appendToList(ride.getId(), LocalFiles.USER_RIDES);

        return null;
    }

    private void notifyDriver(final String passengerName, final Ride ride,
                              final String directionPreference) {
        Log.i("Signing up for ride", "Notifying driver of a new rider");
        // creating message
        String msg = "Hi! My name is %s, and I just used the CRU app to sign up "
                + "to ride in your car to %s.";
        msg = String.format(msg, passengerName, event.getName());
        if (ride.isTwoWay()) {
            msg += String.format(" I'm hoping to get a ride %s the event, thanks!",
                    directionPreference != "both" ? directionPreference : "both to and from");
        }
        SMSHandler.sendSMS(parent, ride.getDriverNumber(), msg);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}