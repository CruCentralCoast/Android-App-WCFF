package com.will_code_for_food.crucentralcoast.controller.crash_reports;

import android.os.Build;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.BuildConfig;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.email.EmailMessage;
import com.will_code_for_food.crucentralcoast.view.common.MyApplication;

import java.util.ArrayList;
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
    public final String app_version;
    public final int version_name;
    public final String model;
    public final String manufacturer;
    public final Throwable exception;
    public final boolean cached;
    public final String message;

    private final String text;

    private static final String CACHED_REPORT = "last-session-cached-crash-report";
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
    public CrashReport(final Throwable exception, final String message) {
        this(exception, message, false);
    }

    private CrashReport(final Throwable exception, final String message, final boolean cached) {
        if (cached) {
            List<String> list = LocalStorageIO.readList(CACHED_REPORT);
            if (list != null) {
                text = convertToString(list);
            } else {
                text = null;
            }
        } else {
            text = null;
        }
        this.exception = exception;
        this.message = message;
        this.app_version = MyApplication.getVersion();
        this.cached = cached;
        // phone hardware information
        date = Calendar.getInstance().getTime();
        version = Build.VERSION.RELEASE;
        version_name = Build.VERSION.SDK_INT;
        model = Build.MODEL;
        manufacturer = Build.MANUFACTURER;
    }

    public List<String> asText() {
        List<String> text = new ArrayList<>();
        if (message != null) {
            text.add("USER MESSAGE:\n");
            text.add(message + "\n\n");
        } else {
            text.add("No user message\n");
        }
        text.add("\nAPP VERSION: " + app_version + "\n\n");
        text.add("DATE: " + date.toString() + "\n");
        text.add("API VERSION: " + version_name + " ( v" + version + " )\n");
        text.add("MODEL: " + model + "\n");
        text.add("MANUFACTURER: " + manufacturer + "\n");

        if (exception != null) {
            text.add("\n\n**************************************************\n");
            text.add("\nSTACK TRACE:\n\n");
            text.add(exception.getMessage() + "\n\n");
            for (StackTraceElement ele : exception.getStackTrace()) {
                text.add(ele.toString() + "\n");
            }
        } else {
            text.add("\n\nNo Exception included\n");
        }

        List<String> sessionLogs = Logger.getSessionLogs();
        if (sessionLogs != null) {
            text.add("\n\n**************************************************\n");
            text.add("\nSESSION LOG:\n\n");
            for (String line : Logger.getSessionLogs()) {
                text.add(line + '\n');
            }
        }
        return text;
    }

    private String convertToString(final List<String> list) {
        String text = "";
        for (String str : list) {
            text += str;
            text += '\n';
        }
        return text;
    }

    public EmailMessage asMessage() {
        EmailMessage msg = EmailMessage.buildMessage(CRU_EMAIL,
                CRASH_SUBJECTS[new Random().nextInt(CRASH_SUBJECTS.length)], message);
        if (cached) {
            msg.addAttachmentFromText(text);
        } else {
            msg.addAttachmentFromText(convertToString(asText()));
        }
        return msg;
    }

    public boolean cache() {
        LocalStorageIO.deleteFile(CACHED_REPORT);
        return LocalStorageIO.writeList(asText(), CACHED_REPORT);
    }

    public static CrashReport loadCachedReport() {
        if (cacheReportExists()) {
            CrashReport report = new CrashReport(null, null, true);
            LocalStorageIO.deleteFile(CACHED_REPORT);
            return report;
        }
        return null;
    }

    public static boolean cacheReportExists() {
        return LocalStorageIO.fileExists(CACHED_REPORT);
    }
}
