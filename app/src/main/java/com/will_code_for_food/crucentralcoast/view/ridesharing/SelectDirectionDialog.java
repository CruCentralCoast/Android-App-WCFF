package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
//I will fix the warning for this class later -Mason
public class SelectDirectionDialog extends DialogFragment {

    private MainActivity parent;
    private String passengerName;
    private Ride ride;
    private String directionPreference;
    private String number;

    public SelectDirectionDialog(MainActivity parent, String passengerName, String number, Ride ride) {
        this.parent = parent;
        this.passengerName = passengerName;
        this.number = number;
        this.ride = ride;
    }

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
                        new RegisterForRideTask(parent, passengerName, number, directionPreference, ride).execute();
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