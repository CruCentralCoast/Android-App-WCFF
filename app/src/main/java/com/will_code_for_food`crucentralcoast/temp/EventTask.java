package com.will_code_for_food.crucentralcoast.temp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.EventCardAdapter;
import com.will_code_for_food.crucentralcoast.EventsActivity;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.fragments.EventCardFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Loads the list of events asynchronously in a ListView layout
 * Calls a new task to display individual events when an event is selected from list
 */
public class EventTask extends AsyncTask<Void, Void, Void> {

    ArrayList<Event> events;        // list of all events in database
    MainActivity currentActivity;   // reference to the activity running this task
    ListView eventsList;            // used to display events in a list
    ArrayList<EventCardFragment> eventScreens; // list of relevant event fragments only
    ArrayList<Event> myEvents;      // list of relevant ministry events only

    public EventTask() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Retriever retriever = new SingleRetriever<>(RetrieverSchema.EVENT);
        events = (ArrayList<Event>) (List<?>) retriever.getAll();
        eventScreens = new ArrayList<EventCardFragment>();
        myEvents = new ArrayList<Event>();

        // Only display events for the ministry
        Set<String> userMinistries = Util.loadStringSet(Android.PREF_MINISTRIES);
        for (Event event : events) {
            JsonArray eventMinistries = event.getField(Database.JSON_KEY_EVENT_MINISTRIES).getAsJsonArray();
            for (int i = 0; i < eventMinistries.size(); i++) {
                if (userMinistries.contains(eventMinistries.get(i).getAsString())) {
                    EventCardFragment tempCard = new EventCardFragment();
                    Bundle args = new Bundle();
                    args.putString("imageLabel", event.getImage());
                    args.putString("title", event.getName());
                    args.putString("date", getEventDate(event));
                    tempCard.setArguments(args);
                    eventScreens.add(tempCard);
                    myEvents.add(event);
                    break;
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        eventsList = (ListView) currentActivity.findViewById(R.id.list_events);

        if ((eventScreens != null) && (!eventScreens.isEmpty())) {
            eventsList.setAdapter(new EventCardAdapter(MainActivity.context, android.R.layout.simple_list_item_1, eventScreens));

            // Display more information about the event chosen in a new layout
            eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentActivity.loadFragmentById(R.layout.fragment_event, "Events > " + eventScreens.get(position).getTitle());
                    new EventTask2().execute(myEvents.get(position));
                }
            });
        }else {
            String errorMessage = Util.getString(R.string.toast_no_events);
            Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    // Gets the date of the event in reader format
    private String getEventDate(Event event) {
        JsonElement dateStart = event.getField(Database.JSON_KEY_EVENT_STARTDATE);
        JsonElement dateEnd = event.getField(Database.JSON_KEY_EVENT_ENDDATE);
        String eventDate;

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date start = dateFormat.parse(dateStart.getAsString());
            Date end = dateFormat.parse(dateEnd.getAsString());
            eventDate = formatDate(start);
        } catch (ParseException e) {
            // Can't be parsed; just use the default ISO format
            eventDate = dateStart.getAsString();
        }

        return eventDate;
    }

    // Formats the date into the form Jan 15, 7:00AM
    private String formatDate(Date date) {
        String formattedDate = new SimpleDateFormat(Database.EVENT_DATE_FORMAT).format(date);
        return formattedDate;
    }
}