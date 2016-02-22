package com.will_code_for_food.crucentralcoast.model.common.common;

import android.os.AsyncTask;

import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by MasonJStevenson on 2/21/2016.
 *
 * Loads and saves DB objects for Fragment classes to use. This is intended to replace all the
 * static variables we have been saving in various files. If you want to use a static variable to
 * save a list of DatabaseObjects, do it here instead by calling DBObjectLoader.loadX
 */
public class DBObjectLoader {

    private static ConcurrentHashMap<String, ArrayList> data;

    /**
     * Loads all objects. Doesn't wait for them to finish loading.
     */
    public static void loadAll() {
        loadEvents();
        loadCampuses();
        loadMinistries();
        loadRides();
        loadResources();
    }

    /**
     * Loads all objects. Waits for each one to get loaded.
     * @param waitTime The amount of time to wait for each object in milliseconds;
     */
    public static void loadAll(long waitTime) {
        loadEvents(waitTime);
        loadCampuses(waitTime);
        loadMinistries(waitTime);
        loadRides(waitTime);
        loadResources(waitTime);
    }

    /**
     * Loads events. Doesn't wait for them to finish loading.
     */
    public static void loadEvents() {
        new GetOjbectTask<Event>(RetrieverSchema.EVENT, Database.REST_EVENT).execute();
    }

    /**
     * Loads events. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadEvents(long waitTime) {
        return loadDelayed(RetrieverSchema.EVENT, Database.REST_EVENT, waitTime);
    }

    /**
     * Loads ministries. Doesn't wait for them to finish loading.
     */
    public static void loadMinistries() {
        new GetOjbectTask<Ministry>(RetrieverSchema.MINISTRY, Database.REST_MINISTRY).execute();
    }

    /**
     * Loads ministries. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadMinistries(long waitTime) {
        return loadDelayed(RetrieverSchema.MINISTRY, Database.REST_MINISTRY, waitTime);
    }

    /**
     * Loads rides. Doesn't wait for them to finish loading.
     */
    public static void loadRides() {
        new GetOjbectTask<Ride>(RetrieverSchema.RIDE, Database.REST_RIDE).execute();
    }

    /**
     * Loads rides. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadRides(long waitTime) {
        return loadDelayed(RetrieverSchema.RIDE, Database.REST_RIDE, waitTime);
    }

    /**
     * Loads campuses. Doesn't wait for them to finish loading.
     */
    public static void loadCampuses() {
        new GetOjbectTask<Campus>(RetrieverSchema.CAMPUS, Database.REST_CAMPUS).execute();
    }

    /**
     * Loads campuses. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadCampuses(long waitTime) {
        return loadDelayed(RetrieverSchema.CAMPUS, Database.REST_CAMPUS, waitTime);
    }

    /**
     * Loads resources. Doesn't wait for them to finish loading.
     */
    public static void loadResources() {
        new GetOjbectTask<Resource>(RetrieverSchema.RESOURCE, Database.REST_RESOURCE);
    }

    /**
     * Loads resources. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadResources(long waitTime) {
        return loadDelayed(RetrieverSchema.RESOURCE, Database.REST_RESOURCE, waitTime);
    }

    public static ArrayList<Event> getEvents() {
        initData();
        return (ArrayList<Event>) data.get(Database.REST_EVENT);
    }

    public static ArrayList<Ministry> getMinistries() {
        return (ArrayList<Ministry>) data.get(Database.REST_MINISTRY);
    }

    public static ArrayList<Ride> getRides() {
        initData();
        return (ArrayList<Ride>) data.get(Database.REST_RIDE);
    }

    public static ArrayList<Campus> getCampuses() {
        initData();
        return (ArrayList<Campus>) data.get(Database.REST_CAMPUS);
    }

    public static ArrayList<Resource> getResources() {
        initData();
        return (ArrayList<Resource>) data.get(Database.REST_RESOURCE);
    }

    private static void initData() {
        if (data == null) {
            data = new ConcurrentHashMap<String, ArrayList>();
        }
    }


    private static boolean loadDelayed(RetrieverSchema schema, String tag, long waitTime) {
        try {
            new GetOjbectTask<Event>(schema, tag).execute().get(waitTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static class GetOjbectTask<T extends DatabaseObject> extends AsyncTask<Void, Void, Void> {

        RetrieverSchema schema;
        String key;

        public GetOjbectTask(RetrieverSchema schema, String key) {
            this.schema = schema;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            initData();

            data.put(key, new SingleRetriever<T>(schema).getAll());
            return null;
        }
    }
}
