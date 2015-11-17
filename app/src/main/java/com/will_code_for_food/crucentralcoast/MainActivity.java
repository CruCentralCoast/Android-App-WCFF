package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.model.common.components.CalendarEvent;
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
}
