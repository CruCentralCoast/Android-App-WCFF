package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
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
    private String directionPreference;
    private Ride ride;

    public RegisterForRideTask(MainActivity parent, String passengerName, String directionPreference, Ride ride) {
        this.parent = parent;
        this.passengerName = passengerName;
        this.directionPreference = directionPreference;
        this.ride = ride;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String phoneNum;
        JsonObject result;
        Passenger passenger;

        try {
            //get this phone number
            TelephonyManager tMgr = (TelephonyManager) parent.getSystemService(Context.TELEPHONY_SERVICE);
            phoneNum = tMgr.getLine1Number();
        } catch (java.lang.SecurityException e) {
            //triggered if emulator is in use
            phoneNum = "EMULATOR";
        }

        //try to find passenger in db
        passenger = RestUtil.getPassenger(phoneNum);

        //if passenger doesn't exist
        if (passenger == null) {
            //push new passenger into db
            result = RestUtil.create(Passenger.toJSON(passengerName, phoneNum, "dummy_id", directionPreference), Database.REST_PASSENGER);
        } else {
            //update existing passenger
            result = RestUtil.update(Passenger.toJSON(passenger.getId(), passengerName, phoneNum, "dummy_id", directionPreference), Database.REST_PASSENGER);
        }

        //update ride
        if ((result != null) && ((ride.hasPassenger(new Passenger(result).getId())) || (RestUtil.addPassenger(ride.getId(), new Passenger(result).getId())))) {
            //TODO: Notify driver

            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "You have been added as a passenger", Toast.LENGTH_LONG).show();
                }
            });

            //TODO: load "MyRides" fragment
        } else {
            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }

        LocalStorageIO.appendToList(ride.getId(), LocalFiles.USER_RIDES);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }


}