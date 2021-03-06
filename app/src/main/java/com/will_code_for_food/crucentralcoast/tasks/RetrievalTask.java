package com.will_code_for_food.crucentralcoast.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrieval task gets a list of database objects from a receiver and adds the items to a list.
 * The adapter, on click listener, and check for including an item
 * in a list is handled by a CardFactory class.
 * Created by Brian on 1/24/2016.
 */
public class RetrievalTask <T extends DatabaseObject> extends AsyncTask<Integer, Void, Void> {
    private List<T> dbObjects;                // list of all events in database
    private MainActivity currentActivity;     // reference to the activity running this task
    private Content<T> myDBObjects;           // list of relevant ministry objects only
    private Retriever retriever;              // Database retriever
    private CardFragmentFactory cardFactory;  // Factory to create a card fragment from a json object
    private int errorMessageId;
    private AsyncResponse response;
    private int index, top;

    public RetrievalTask(Retriever retriever, CardFragmentFactory cardFactory,
                         int errorMessageId, AsyncResponse response) {
        super();
        this.retriever = retriever;
        this.cardFactory = cardFactory;
        this.errorMessageId = errorMessageId;
        currentActivity = (MainActivity) MainActivity.context;
        this.response = response;
    }

    public RetrievalTask(Retriever retriever, CardFragmentFactory cardFactory,
                         int errorMessageId) {
        this(retriever, cardFactory, errorMessageId, null);
    }

    @Override
    protected Void doInBackground(Integer... params) {
        Content dbContent = retriever.getAll();
        index = params.length == 2 ? params[0] : 0;
        top = params.length == 2 ? params[1] : 0;

        if (dbContent != null) {
            ArrayList<T> filteredObjects = new ArrayList<T>();
            dbObjects = dbContent;

            for (T object : dbObjects) {
                if (cardFactory.include(object)) {
                    filteredObjects.add(object);
                }
            }
            myDBObjects = new Content<>(filteredObjects, dbContent.getType());
        } else {
            Logger.e("Retrieval", "Unable to retrieve any data");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ListView list = (ListView) currentActivity.findViewById(R.id.list_cards);
        if ((myDBObjects != null) && (myDBObjects != null) && (!myDBObjects.isEmpty())) {

            //for some reason, list is null when switching from the main feed to resources or get involved.
            //adding this check stops the app from crashing
            if (list != null) {
                list.setAdapter(cardFactory.createAdapter(myDBObjects));
                list.setOnItemClickListener(cardFactory.createCardListener(currentActivity, myDBObjects));
                list.setSelectionFromTop(index, top);
            }
        } else {
            String errorMessage = Util.getString(errorMessageId);
            Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        if (response != null) {
            response.processFinish(myDBObjects);
        }
    }
}