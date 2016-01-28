package com.will_code_for_food.crucentralcoast.view.other;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.EventCardAdapter;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.tasks.DisplayEventInfoTask;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.fragments.EventCardFragment;
import com.will_code_for_food.crucentralcoast.view.other.CardFragmentFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Brian on 1/24/2016.
 */
public class EventCardFactory implements CardFragmentFactory<EventCardFragment> {
    private Set<String> myMinistries;

    public EventCardFactory(){
        // Only display dbObjects for the ministry
        myMinistries = Util.loadStringSet(Android.PREF_MINISTRIES);
    }

    @Override
    public EventCardFragment createCardFragment(DatabaseObject object) {
        JsonElement ministriesObject = object.getField(Database.JSON_KEY_EVENT_MINISTRIES);

        //Go through all ministries for the event and see if the user is subscribed
        for (JsonElement objectMinistry : ministriesObject.getAsJsonArray()) {
            if (myMinistries.contains(objectMinistry.getAsString())) {
                Event event = null;
                EventCardFragment card = new EventCardFragment();
                if (object instanceof Event) {
                    event = (Event) object;
                    Bundle args = new Bundle();
                    args.putString("imageLabel", event.getImage());
                    args.putString("title", event.getName());
                    args.putString("date", getEventDate(event));
                    card.setArguments(args);
                }
                return card;
            }
        }

        //Return null if my ministries are not found
        return null;
    }

    @Override
    public ArrayAdapter createAdapter(List<EventCardFragment> cardFragments) {
        return new EventCardAdapter(MainActivity.context,
                android.R.layout.simple_list_item_1, cardFragments);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final List<? extends DatabaseObject> myDBObjects)
    {
        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                Event selectedEvent = (Event) myDBObjects.get(position);
                currentActivity.loadFragmentById(R.layout.fragment_event,
                        currentActivity.getTitle() + " > " + selectedEvent.getName());
                new DisplayEventInfoTask().execute(selectedEvent);
            }
        } ;
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