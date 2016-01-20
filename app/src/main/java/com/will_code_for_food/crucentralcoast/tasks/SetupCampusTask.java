package com.will_code_for_food.crucentralcoast.tasks;

import android.os.AsyncTask;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 1/19/2016.
 */
public class SetupCampusTask extends AsyncTask<Void, Void, Void> {

    ArrayList<Campus> campuses;
    ArrayList<String> campusStrings;

    @Override
    protected Void doInBackground(Void... params) {

        Retriever retriever = new SingleRetriever<>(RetrieverSchema.CAMPUS);
        campuses = (ArrayList<Campus>) (List<?>) retriever.getAll();
        campusStrings = new ArrayList<String>();

        for (Campus campus : campuses) {
            campusStrings.add(campus.getName());
        }

        return null;
    }

    /*
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        ListView campusList = (ListView) ((Activity)SplashscreenActivity.context).findViewById(R.id.setup_campus_list);

        if ((campusStrings != null) && (!campusStrings.isEmpty())) {
            campusList.setAdapter(new ArrayAdapter<>(MainActivity.context, android.R.layout.simple_list_item_1, campusStrings));
        }
        else {
            //do nothing
        }
    }*/
}
