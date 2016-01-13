package com.will_code_for_food.crucentralcoast.temp;

import com.will_code_for_food.crucentralcoast.CruFragment;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;

/**
 * Parent class for fragments that will display lists of DatabaseObjects
 * as cards, pulled from a Retriever object.
 * Created by Gavin on 1/12/2016.
 */
public class CruCardScreen extends CruFragment {
    private Retriever retriever;

    public CruCardScreen() {
        super();
    }

    public void setRetriever(final Retriever retriever) {
        this.retriever = retriever;
    }

    public Retriever getRetriever() {
        return retriever;
    }
}
