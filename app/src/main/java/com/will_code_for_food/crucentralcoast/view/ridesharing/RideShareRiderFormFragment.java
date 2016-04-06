package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RiderForm;

/**
 * Created by ShelliCrispen on 2/16/16.
 *
 */
public class RideShareRiderFormFragment extends RideShareFormFragment {
    private RiderForm form;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        return fragmentView;
    }

    @Override
    public Form getForm(String eventId) {
        form = new RiderForm(eventId);
        return form;
    }

    @Override
    public void answerAdditionalQuestions() {
        //no additional questions to answer here
    }

    @Override
    public void submitFormAdditionalActions() {
    }
}
