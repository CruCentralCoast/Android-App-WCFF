package com.will_code_for_food.crucentralcoast.controller.crash_reports;

import android.os.Build;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.BuildConfig;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.email.EmailMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Represents a crash report based on the
 * current state of the app
 */
public class CrashReport {
    public final Date date;
    public final String version;
    public final int version_name;
    public final String model;
    public final String manufacturer;
    public final Exception exception;

    public final String message;

    public static final String CRU_EMAIL = "Will.Code.For.Food.CP@gmail.com";
    public static final String[] CRASH_SUBJECTS = new String[] {
            "Bug Report",
            "Bug  Report",
            "Oops!",
            "Oops!!",
            "Bug Report!",
            "Bug  Report!",
            "Oh no!",
            "Oh  no!",
            "Oh no! :(",
            "Oh no!  :(",
            "I got 99 problems and I just found another",
            "I got 99 problems and I just found another!",
            "Found a new \"feature\"",
            "I Found a new \"feature\"",
            "Whoops!",
            "Whoops!!"
        };

    /**
     * Creates a crash report based on the current state of the app
     */
    public CrashReport(final Exception exception, final String message) {
        this.exception = exception;
        this.message = message;
        // phone hardware information
        date = Calendar.getInstance().getTime();
        version = Build.VERSION.RELEASE;
        version_name = Build.VERSION.SDK_INT;
        model = Build.MODEL;
        manufacturer = Build.MANUFACTURER;
    }

    public String asText() {
        String text = "";
        if (message != null) {
            text += "USER MESSAGE:\n";
            text += message + "\n\n";
        } else {
            text += "No user message\n";
        }
        text += "DATE: " + date.toString() + "\n";
        text += "API VERSION: " + version_name + " ( v" + version + " )\n";
        text += "MODEL: " + model + "\n";
        text += "MANUFACTURER: " + manufacturer + "\n";

        if (exception != null) {
            text += "\n\n**************************************************\n";
            text += "\nSTACK TRACE:\n\n";
            text += exception.getMessage() + "\n\n";
            for (StackTraceElement ele : exception.getStackTrace()) {
                text += ele.toString() + "\n";
            }
        } else {
            text += "\n\nNo Exception included\n";
        }

        List<String> sessionLogs = Logger.getSessionLogs();
        if (sessionLogs != null) {
            text += "\n\n**************************************************\n";
            text += "\nSESSION LOG:\n\n";
            for (String line : Logger.getSessionLogs()) {
                text += line + '\n';
            }
        }
        Log.i("Session Log Retrieved:\n", text);
        return text;
    }

    public EmailMessage asMessage() {
        EmailMessage msg = EmailMessage.buildMessage(CRU_EMAIL,
                CRASH_SUBJECTS[new Random().nextInt(CRASH_SUBJECTS.length)], message);
        msg.addAttachmentFromText(asText());
        return msg;
    }
}
