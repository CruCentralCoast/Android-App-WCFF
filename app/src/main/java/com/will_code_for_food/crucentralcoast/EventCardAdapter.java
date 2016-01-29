package com.will_code_for_food.crucentralcoast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 1/19/16.
 */
public class EventCardAdapter extends ArrayAdapter<Event> {

    List<Event> cards;

    public EventCardAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        cards = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event current = cards.get(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        String imageLabel = current.getImage();
        View hold = inflater.inflate(R.layout.fragment_event_card, parent, false);
        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(EventsActivity.context).load(imageLabel).fit().into(imageView);
        }
        else {
            System.out.println("Image is this: " + imageLabel);
            imageView.setImageResource(R.drawable.crulogo);
        }
        TextView titleView = (TextView) hold.findViewById(R.id.card_text);
        titleView.setText(current.getName());
        TextView dateView = (TextView) hold.findViewById(R.id.card_date);
        dateView.setText(current.getEventDate());

        return hold;
    }
}
