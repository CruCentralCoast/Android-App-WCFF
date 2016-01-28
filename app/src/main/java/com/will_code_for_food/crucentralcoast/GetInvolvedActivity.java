package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;

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
}
