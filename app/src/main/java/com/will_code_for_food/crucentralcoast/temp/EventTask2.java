package com.will_code_for_food.crucentralcoast.temp;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.Display;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.EventsActivity;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.resources.TypeFaceUtil;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Displays information about the event
 */
public class EventTask2 extends AsyncTask<Event, Void, Void> {

    EventsActivity currentActivity;   // reference to the activity running this task
    Event event;                    // event being displayed

    public EventTask2() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Event... params) {
        event = params[0];
        EventsActivity.setEvent(event);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Display the header image of the event
        ImageView imageView = (ImageView)currentActivity.findViewById(R.id.image_event);
        scaleImage(imageView, 1.0, 1.0 / 6.0);
        if (event.getImage() != null && event.getImage() != "") {
            Picasso.with(currentActivity).load(event.getImage()).into(imageView);
        }

        System.out.println("Find assets: " + currentActivity.getApplication().getAssets());

        //Typeface freigSanProLiglt = Typeface.createFromFile("app/src/main/assets/FreigSanProBooklt.otf");

        // Display the location of the event
        TextView locationLabel = (TextView)currentActivity.findViewById(R.id.text_event_location);
        locationLabel.setText(getEventLocation());
        //locationLabel.setTypeface(freigSanProLiglt);

        // Display the time of the event
        TextView dateLabel = (TextView)currentActivity.findViewById(R.id.text_event_date);
        dateLabel.setText(getEventDate());
        //dateLabel.setTypeface(freigSanProLiglt);

        // Display the description of the event
        TextView descriptionLabel = (TextView)currentActivity.findViewById(R.id.text_event_description);
        JsonElement description = event.getField(Database.JSON_KEY_COMMON_DESCRIPTION);
        descriptionLabel.setText(description.getAsString());
        //descriptionLabel.setTypeface(freigSanProLiglt);

        // Set the Facebook link to be retrieved later in EventsActivity
        ImageButton fbButton = (ImageButton)currentActivity.findViewById(R.id.button_facebook);
        String url = event.getField(Database.JSON_KEY_COMMON_URL).getAsString();
        fbButton.setContentDescription(url);
        // Grey out button to note invalid url link
        if (url == null || url == "") {
            fbButton.setImageResource(R.drawable.facebook_no);
        }

        // TODO: Display the parent ministries of the event

        currentActivity.modifyAddToCalendarButton();
    }

    // Gets the address of the event in reader format
    private String getEventLocation() {
        JsonObject eventLoc = event.getField(Database.JSON_KEY_COMMON_LOCATION).getAsJsonObject();
        String street = eventLoc.get(Database.JSON_KEY_COMMON_LOCATION_STREET).getAsString();
        String suburb = eventLoc.get(Database.JSON_KEY_COMMON_LOCATION_SUBURB).getAsString();
        String state = eventLoc.get(Database.JSON_KEY_COMMON_LOCATION_STATE).getAsString();
        ImageButton mapButton = (ImageButton)currentActivity.findViewById(R.id.button_map);

        // Grey out button to note no chosen location
        if (street.equals(Database.EVENT_BAD_LOCATION) || suburb.equals(Database.EVENT_BAD_LOCATION)) {
            mapButton.setImageResource(R.drawable.map_no);
        }
        mapButton.setContentDescription(street);

        return street + ", " + suburb + " " + state;
    }

    // Gets the date of the event in reader format
    private String getEventDate() {
        JsonElement dateStart = event.getField(Database.JSON_KEY_EVENT_STARTDATE);
        JsonElement dateEnd = event.getField(Database.JSON_KEY_EVENT_ENDDATE);
        String eventDate;

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date start = dateFormat.parse(dateStart.getAsString());
            Date end = dateFormat.parse(dateEnd.getAsString());
            eventDate = formatDate(start) + " - " + formatDate(end);
        } catch (ParseException e) {
            // Can't be parsed; just use the default ISO format
            eventDate = dateStart.getAsString() + " - " + dateEnd.getAsString();
        }

        return eventDate;
    }

    // Formats the date into the form Jan 15, 7:00AM
    private String formatDate(Date date) {
        String formattedDate = new SimpleDateFormat(Database.EVENT_DATE_FORMAT).format(date);
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