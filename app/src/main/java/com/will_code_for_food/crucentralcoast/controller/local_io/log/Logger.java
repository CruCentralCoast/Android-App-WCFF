package com.will_code_for_food.crucentralcoast.controller.local_io.log;

import android.app.Activity;
import android.content.Context;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Standardizes logging output and offers persistent storage of log files. Needs to be created when
 * the app first starts. Has methods that should allow the UI to grab the output easily, if we want
 * to display it in the app.
 */
public final class Logger {
    private static final String fileName = Util.getString(R.string.log_file);
    private final DateFormat dateFormat;
    private List<String> output;

    public Logger() {
        output = new ArrayList<String>();
        dateFormat = new SimpleDateFormat(Util.getString(R.string.log_date_format));
    }

    public List<String> getOutput() {
        return output;
    }

    public List<String> getFilteredOutput(final boolean showErrors, final boolean showWarnings,
                                          final boolean showNormalMessages) {
        List<String> filtered = new ArrayList<String>();
        for (String entry : output) {
            if (entry.contains(Util.getString(R.string.log_error)) && showErrors) {
                filtered.add(entry);
            } else if (entry.contains(Util.getString(R.string.log_warning)) && showWarnings) {
                filtered.add(entry);
            } else if (showNormalMessages) {
                filtered.add(entry);
            }
        }
        return filtered;
    }

    public boolean isError(final String msg) {
        return msg.contains(Util.getString(R.string.log_error));
    }

    public boolean isWarning(final String msg) {
        return msg.contains(Util.getString(R.string.log_warning));
    }

    public void logMessage(final String message) {
        appendLogEntry(getTimeString() + "|" + message);
    }

    public void logWarning(final String warning) {
        logMessage(Util.getString(R.string.log_warning) + warning);
    }

    public void logError(final String error) {
        logMessage(Util.getString(R.string.log_error) + error);
    }

    public void updateOutput() {
        output = new ArrayList<String>();
        File file = MainActivity.context.getFileStreamPath(fileName);
        InputStream stream = null;
        try {
            stream = MainActivity.context.openFileInput(file.getName());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            try {
                while ((line = reader.readLine()) != null)
                    output.add(line);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getTimeString() {
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    private void appendLogEntry(final String entry) {
        FileOutputStream writer = getOutputWriter();
        try {
            writer.write((entry + '\n').getBytes());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private FileOutputStream getOutputWriter() {
        File file = MainActivity.context.getFileStreamPath(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        FileOutputStream out = null;
        try {
            out = MainActivity.context.openFileOutput(file.getName(), Context.MODE_APPEND);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return out;
    }
}
