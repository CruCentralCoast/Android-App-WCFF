package com.will_code_for_food.crucentralcoast.temp;

/**
 * Created by MasonJStevenson on 1/11/2016.
 */

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.controller.retrieval.MinistryRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an example of how to asynchronously retrieve all ministry objects.
 * Currently we aren't storing them anywhere.
 */
public class MinistryExampleTask2 extends AsyncTask<Void, Void, Void> {
    ArrayList<Ministry> ministries;
    ListView ministriesList;
    ArrayList<String> minstriesStrings;
    String campusId;
    MainActivity currentActivity;

    public MinistryExampleTask2(String newCampusId) {
        campusId = newCampusId;
        currentActivity = (MainActivity) MainActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        MinistryRetriever retriever = new MinistryRetriever();
        //ministries = Ministry.getMinistries(); //old way
        ministries = (ArrayList<Ministry>)(List<?>) retriever.getAll();

        minstriesStrings = new ArrayList<String>();

        for (Ministry ministry : ministries) {
            if (ministry.getCampuses().contains(campusId)) {
                minstriesStrings.add(ministry.getName());
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        ministriesList = (ListView) currentActivity.findViewById(R.id.ministries_list);

        if ((minstriesStrings != null) && (!minstriesStrings.isEmpty())) {
            ministriesList.setAdapter(new ArrayAdapter<>(MainActivity.context, android.R.layout.simple_list_item_1, minstriesStrings));
        }

        else {
            Toast.makeText(MainActivity.context.getApplicationContext(), "Unable to access ministries", Toast.LENGTH_LONG).show();
        }
    }
}