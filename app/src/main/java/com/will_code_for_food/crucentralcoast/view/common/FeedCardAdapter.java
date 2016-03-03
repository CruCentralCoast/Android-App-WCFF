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
        View view = null;

        if (current instanceof Event) {
            view =  getEventView((Event) current, parent);
        }else if (current instanceof Resource) {
            view =  getArticleView((Resource) current, parent);
        }else if (current instanceof Video) {
            view =  getVideoView((Video) current, parent);
        } else {
            Log.e("FeedCardAdapter", "Expected valid db object. Got: " + current.getClass().toString());
        }

        return view;
    }

    private View getVideoView(Video current, ViewGroup parent) {
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

    private View getArticleView(Resource current, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        String imageLabel = current.getImage();
        View hold = inflater.inflate(R.layout.fragment_resources_card, parent, false);

        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(SummerMissionsActivity.context).load(imageLabel).fit().into(imageView);
        } else {
            System.out.println("Image is this: " + imageLabel);
            imageView.setImageResource(R.drawable.crulogo);
        }

        ImageView typeView = (ImageView) hold.findViewById(R.id.resource_type);
        String type = current.getType();
        if (type.equals(Database.RESOURCE_ARTICLE)) {
            typeView.setImageResource(R.drawable.ic_web_black_36dp);
        } else if (type.equals(Database.RESOURCE_VIDEO)) {
            typeView.setImageResource(R.drawable.ic_video);
        } else if (type.equals(Database.RESOURCE_AUDIO)) {
            typeView.setImageResource(R.drawable.ic_volume_up_black_36dp);
        }

        TextView titleView = (TextView) hold.findViewById(R.id.card_text);
        titleView.setText(current.getTitle());

        return hold;
    }

    private View getEventView(Event current, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        String imageLabel = current.getImage();
        View hold = inflater.inflate(R.layout.fragment_event_card, parent, false);
        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(EventsActivity.context).load(imageLabel).fit().into(imageView);
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
        return hold;
    }
}