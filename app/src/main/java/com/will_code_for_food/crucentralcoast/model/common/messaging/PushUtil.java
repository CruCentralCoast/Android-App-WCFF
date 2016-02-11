package com.will_code_for_food.crucentralcoast.model.common.messaging;

import android.os.AsyncTask;
import android.preference.MultiSelectListPreference;

import com.parse.ParsePush;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Util to subscribe to push channels. This currently uses parse as the implementation.
 * Created by Brian on 1/20/2016.
 */
public class PushUtil {

    /**
     * Subscribe to a ministry's push channel
     * @param ministryId
     */
    public static void subscribe(final String ministryId){
        new AsyncTask<Void, Void, List<Ministry>>(){
            protected List<Ministry> doInBackground(Void... params) {
                Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
                return retriever.getAll().getObjects();
            }

            @Override
            protected void onPostExecute(List<Ministry> ministries) {
                for (Ministry ministry : ministries)
                    if (ministry.getId().equals(ministryId))
                        ParsePush.subscribeInBackground(ministry.getName().replaceAll("\\s",""));
            }
        }.execute();
    }

    /**
     * Unsubscribe from a ministry's push channel
     * @param ministryId
     */
    public static void unsubscribe(final String ministryId){
        new AsyncTask<Void, Void, List<Ministry>>(){
            protected List<Ministry> doInBackground(Void... params) {
                Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
                return retriever.getAll().getObjects();
            }

            @Override
            protected void onPostExecute(List<Ministry> ministries) {
                for (Ministry ministry : ministries)
                    if (ministry.getId().equals(ministryId))
                        ParsePush.unsubscribeInBackground(ministry.getName().replaceAll("\\s", ""));
            }
        }.execute();
    }

    /**
     * Unsubscribes from all ministry push channels
     */
    public static void clearPushChannels()
    {
        new AsyncTask<Void, Void, List<Ministry>>(){
            protected List<Ministry> doInBackground(Void... params) {
                Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
                return retriever.getAll().getObjects();
            }

            @Override
            protected void onPostExecute(List<Ministry> ministries) {
                for (Ministry ministry : ministries)
                    ParsePush.unsubscribeInBackground(ministry.getName().replaceAll("\\s",""));
            }
        }.execute();
    }
}
