package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.model.common.components.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.common.components.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.components.RestUtil;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity {
    Notifier notifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifier = new Notifier();

        Button notifyButton = (Button)findViewById(R.id.button_notify);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.createNotification("title", "text", getApplicationContext());
            }
        });
    }

    public void testCalendar(View view) {
        // building test event
        CalendarEvent event = new CalendarEvent("Leave for CRU Event", "This is a cru event " +
                "that should be added to the users calendar at this exact time.", "PAC Circle",
                Calendar.getInstance());
        CalendarAccessor.addEventToCalendar(event, this);
    }

    public void testCalendarEdit(View view) {
        // building test event
        CalendarEvent event = new CalendarEvent("New Title!", "This is a cru event " +
                "that should be added to the users calendar at this exact time.", "PAC Circle",
                Calendar.getInstance());
        CalendarAccessor.editExistingEvent(event, "Leave for CRU Event", this);
    }

    public void testSMS(View view) {
        //test event for SMS
        SMSHandler.sendSMS(this);
    }

    public void testDB(View view) {
        //Toast.makeText(getApplicationContext(), "first toast", Toast.LENGTH_LONG).show();
        new dbTestTask().execute();
    }

    private class dbTestTask extends AsyncTask<Void, Void, Void> {
        String textResult = "error: myTask";
        String message = "Ministries:";
        ArrayList<Ministry> ministries;

        @Override
        protected Void doInBackground(Void... params) {
            //message += "hello there!";
            ministries = RestUtil.getMinistries();

            for (Ministry ministry : ministries) {
                message += ("\n" + ministry.getName());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    public void testYoutube(View view) {
        YoutubeViewer.watchYoutubeVideo("hGcmaztq7eU", this);
    }
}
