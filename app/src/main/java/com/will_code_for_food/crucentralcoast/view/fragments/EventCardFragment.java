package com.will_code_for_food.crucentralcoast.view.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;

/**
 * Class for fragments that will display lists of Events
 * as cards, pulled from a Retriever object.
 * Created by Gavin on 1/12/2016.
 */
public class EventCardFragment extends Fragment {
    private Retriever retriever;
    protected String imageLabel = "";
    protected String title = "";
    protected String date = "";

    public EventCardFragment() {
        super();
    }

    public void setRetriever(final Retriever retriever) {
        this.retriever = retriever;
    }

    public Retriever getRetriever() {
        return retriever;
    }

    public String getImageLabel() {
        Bundle args = getArguments();
        imageLabel = args.getString("imageLabel", "");
        return imageLabel;
    }

    public String getTitle() {
        Bundle args = getArguments();
        title = args.getString("title", "");
        return title;
    }

    public String getDate() {
        Bundle args = getArguments();
        date = args.getString("date", "");
        return date;
    }

}
