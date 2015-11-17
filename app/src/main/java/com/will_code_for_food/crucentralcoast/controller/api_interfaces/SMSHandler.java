package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;

/**
 * Created by Mallika on 11/17/15.
 * Class to demonstrate SMS messaging capability.
 */
public class SMSHandler {

    public static void sendSMS(Activity currentActivity) {
        final String toPhoneNumber = currentActivity.getString(R.string.phone_number);
        final String smsMessage = currentActivity.getString(R.string.sms_message);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(currentActivity.getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(currentActivity.getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
