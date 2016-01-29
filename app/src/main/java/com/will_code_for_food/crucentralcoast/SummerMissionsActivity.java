package com.will_code_for_food.crucentralcoast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.SummerMission;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.UI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mallika on 1/14/16.
 */
public class SummerMissionsActivity extends MainActivity {

    public MainActivity context;
    private static SummerMission mission = null;

    public static void setMission(final SummerMission newMission) {
        mission = newMission;
    }

    public static SummerMission getMission() {
        return mission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        loadFragmentById(R.layout.fragment_mission, "Missions");
        new MissionTEST().execute();
    }

    // Opens the mission's website application in browser
    public void applyToMission(View view) {
        if (mission != null) {
            String url = mission.getWebsiteUrl();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }

    private class MissionTEST extends AsyncTask<Void, Void, Void> {

        SummerMission testMission;

        @Override
        protected Void doInBackground(Void... params) {

            Retriever retriever = new SingleRetriever<>(RetrieverSchema.SUMMERMISSION);
            List<DatabaseObject> dbObjects = retriever.getAll();
            testMission = (SummerMission) dbObjects.get(0);
            SummerMissionsActivity.setMission(testMission);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            setTitle("Missions > " + testMission.getDestination());
            ImageView header = (ImageView) findViewById(R.id.image_mission);
            Point scaledSize = Util.scaledImageSize(context, UI.IMAGE_HEADER_LENGTH_RATIO, UI.IMAGE_HEADER_HEIGHT_RATIO);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scaledSize.x, scaledSize.y);
            header.setLayoutParams(layoutParams);

            // Setup UI elements
            if (testMission.getImage() != null && testMission.getImage() != "") {
                Picasso.with(context).load(testMission.getImage()).resize(scaledSize.x, scaledSize.y)
                        .onlyScaleDown().placeholder(R.drawable.transparent).into(header);
            }

            TextView date = (TextView) findViewById(R.id.text_mission_date);
            JsonElement dateStart = testMission.getField(Database.JSON_KEY_EVENT_STARTDATE);
            JsonElement dateEnd = testMission.getField(Database.JSON_KEY_EVENT_ENDDATE);
            String eventDate;
            try {
                DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
                SimpleDateFormat newFormat = new SimpleDateFormat(Database.MISSION_DATE_FORMAT);
                Date start = dateFormat.parse(dateStart.getAsString());
                Date end = dateFormat.parse(dateEnd.getAsString());
                eventDate = newFormat.format(start) + " - " + newFormat.format(end);
            } catch (ParseException e) {
                // Can't be parsed; just use the default ISO format
                eventDate = dateStart.getAsString() + " - " + dateEnd.getAsString();
            }
            date.setText(eventDate);

            TextView cost = (TextView) findViewById(R.id.text_mission_cost);
            cost.setText("Cost: $" + testMission.getCost());

            TextView description = (TextView) findViewById(R.id.text_mission_description);
            description.setText(testMission.getDescription());
        }
    }
}