package com.will_code_for_food.crucentralcoast.model.common.messaging;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GcmPubSub;
import com.parse.ParsePush;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Util to subscribe to push channels. This currently uses parse as the implementation.
 * Created by Brian on 1/20/2016.
 */
public class PushUtil {
    public static final String TOPICS = "/topics/";
    private static String gcmId;

    /**
     * Subscribe to a ministry's push channel
     * @param ministryId
     */
    public static void subscribe(final String ministryId){
        new AsyncTask<Void, Void, List<Ministry>>(){
            GcmPubSub subber = GcmPubSub.getInstance(MainActivity.context);

            protected List<Ministry> doInBackground(Void... params) {
                Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
                return retriever.getAll().getObjects();
            }

            @Override
            protected void onPostExecute(List<Ministry> ministries) {
                for (Ministry ministry : ministries)
                    if (ministry.getId().equals(ministryId))
                        try {
                            subber.subscribe(getGCMId(), TOPICS +  ministry.getId(), null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }
        }.execute();
    }

    /**
     * Unsubscribe from a ministry's push channel
     * @param ministryId
     */
    public static void unsubscribe(final String ministryId){
        new AsyncTask<Void, Void, List<Ministry>>(){
            GcmPubSub subber = GcmPubSub.getInstance(MainActivity.context);

            protected List<Ministry> doInBackground(Void... params) {
                Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
                return retriever.getAll().getObjects();
            }

            @Override
            protected void onPostExecute(List<Ministry> ministries) {
                for (Ministry ministry : ministries)
                    if (ministry.getId().equals(ministryId))
                        try {
                            subber.unsubscribe(getGCMId(), TOPICS + ministry.getId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    public static String getGCMId()
    {
        if (gcmId == null || gcmId.isEmpty()){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(new RegistrationIntentService());
            gcmId = prefs.getString(RegistrationIntentService.GCM_ID, "");
        }
        return gcmId;
    }
}
