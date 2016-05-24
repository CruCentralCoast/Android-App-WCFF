package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.controller.Logger;

/**
 * Handles the interface between our application and
 * the SMS application on the user's phone
 */
public class SMSHandler {

    /**
     * Sends a text message via an intent
     */
    public static boolean sendSMS(Context context, final String number, final String msg) {
        try {
            Logger.i("Send SMS", "Sending SMS to " + number);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", msg);
            sendIntent.putExtra("address", number);
            sendIntent.setType("vnd.android-dir/mms-sms");
            context.startActivity(sendIntent);
            return true;
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            Logger.e("Send SMS", "Message failed to send to " + number);
            return false;
        }
    }
}