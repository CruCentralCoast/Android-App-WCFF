package com.will_code_for_food.crucentralcoast;

/**
 * Created by MasonJStevenson on 1/11/2016.
 */

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.model.common.common.Campus;

import java.util.ArrayList;

/**
 * This class is an example of how to asynchronously retrieve all campus objects.
 * Currently we aren't storing them anywhere.
 */
public class CampusExampleTask2 extends AsyncTask<Void, Void, Void> {

    ArrayList<Campus> campuses;
    ListView campusesList;
    ArrayList<String> campusesStrings; //required for listview
    MainActivity currentActivity;

    public CampusExampleTask2() {
        currentActivity = (MainActivity) MainActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        campuses = Campus.getCampuses();
        campusesStrings = new ArrayList<String>();

        for (Campus campus : campuses) {
            campusesStrings.add(campus.getName());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        campusesList = (ListView) currentActivity.findViewById(R.id.campusesList);
        String campusID = "All";

        if ((campusesStrings != null) && (!campusesStrings.isEmpty())) {
            campusesList.setAdapter(new ArrayAdapter<>(MainActivity.context, android.R.layout.simple_list_item_1, campusesStrings));

            campusesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentActivity.loadFragmentById(R.layout.fragment_ministries, "Ministries");
                    new MinistryExampleTask2(campuses.get(position).getId()).execute();
                }
            });
        }

        else {
            Toast.makeText(currentActivity.getApplicationContext(), "Unable to access campuses", Toast.LENGTH_LONG).show();
        }
    }
}
