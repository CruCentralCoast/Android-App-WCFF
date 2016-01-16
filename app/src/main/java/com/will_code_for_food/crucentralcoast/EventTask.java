package com.will_code_for_food.crucentralcoast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.retrieval.EventRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 1/14/2016.
 */
public class EventTask extends AsyncTask<Integer, Void, Void> {

    ArrayList<Event> events;
    MainActivity currentActivity;
    int eventID;

    public EventTask() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        EventRetriever retriever = new EventRetriever();
        events = (ArrayList<Event>) (List<?>) retriever.getAll();
        eventID = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Event event = events.get(eventID);
        ImageView imageView = (ImageView)currentActivity.findViewById(R.id.image_event);
        TextView locationLabel = (TextView)currentActivity.findViewById(R.id.text_event_location);
        TextView dateLabel = (TextView)currentActivity.findViewById(R.id.text_event_date);
        Button button = (Button)currentActivity.findViewById(R.id.button_notify);

        // Get the location of the event
        JsonObject eventLoc = event.getField("location").getAsJsonObject();
        String street = eventLoc.get("street1").getAsString();
        String suburb = eventLoc.get("suburb").getAsString();
        String state = eventLoc.get("state").getAsString();
        String postcode = eventLoc.get("postcode").getAsString();
        locationLabel.setText(street + " " + suburb + " " + state + " " + postcode);

        // Get the date of the event
        JsonElement dateStart = event.getField("startDate");
        dateLabel.setText(dateStart.getAsString());

        // Get the URL for the image
        try {
            // LOAD URL

        } catch (Exception e) {
            button.setText(event.getImage());
            e.printStackTrace();
        }
    }
}
