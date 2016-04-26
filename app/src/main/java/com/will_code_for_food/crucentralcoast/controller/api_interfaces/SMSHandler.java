package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Mallika on 11/17/15.
 * Class to demonstrate SMS messaging capability.
 */
public class SMSHandler {

    public static void sendSMS(Activity currentActivity, final String number, final String msg) {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", msg);
            sendIntent.putExtra("address"  , number);
            sendIntent.setType("vnd.android-dir/mms-sms");
            currentActivity.startActivity(sendIntent);
        } catch (Exception ex) {
            Toast.makeText(currentActivity.getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}