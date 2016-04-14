package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RiderForm;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
//I will fix the warning for this class later -Mason
public class EnterNameDialog extends DialogFragment {

    final MainActivity parent;
    final Ride ride;
    String passengerName;
    final Event event;

    public EnterNameDialog(final MainActivity parent, final Ride ride, final Event event) {
        this.parent = parent;
        this.ride = ride;
        this.event = event;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Set up the input
        final EditText input = new EditText(parent);
        final RiderForm form = new RiderForm("");
        // auto-fill name
        if (form.getQuestion(0).isAnswered()) {
            input.setText((String) form.getQuestion(0).getAnswer());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input)
                .setTitle("Enter Your Name")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //store the passenger's name
                        passengerName = input.getText().toString();
                        EnterNumberDialog popup = new EnterNumberDialog(parent, passengerName,
                                ride, event);
                        FragmentManager manager = getFragmentManager();
                        popup.show(manager, "ride_info_select_number");
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