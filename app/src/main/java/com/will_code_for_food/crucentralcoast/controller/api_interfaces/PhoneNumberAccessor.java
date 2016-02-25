package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

/**
 * Created by Gavin on 2/24/2016.
 */
public class PhoneNumberAccessor {

    /**
     * Gets the user's phone number from the phone if possible, from local
     * storage if it can't get it from there, and returns null if it can't
     * be found anywhere.
     */
    public static String getUserPhoneNumber(final Context ctxt) {
        String number;
        try {
            TelephonyManager tMgr = (TelephonyManager) ctxt.getSystemService(Context.TELEPHONY_SERVICE);
            number = tMgr.getLine1Number();
            if (number == null) {
                number = getNumberFromFile();
            }
        } catch (Exception ex) {
            number = getNumberFromFile();
        }
        return number;
    }

    private static String getNumberFromFile() {
        return LocalStorageIO.readSingleLine(LocalFiles.USER_NUMBER);
    }

    public static boolean savePhoneNumberToFile(final String number) {
        return LocalStorageIO.writeSingleLineFile(LocalFiles.USER_NUMBER, number);
    }
}
