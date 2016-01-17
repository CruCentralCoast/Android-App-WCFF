package com.will_code_for_food.crucentralcoast;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.retrieval.EventRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.temp.MinistryExampleTask2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 1/14/2016.
 */
public class EventTask extends AsyncTask<Void, Void, Void> {

    ArrayList<Event> events;        // list of all events in database
    ArrayList<String> eventStrings; // list of event names
    MainActivity currentActivity;   // reference to the activity running this task
    ListView eventsList;            // used to display events in a list

    public EventTask() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        EventRetriever retriever = new EventRetriever();
        events = (ArrayList<Event>) (List<?>) retriever.getAll();
        eventStrings = new ArrayList<String>();

        for (Event event : events) {
            eventStrings.add(event.getName());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        eventsList = (ListView) currentActivity.findViewById(R.id.list_events);

        if ((eventStrings != null) && (!eventStrings.isEmpty())) {
            eventsList.setAdapter(new ArrayAdapter<>(MainActivity.context, android.R.layout.simple_list_item_1, eventStrings));

            // Display more information about the event chosen in a new layout
            eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentActivity.loadFragmentById(R.layout.fragment_event, "Events > " + eventStrings.get(position));
                    new EventTask2().execute(events.get(position));
                }
            });
        }
        else {
            Toast.makeText(currentActivity.getApplicationContext(), "Unable to access events", Toast.LENGTH_LONG).show();
        }
    }
}
