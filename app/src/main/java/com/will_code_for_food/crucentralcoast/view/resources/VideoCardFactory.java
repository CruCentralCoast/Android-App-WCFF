package com.will_code_for_food.crucentralcoast.view.resources;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.List;

/**
 * Created by Kayla on 2/11/2016.
 */
public class VideoCardFactory implements CardFragmentFactory {

    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new VideoCardAdapter(ResourcesActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final Content myDBObjects) {
        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                Video selectedVideo = (Video) myDBObjects.getObjects().get(position);
                YoutubeViewer.watchYoutubeVideo(selectedVideo.getId(), currentActivity);
            }
        } ;
    }
}