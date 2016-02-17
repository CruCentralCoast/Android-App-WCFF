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
    public static RegistrationIntentService regService;
    private static String gcmId;

    /**
     * Subscribe to a push channel
     * @param channelName
     */
    public static void subscribe(final String channelName){
        new AsyncTask<Void, Void, Void>(){
            GcmPubSub subber = GcmPubSub.getInstance(regService);

            protected Void doInBackground(Void... params) {
                try {
                    subber.subscribe(getGCMId(), TOPICS +  channelName, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    /**
     * Unsubscribe from a ministry's push channel
     * @param channelName
     */
    public static void unsubscribe(final String channelName){
        new AsyncTask<Void, Void, Void>(){
            GcmPubSub subber = GcmPubSub.getInstance(regService);

            protected Void doInBackground(Void... params) {
                try {
                    subber.unsubscribe(getGCMId(), TOPICS + channelName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    /**
     * Unsubscribes from all ministry push channels
     */
    public static void clearPushChannels()
    {
        new AsyncTask<Void, Void, Void>(){
            protected Void doInBackground(Void... params) {
                Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
                List<Ministry> ministries = retriever.getAll().getObjects();

                try {
                    GcmPubSub subber = GcmPubSub.getInstance(MainActivity.context);

                    //Unsubscribe from all ministries
                    for (Ministry ministry : ministries)
                        subber.unsubscribe(getGCMId(), TOPICS + ministry.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    public static String getGCMId()
    {
        if (gcmId == null || gcmId.isEmpty()){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(regService);
            gcmId = prefs.getString(RegistrationIntentService.GCM_ID, "");
        }
        return gcmId;
    }
}
