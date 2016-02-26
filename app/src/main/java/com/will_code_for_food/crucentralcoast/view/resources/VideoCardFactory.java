package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.List;

/**
 * Created by Kayla on 2/11/2016.
 */
public class VideoCardFactory implements CardFragmentFactory {

    private List<Video> cards;

    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new VideoAdapter(ResourcesActivity.context,
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

    private class VideoAdapter extends ArrayAdapter<Video> {

        public VideoAdapter(Context context, int resource, Content content) {
            super(context, resource, content.getObjects());
            cards = content.getObjects();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Video current = cards.get(position);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View hold = inflater.inflate(R.layout.fragment_resources_youtube_card, parent, false);

            // Load card elements with video information
            ImageView thumbnail = (ImageView) hold.findViewById(R.id.card_image);
            Picasso.with(ResourcesActivity.context).load(current.getThumbnailUrl()).fit().into(thumbnail);

            TextView name = (TextView) hold.findViewById(R.id.card_video_name);
            name.setText(current.getTitle());

            TextView date = (TextView) hold.findViewById(R.id.card_video_date);
            date.setText(current.getAgeString());

            return hold;
        }
    }
}
