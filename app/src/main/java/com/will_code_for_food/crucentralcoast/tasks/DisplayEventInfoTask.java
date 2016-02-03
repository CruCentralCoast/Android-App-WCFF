package com.will_code_for_food.crucentralcoast.tasks;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.EventsActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.resources.TypeFaceUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.UI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Displays information about the event
 */
public class DisplayEventInfoTask extends AsyncTask<Event, Void, Void> {

    EventsActivity currentActivity; // reference to the activity running this task
    Event event;                    // event being displayed

    public DisplayEventInfoTask() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Event... params) {
        event = params[0];
        //EventsActivity.setEvent(event);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Display the header image of the event
        ImageView imageView = (ImageView)currentActivity.findViewById(R.id.image_event);
        Point scaledSize = Util.scaledImageSize(currentActivity, UI.IMAGE_HEADER_LENGTH_RATIO, UI.IMAGE_HEADER_HEIGHT_RATIO);
        if (event.getImage() != null && event.getImage() != "") {
            Picasso.with(currentActivity).load(event.getImage()).resize(scaledSize.x, scaledSize.y)
                    .onlyScaleDown().placeholder(R.drawable.transparent).into(imageView);
        }
        else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scaledSize.x, scaledSize.y);
            imageView.setLayoutParams(layoutParams);
        }
        System.out.println("Find assets: " + currentActivity.getApplication().getAssets());

        // Display the location of the event
        TextView locationLabel = (TextView)currentActivity.findViewById(R.id.text_event_location);
        locationLabel.setText(event.getEventLocation());

        // Grey out button to note no chosen location
        if (!event.hasLocation()) {
            ImageButton mapButton = (ImageButton)currentActivity.findViewById(R.id.button_map);
            mapButton.setImageResource(R.drawable.map_no);
        }

        // Display the time of the event
        TextView dateLabel = (TextView)currentActivity.findViewById(R.id.text_event_date);
        dateLabel.setText(event.getEventFullDate());

        // Display the description of the event
        TextView descriptionLabel = (TextView)currentActivity.findViewById(R.id.text_event_description);
        descriptionLabel.setText(event.getDescription());

        // Grey out FB button if invalid url link
        if (!event.hasFacebook()) {
            ImageButton fbButton = (ImageButton)currentActivity.findViewById(R.id.button_facebook);
            fbButton.setImageResource(R.drawable.facebook_no);
        }

        // Color RideShare button if enabled
        if (event.hasRideSharing()) {
            ImageButton rideButton = (ImageButton)currentActivity.findViewById(R.id.button_rideshare);
            rideButton.setImageResource(R.drawable.car_yes);
        }

        currentActivity.modifyAddToCalendarButton();
    }
}