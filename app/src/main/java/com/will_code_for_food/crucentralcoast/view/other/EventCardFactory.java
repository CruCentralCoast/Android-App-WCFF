package com.will_code_for_food.crucentralcoast.view.other;

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

import java.util.List;
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

    @Override
    public boolean include(DatabaseObject object) {
        JsonElement ministriesObject = object.getField(Database.JSON_KEY_EVENT_MINISTRIES);

        //Go through all ministries for the event and see if the user is subscribed
        for (JsonElement objectMinistry : ministriesObject.getAsJsonArray()) {
            if (myMinistries.contains(objectMinistry.getAsString())) {
                Event event = null;
                if (object instanceof Event) {
                    return true;
                }
            }
        }

        //Return false if my ministries are not found
        return false;
    }

    @Override
    public ArrayAdapter createAdapter(List<Event> cardFragments) {
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
}