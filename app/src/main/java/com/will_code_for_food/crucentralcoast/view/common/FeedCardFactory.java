package com.will_code_for_food.crucentralcoast.view.common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.view.events.EventInfoFragment;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class FeedCardFactory implements CardFragmentFactory<DatabaseObject> {

    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content<DatabaseObject> cardObjects) {
        return new FeedCardAdapter(MainActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity, final Content myDBObjects) {

        return new AdapterView.OnItemClickListener () {

            Object obj;

            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                obj = myDBObjects.get(position);

                if (obj instanceof Event) {
                    eventClicked((Event) obj, currentActivity);
                } else if (obj instanceof Resource) {
                    //do nothing
                } else if (obj instanceof Video) {
                    //do nothing
                }
            }
        } ;
    }

    private void eventClicked(Event selectedEvent, MainActivity currentActivity) {
        EventsActivity.setEvent(selectedEvent);
        currentActivity.loadFragmentById(R.layout.fragment_event, currentActivity.getTitle() + " > " + selectedEvent.getName(), new EventInfoFragment(), currentActivity);
    }
}
