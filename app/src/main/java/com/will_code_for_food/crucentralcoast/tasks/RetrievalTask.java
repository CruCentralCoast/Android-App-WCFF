package com.will_code_for_food.crucentralcoast.tasks;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.EventsActivity;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.view.other.CardFragmentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 1/24/2016.
 */
public class RetrievalTask <T extends DatabaseObject> extends AsyncTask<Void, Void, Void>{
    private List<T> dbObjects;         // list of all events in database
    private MainActivity currentActivity;           // reference to the activity running this task
    private List<T> myDBObjects;       // list of relevant ministry objects only
    private Retriever retriever;                    //Database retriever
    private CardFragmentFactory cardFactory;        //Factory to create a card fragment from a json object
    private AsyncTask<T, ?, ?> onClickTask;
    private int errorMessageId;
    private int listId;

    public RetrievalTask(Retriever retriever, CardFragmentFactory cardFactory, AsyncTask onClickTask,
                         int listId, int errorMessageId) {
        super();
        this.retriever = retriever;
        this.cardFactory = cardFactory;
        this.errorMessageId = errorMessageId;
        this.onClickTask = onClickTask;
        this.listId = listId;
        currentActivity = (MainActivity) MainActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        dbObjects = retriever.getAll();
        myDBObjects = new ArrayList<T>();


        for (T object : dbObjects) {
            if (cardFactory.include(object)) {
                myDBObjects.add(object);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ListView list = (ListView) currentActivity.findViewById(listId);
        if ((myDBObjects != null) && (!myDBObjects.isEmpty())) {
            list.setAdapter(cardFactory.createAdapter(myDBObjects));
            list.setOnItemClickListener(cardFactory.createCardListener(currentActivity, myDBObjects));
        }else {
            String errorMessage = Util.getString(errorMessageId);
            Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}