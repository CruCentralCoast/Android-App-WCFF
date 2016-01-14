package com.will_code_for_food.crucentralcoast.temp;

import com.will_code_for_food.crucentralcoast.CruFragment;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever2;

/**
 * Parent class for fragments that will display lists of DatabaseObjects
 * as cards, pulled from a Retriever object.
 * Created by Gavin on 1/12/2016.
 */
public class CruCardScreen extends CruFragment {
    private Retriever2 retriever;

    public CruCardScreen() {
        super();
    }

    public void setRetriever(final Retriever2 retriever) {
        this.retriever = retriever;
    }

    public Retriever2 getRetriever() {
        return retriever;
    }
}
