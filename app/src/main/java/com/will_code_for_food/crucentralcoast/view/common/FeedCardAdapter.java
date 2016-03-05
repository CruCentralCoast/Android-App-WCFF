package com.will_code_for_food.crucentralcoast.view.common;

import android.content.Context;
import android.util.Log;
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
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.DatabaseObjectSorter;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.SortMethod;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.events.EventInfoFragment;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.view.resources.ResourcesActivity;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

import java.util.List;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class FeedCardAdapter extends CardAdapter {

    public FeedCardAdapter(Context context, int resource, Content<DatabaseObject> content) {
        super(context, resource, content);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatabaseObject current = cards.get(position);

        if (current instanceof Event) {

            //inflate
            if (convertView == null || !checkTag(current, convertView)) {
                convertView = inflateEventView((Event) current, parent);
            }

            //populate
            populateEventView((Event) current, convertView);

        } else if (current instanceof Resource) {
            //inflate
            if (convertView == null || !checkTag(current, convertView)) {
                convertView = inflateArticleView((Resource) current, parent);
            }

            //populate
            populateArticleView((Resource) current, convertView);
        } else if (current instanceof Video) {
            //inflate
            if (convertView == null || !checkTag(current, convertView)) {
                convertView = inflateVideoView((Video) current, parent);
            }

            //populate
            populateVideoView((Video) current, convertView);
        } else {
            Log.e("FeedCardAdapter", "Expected valid db object. Got: " + current.getClass().toString());
        }

        return convertView;
    }

    private boolean checkTag (DatabaseObject current, View convertView){
        if (current instanceof Event) {
            return ((String) convertView.getTag()).equals("event");
        } else if (current instanceof Video) {
            return ((String) convertView.getTag()).equals("video");
        } else if (current instanceof Resource) {
            return ((String) convertView.getTag()).equals("resource");
        } else {
            return false;
        }
    }

    private View inflateVideoView(Video current, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View hold = inflater.inflate(R.layout.fragment_resources_youtube_card, parent, false);
        hold.setTag("video");

        return hold;
    }

    private void populateVideoView(Video current, View hold) {

        // Load card elements with video information
        ImageView thumbnail = (ImageView) hold.findViewById(R.id.card_image);
        Picasso.with(getContext()).load(current.getThumbnailUrl()).fit().into(thumbnail);

        TextView name = (TextView) hold.findViewById(R.id.card_video_name);
        name.setText(current.getTitle());

        TextView date = (TextView) hold.findViewById(R.id.card_video_date);
        date.setText(current.getAgeString());
    }

    private View inflateArticleView(Resource current, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View hold = inflater.inflate(R.layout.fragment_resources_card, parent, false);

        hold.setTag("resource");
        return hold;
    }

    private void populateArticleView(Resource current, View hold) {
        String imageLabel = current.getImage();
        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(getContext()).load(imageLabel).fit().into(imageView);
        } else {
            System.out.println("Image is this: " + imageLabel);
            imageView.setImageResource(R.drawable.crulogo);
        }

        ImageView typeView = (ImageView) hold.findViewById(R.id.resource_type);
        String type = current.getType();
        if (type.equals(Database.RESOURCE_ARTICLE)) {
            typeView.setImageResource(R.drawable.ic_web_grey_36dp);
        } else if (type.equals(Database.RESOURCE_VIDEO)) {
            typeView.setImageResource(R.drawable.ic_video_grey);
        } else if (type.equals(Database.RESOURCE_AUDIO)) {
            typeView.setImageResource(R.drawable.ic_volume_up_grey_36dp);
        }

        TextView titleView = (TextView) hold.findViewById(R.id.card_text);
        titleView.setText(current.getTitle());
    }

    private View inflateEventView(Event current, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View hold = inflater.inflate(R.layout.fragment_event_card, parent, false);

        hold.setTag("event");
        return hold;
    }

    private void populateEventView(Event current, View hold) {
        String imageLabel = current.getImage();
        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(getContext()).load(imageLabel).fit().into(imageView);
        } else {
            System.out.println("Image is this: " + imageLabel);
            imageView.setImageResource(R.drawable.crulogo);
        }
        TextView titleView = (TextView) hold.findViewById(R.id.card_text);
        titleView.setText(current.getName());
        TextView dateView = (TextView) hold.findViewById(R.id.card_date);
        dateView.setText(current.getEventDate());
        ImageView carView = (ImageView) hold.findViewById(R.id.card_car_image);

        if (!current.hasRideSharing()) {
            carView.setVisibility(View.INVISIBLE);
        }
    }
}