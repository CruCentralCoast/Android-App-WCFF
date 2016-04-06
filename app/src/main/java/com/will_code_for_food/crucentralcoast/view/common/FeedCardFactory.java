package com.will_code_for_food.crucentralcoast.view.common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.view.events.EventInfoFragment;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.view.resources.ResourcesActivity;
import com.will_code_for_food.crucentralcoast.view.resources.ViewArticleFragment;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class FeedCardFactory implements CardFragmentFactory<DatabaseObject> {

    @Override
    public boolean include(DatabaseObject object) {
        if (object != null && object instanceof Resource) {
            Resource resource = (Resource) object;

            // Not logged in; check if article restricted
            if (!Authenticator.isUserLoggedIn() && !Authenticator.logIn())
                return !resource.isRestricted();
        }
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
                    resourceClicked((Resource) obj, currentActivity);
                } else if (obj instanceof Video) {
                    videoClicked((Video) obj, currentActivity);
                }
            }
        } ;
    }

    private void eventClicked(Event selectedEvent, MainActivity currentActivity) {
        EventsActivity.setEvent(selectedEvent);
        currentActivity.loadFragmentById(R.layout.fragment_event, currentActivity.getTitle() + " > " + selectedEvent.getName(), new EventInfoFragment(), currentActivity);
    }

    private void resourceClicked(Resource selectedResource, MainActivity currentActivity) {
        ResourcesActivity.selectedResource = selectedResource;
        currentActivity.loadFragmentById(R.layout.fragment_article, currentActivity.getTitle() + " > " + ResourcesActivity.selectedResource.getTitle(), new ViewArticleFragment(), currentActivity);
    }

    private void videoClicked(Video selectedVideo, MainActivity currentActivity) {
        YoutubeViewer.watchYoutubeVideo(selectedVideo.getId(), currentActivity);
    }
}
