package com.will_code_for_food.crucentralcoast.temp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.EventsActivity;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads the list of events asynchronously in a ListView layout
 * Calls a new task to display individual events when an event is selected from list
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
        Retriever retriever = new SingleRetriever<>(RetrieverSchema.EVENT);
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
            String errorMessage = Util.getString(R.string.toast_no_events);
            Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}