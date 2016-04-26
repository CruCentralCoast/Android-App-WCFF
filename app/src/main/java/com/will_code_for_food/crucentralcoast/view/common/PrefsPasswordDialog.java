package com.will_code_for_food.crucentralcoast.view.common;

/**
 * Created by MasonJStevenson on 4/13/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RiderForm;
import com.will_code_for_food.crucentralcoast.view.ridesharing.EnterNumberDialog;

/**
 * Created by MasonJStevenson on 2/16/2016.
 */
//I will fix the warning for this class later -Mason
public class PrefsPasswordDialog extends DialogFragment {

    Activity parent;
    PrefsFragment prefsFragment;

    public PrefsPasswordDialog(PrefsFragment prefsFragment) {
        parent = (Activity) SettingsActivity.context;
        this.prefsFragment = prefsFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final EditText input = new EditText(parent);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input)
                .setTitle("Password?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //send password to parent
                        prefsFragment.enableDeveloperOptions(input.getText().toString());
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
