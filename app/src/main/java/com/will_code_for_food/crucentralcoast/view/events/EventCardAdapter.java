package com.will_code_for_food.crucentralcoast.view.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 1/19/16.
 */
public class EventCardAdapter extends ArrayAdapter<Event> {

    List<Event> cards;
    List<Boolean> ridesharingActives = new ArrayList<Boolean>();

    public EventCardAdapter(Context context, int resource, Content<Event> content) {
        super(context, resource, content.getObjects());
        cards = content.getObjects();

        for (Event card : cards) {
            if (card.hasRideSharing()) {
                ridesharingActives.add(true);
            }
            else {
                ridesharingActives.add(false);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event current = cards.get(position);
        String imageLabel = current.getImage();

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_event_card, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(EventsActivity.context).load(imageLabel).fit().into(imageView);
        } else {
            System.out.println("Image is this: " + imageLabel);
            imageView.setImageResource(R.drawable.crulogo);
        }
        TextView titleView = (TextView) convertView.findViewById(R.id.card_text);
        titleView.setText(current.getName());
        TextView dateView = (TextView) convertView.findViewById(R.id.card_date);
        dateView.setText(current.getEventDate());
        ImageView carView = (ImageView) convertView.findViewById(R.id.card_car_image);

        if (!ridesharingActives.get(position)) {
            carView.setVisibility(View.INVISIBLE);
        }
        else {
            carView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
