package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
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
    private Event event;

    public SelectDirectionDialog(final MainActivity parent, final String passengerName,
                                 final String number, final Ride ride, final Event event) {
        this.parent = parent;
        this.passengerName = passengerName;
        this.number = number;
        this.ride = ride;
        this.event = event;
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
                .setPositiveButton("join", null)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });

        // Create the AlertDialog object and return it
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        handleOk();
                    }
                });
            }
        });

        return alertDialog;
    }

    private void handleOk() {

        if (directionPreference != null) {
            new RegisterForRideTask(parent, passengerName, number,
                    directionPreference, ride, event).execute();
            dismiss();
        } else {
            Toast.makeText(parent, "Please select a direction", Toast.LENGTH_SHORT).show();
        }
    }
}