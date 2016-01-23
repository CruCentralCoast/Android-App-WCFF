package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.tasks.CampusExampleTask2;

/**
 * Created by mallika on 1/14/16.
 */
public class GetInvolvedActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_get_involved, "Get Involved");
    }


    public void testSMS(View view) {
        //test event for SMS
        SMSHandler.sendSMS(this);
    }

    public void testDB(View view) {
        //Toast.makeText(getApplicationContext(), "first toast", Toast.LENGTH_LONG).show();
        loadFragmentById(R.layout.fragment_campuses, "Select a Campus");
        new CampusExampleTask2().execute();
    }


}
