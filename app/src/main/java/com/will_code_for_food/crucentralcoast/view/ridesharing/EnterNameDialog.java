package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
//I will fix the warning for this class later -Mason
public class EnterNameDialog extends DialogFragment {

    MainActivity parent;
    Ride ride;
    String passengerName;

    public EnterNameDialog(MainActivity parent, Ride ride) {
        this.parent = parent;
        this.ride = ride;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Set up the input
        final EditText input = new EditText(parent);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input)
                .setTitle("Enter Your Name")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //store the passenger's name
                        passengerName = input.getText().toString();

                        //if the ride is two-way, the passenger can choose to only go one direction
                        if (ride.getDirection() == RideDirection.TWO_WAY) {
                            SelectDirectionDialog popup = new SelectDirectionDialog(parent, passengerName, ride);
                            FragmentManager manager = getFragmentManager();
                            popup.show(manager, "ride_info_select_direction");
                        } else {
                            new RegisterForRideTask(parent, passengerName, ride.getDirection().toString(), ride).execute();
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
