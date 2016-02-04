package com.will_code_for_food.crucentralcoast.view.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.tasks.DisplayEventInfoTask;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by MasonJStevenson on 2/2/2016.
 */
public class EventInfoFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        new DisplayEventInfoTask().execute(EventsActivity.getEvent());

        return fragmentView;
    }
}