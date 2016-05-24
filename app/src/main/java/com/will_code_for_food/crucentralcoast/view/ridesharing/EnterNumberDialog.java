package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by Gavin on 2/16/2016.
 */
//I will fix the warning for this class later -Mason
public class EnterNumberDialog extends DialogFragment {

    private MainActivity parent;
    private String number;
    private Ride ride;
    private String directionPreference;
    private String passengerName;
    private Event event;

    public EnterNumberDialog(final MainActivity parent, final String passengerName,
                             final Ride ride, final Event event) {
        this.parent = parent;
        this.number = passengerName;
        this.ride = ride;
        this.passengerName = passengerName;
        this.event = event;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText input = new EditText(parent);
        input.setText(Util.getPhoneNum());
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input)
                .setTitle("Enter Phone Number")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //store the passenger's name
                        number = input.getText().toString();

                        //if the ride is two-way, the passenger can choose to only go one direction
                        if (ride.getDirection() == RideDirection.TWO_WAY) {
                            SelectDirectionDialog popup =
                                    new SelectDirectionDialog(parent, new RegisterForRideTask(parent, passengerName, number,
                                            ride, event));
                            FragmentManager manager = getFragmentManager();
                            popup.show(manager, "ride_info_select_direction");
                        } else {
                            new RegisterForRideTask(parent, passengerName, number,
                                    ride.getDirection().toString(), ride, event).execute();
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