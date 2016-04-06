package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
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
    private String phoneNum;
    private String directionPreference;
    private Ride ride;

    public RegisterForRideTask(MainActivity parent, String passengerName, String number, String directionPreference, Ride ride) {
        this.parent = parent;
        this.passengerName = passengerName;
        this.directionPreference = directionPreference;
        this.ride = ride;
        this.phoneNum = number;
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
            result = RestUtil.create(Passenger.toJSON(passengerName, phoneNum, "dummy_id", directionPreference), Database.REST_PASSENGER);
        } else {
            //update existing passenger
            result = RestUtil.update(Passenger.toJSON(passenger.getId(), passengerName, phoneNum, "dummy_id", directionPreference), Database.REST_PASSENGER);
        }

        //update ride
        if ((ride.hasPassenger(new Passenger(result).getId()))) {
            System.out.println("Ride has passenger");
        }

        if ((result != null) && ((ride.hasPassenger(new Passenger(result).getId())) || (RestUtil.addPassenger(ride.getId(), new Passenger(result).getId())))) {
            //TODO: Notify driver

            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "Ride Joined", Toast.LENGTH_SHORT).show();
                    parent.loadFragmentById(R.layout.fragment_my_rides_list,
                            Util.getString(R.string.ridesharing_my_rides_title),
                            new MyRidesFragment(), parent);
                }
            });

            //TODO: load "MyRides" fragment
        } else {
            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "Error", Toast.LENGTH_SHORT).show();
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