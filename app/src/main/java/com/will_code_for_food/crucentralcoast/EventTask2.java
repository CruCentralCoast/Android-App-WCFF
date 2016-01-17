package com.will_code_for_food.crucentralcoast;

import android.graphics.Point;
import android.os.AsyncTask;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kayla on 1/16/2016.
 */
public class EventTask2 extends AsyncTask<Event, Void, Void> {

    MainActivity currentActivity;   // reference to the activity running this task
    Event event;                    // event being displayed

    public EventTask2() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Event... params) {
        event = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Display the location of the event
        TextView locationLabel = (TextView)currentActivity.findViewById(R.id.text_event_location);
        locationLabel.setText(getEventLocation());

        // Display the time of the event
        TextView dateLabel = (TextView)currentActivity.findViewById(R.id.text_event_date);
        dateLabel.setText(getEventDate());

        // Display the description of the event
        TextView descriptionLabel = (TextView)currentActivity.findViewById(R.id.text_event_description);
        JsonElement description = event.getField("description");
        descriptionLabel.setText(description.getAsString());

        // Display the header image of the event
        ImageView imageView = (ImageView)currentActivity.findViewById(R.id.image_event);
        scaleImage(imageView, 1.0, 1.0/6.0);

        try {
            // Get the url for the image
        } catch (Exception e) {

        }
    }

    // Gets the address of the event in reader format
    private String getEventLocation() {
        JsonObject eventLoc = event.getField("location").getAsJsonObject();
        String street = eventLoc.get("street1").getAsString();
        String suburb = eventLoc.get("suburb").getAsString();
        String state = eventLoc.get("state").getAsString();
        return street + ", " + suburb + " " + state;
    }

    // Gets the date of the event in reader format
    private String getEventDate() {
        JsonElement dateStart = event.getField("startDate");
        JsonElement dateEnd = event.getField("endDate");
        String eventDate = dateStart.getAsString() + " - " + dateEnd.getAsString();

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date start = dateFormat.parse(dateStart.getAsString());
            Date end = dateFormat.parse(dateEnd.getAsString());
            eventDate = formatDate(start) + " - " + formatDate(end);
        } catch (ParseException e) {

        }

        return eventDate;
    }

    // Formats the date into the form Jan 15, 7:00AM
    private String formatDate(Date date) {
        String formattedDate = new SimpleDateFormat("MMM dd, K:mma").format(date);
        return formattedDate;
    }

    // Scales the image to fit the phone's aspect ratio
    private void scaleImage(ImageView imageView, double widthRatio, double heightRatio) {
        // Get the screen size
        Display display = currentActivity.getWindowManager().getDefaultDisplay();
        Point windowSize = new Point();
        display.getSize(windowSize);
        int width_screen = windowSize.x;
        int height_screen = windowSize.y;

        // Scale image based on screen size
        int newWidth = (int)(width_screen * widthRatio);
        int newHeight = (int)(height_screen * heightRatio);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(newWidth, newHeight);
        imageView.setLayoutParams(layoutParams);
    }
}
