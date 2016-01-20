package com.will_code_for_food.crucentralcoast.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.temp.EventTask;

/**
 * Created by mallika on 1/19/16.
 */
public class EventsFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        new EventTask().execute();
        return hold;
    }
}
