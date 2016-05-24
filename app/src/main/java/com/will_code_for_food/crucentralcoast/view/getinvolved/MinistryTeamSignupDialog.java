package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.messaging.Notifier;
import com.will_code_for_food.crucentralcoast.model.common.messaging.SMSNotifier;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by Brian on 3/2/2016.
 */
public class MinistryTeamSignupDialog extends DialogFragment {

    MainActivity parent;
    MinistryTeam team;
    String passengerName;

    static MinistryTeamSignupDialog newInstance(MinistryTeam team) {
        MinistryTeamSignupDialog dialog = new MinistryTeamSignupDialog();
        dialog.team = team;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_ministryteam_signup, null);

        // Set up the input
        final EditText nameInput = (EditText) view.findViewById(R.id.name_prompt);
        final EditText emailInput = (EditText) view.findViewById(R.id.email_prompt);
        final EditText numberInput = (EditText) view.findViewById(R.id.number_prompt);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle(Util.getString(R.string.ministry_team_signup_header))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String number = numberInput.getText().toString();

                // create an SMS notifier to send a text to the user
                SMSNotifier notifier = new SMSNotifier(getActivity(), Util.getString(R.string.phone_number));
                team.signup(name, email, number, notifier);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
