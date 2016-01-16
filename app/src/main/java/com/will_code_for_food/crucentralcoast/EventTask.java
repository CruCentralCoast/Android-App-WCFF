package com.will_code_for_food.crucentralcoast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageView;

import com.will_code_for_food.crucentralcoast.controller.retrieval.CampusRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.EventRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 1/14/2016.
 */
public class EventTask extends AsyncTask<Void, Void, Void> {

    ArrayList<Event> events;
    MainActivity currentActivity;

    public EventTask() {
        currentActivity = (EventsActivity) EventsActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        EventRetriever retriever = new EventRetriever();
        events = (ArrayList<Event>) (List<?>) retriever.getAll();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Event event = events.get(0);
        ImageView imageView = (ImageView)currentActivity.findViewById(R.id.image_event);
        Button button = (Button)currentActivity.findViewById(R.id.button_notify);

        // Get the URL for the image
        try {
            URL imgurl = new URL(event.getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(imgurl.openConnection().getInputStream());
            imageView.setImageBitmap(bitmap);
            button.setText(imgurl.toString());
        } catch (Exception e) {
            if (event.getImage() == null)
                button.setText("null");
            else
                button.setText("empty");
            throw new Error("Invalid URL for an image");
        }
    }
}
