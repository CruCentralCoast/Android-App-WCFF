package com.will_code_for_food.crucentralcoast.controller.crash_reports;

import android.net.Uri;
import android.os.Build;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.EmailMessage;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a crash report based on the
 * current state of the app
 */
public class CrashReport {
    public final Date date;
    public final int version;
    public final String version_name;
    public final String model;
    public final String manufacturer;
    public final Exception exception;

    public final String message;

    public static final String CRU_EMAIL = "Will.Code.For.Food.CP@gmail.com";
    public static final String CRASH_SUBJECT = "Error Report";

    /**
     * Creates a crash report based on the current state of the app
     */
    public CrashReport(final Exception exception, final String message) {
        this.exception = exception;
        this.message = message;
        // phone hardware information
        date = Calendar.getInstance().getTime();
        version = Build.VERSION.SDK_INT;
        version_name = Build.VERSION.CODENAME;
        model = Build.MODEL;
        manufacturer = Build.MANUFACTURER;
    }

    public String asText() {
        String text = "";
        if (message != null) {
            text += "USER MESSAGE:\n";
            text += message + "\n\n";
        }
        text += "DATE: " + date.toString() + "\n";
        text += "VERSION: " + version_name + " ( v" + version + " )\n";
        text += "MODEL: " + model + "\n";
        text += "MANUFACTURER: " + manufacturer + "\n";

        text += "\n\nSTACK TRACE:\n\n";
        text += exception.getMessage() + "\n\n";
        for (StackTraceElement ele : exception.getStackTrace()) {
            text += ele.toString() + "\n";
        }
        return text;
    }

    public EmailMessage asMessage() {
        EmailMessage msg = EmailMessage.buildMessage(CRU_EMAIL, CRASH_SUBJECT, message);
        msg.addAttachmentFromText(asText());
        return msg;
    }
}
