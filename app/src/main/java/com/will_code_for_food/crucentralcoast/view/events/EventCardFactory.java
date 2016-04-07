package com.will_code_for_food.crucentralcoast.view.events;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.Set;

/**
 * Created by Brian on 1/24/2016.
 */
public class EventCardFactory implements CardFragmentFactory<Event> {
    private Set<String> myMinistries;

    public EventCardFactory(){
        // Only display dbObjects for the ministry
        myMinistries = Util.loadStringSet(Android.PREF_MINISTRIES);
    }

    public Set<String> getMyMinistries() {
        return myMinistries;
    }

    @Override
    public boolean include(Event object) {
        JsonElement ministriesObject = object.getField(Database.JSON_KEY_EVENT_MINISTRIES);

        //Go through all ministries for the event and see if the user is subscribed
        for (JsonElement objectMinistry : ministriesObject.getAsJsonArray()) {
            if (myMinistries.contains(objectMinistry.getAsString())) {
                return true;
            }
        }

        //Return false if my ministries are not found
        return false;
    }

    @Override
    public ArrayAdapter createAdapter(Content<Event> cardFragments) {
        return new EventCardAdapter(MainActivity.context,
                android.R.layout.simple_list_item_1, cardFragments);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final Content<Event> myDBObjects)
    {
        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                Event selectedEvent = (Event) myDBObjects.getObjects().get(position);
                EventsActivity.setEvent(selectedEvent);
                currentActivity.loadFragmentById(R.layout.fragment_event,
                        currentActivity.getTitle() + " > " + selectedEvent.getName(), new EventInfoFragment(), currentActivity);
            }
        } ;
    }
}