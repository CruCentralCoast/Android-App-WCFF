package com.will_code_for_food.crucentralcoast.view.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareActivity;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareEventCardFactory;

/**
 * Created by mallika on 1/19/16.
 */
public class EventsFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);

        Retriever retriever = new SingleRetriever(RetrieverSchema.EVENT);
        CardFragmentFactory factory;

        if (getParent() instanceof RideShareActivity) {
            factory = new RideShareEventCardFactory();
        } else {
            factory = new EventCardFactory();
        }

        new RetrievalTask<Event>(retriever, factory,
                R.id.list_events, R.string.toast_no_events).execute();
        return hold;
    }
}
