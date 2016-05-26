package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.common.messaging.Notifier;
import com.will_code_for_food.crucentralcoast.model.common.messaging.PushUtil;
import com.will_code_for_food.crucentralcoast.model.common.messaging.SMSNotifier;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.common.MyApplication;

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

        //try to find passenger in db
        result = RestUtil.create(Passenger.toJSON(passengerName, phoneNum, PushUtil.getGCMId(), directionPreference), Database.REST_PASSENGER);

        // check for internet connection
        if (!Util.isNetworkAvailable(parent)) {
            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        //update ride
        if ((ride.hasPassenger(new Passenger(result).getId()))) {
            System.out.println("Ride has passenger");
        }

        if ((result != null) && ((ride.hasPassenger(new Passenger(result).getId()))
                || (RestUtil.addPassenger(ride.getId(), new Passenger(result).getId())))) {
            // create a notifier to send a message to the driver
            Notifier notifier = new SMSNotifier(MainActivity.context, ride.getDriverNumber());
            notifyDriver(passengerName, ride, directionPreference, notifier);

            parent.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(parent, "Ride Joined", Toast.LENGTH_SHORT).show();
                    DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT);
                    DBObjectLoader.loadObjects(RetrieverSchema.PASSENGER, Database.DB_TIMEOUT);
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
                              final String directionPreference, final Notifier notifier) {
        Logger.i("Signing up for ride", "Notifying driver of a new rider");
        // creating message
        String msg = "Hi! My name is %s, and I just used the CRU app to sign up "
                + "to ride in your car to %s.";
        msg = String.format(msg, passengerName, event.getName());
        if (ride.isTwoWay()) {
            msg += String.format(" I'm hoping to get a ride %s the event, thanks!",
                    directionPreference != "both" ? directionPreference : "both to and from");
        }
        notifier.notify(msg);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}