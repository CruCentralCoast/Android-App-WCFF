package com.will_code_for_food.crucentralcoast.model.common.common;

import android.os.AsyncTask;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.VideoRetriever;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static ConcurrentHashMap<String, Content> data;
    private static final int OBJECTS_TO_LOAD = 7;
    private static int loadCount = 0;

    /**
     * Loads all objects. Doesn't wait for them to finish loading.
     */
    public static void loadAll() {
        loadCount = 0;
        loadEvents();
        loadCampuses();
        loadMinistries();
        loadRides();
        loadResources();
        loadSummerMissions();
        loadVideos();
    }

    /**
     * Loads all objects. Waits for each one to get loaded.
     * @param waitTime The amount of time to wait for each object in milliseconds;
     */
    public static void loadAll(long waitTime) {
        loadCount = 0;
        loadEvents(waitTime);
        loadCampuses(waitTime);
        loadMinistries(waitTime);
        loadRides(waitTime);
        loadResources(waitTime);
        loadSummerMissions(waitTime);
        //TODO: loadVideos(waitTime);
    }

    /**
     * Returns true if all database objects have loaded.
     */
    public static boolean finishedLoading() {
        return loadCount == OBJECTS_TO_LOAD;
    }

    /**
     * Loads events. Doesn't wait for them to finish loading.
     */
    public static void loadEvents() {
        Log.i("DBObjectLoader", "Loading events");
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
        Log.i("DBObjectLoader", "Loading ministries");
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
        Log.i("DBObjectLoader", "Loading rides");
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
        Log.i("DBObjectLoader", "Loading campuses");
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
        Log.i("DBObjectLoader", "Loading resources");
        new GetOjbectTask<Resource>(RetrieverSchema.RESOURCE, Database.REST_RESOURCE).execute();
    }

    /**
     * Loads resources. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadResources(long waitTime) {
        return loadDelayed(RetrieverSchema.RESOURCE, Database.REST_RESOURCE, waitTime);
    }

    /**
     * Loads summer missions. Doesn't wait for them to finish loading.
     */
    public static void loadSummerMissions() {
        Log.i("DBObjectLoader", "Loading summer missions");
        new GetOjbectTask<Resource>(RetrieverSchema.SUMMER_MISSION, Database.REST_SUMMER_MISSION).execute();
    }

    /**
     * Loads summer missions. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadSummerMissions(long waitTime) {
        return loadDelayed(RetrieverSchema.SUMMER_MISSION, Database.REST_SUMMER_MISSION, waitTime);
    }

    /**
     * Loads youtube videos.
     */
    public static void loadVideos() {
        new GetVideoTask().execute();
    }

    public static ArrayList<Event> getEvents() {
        initData();
        return (Content<Event>) data.get(Database.REST_EVENT);
    }

    public static ArrayList<Ministry> getMinistries() {
        initData();
        return (Content<Ministry>) data.get(Database.REST_MINISTRY);
    }

    public static ArrayList<Ride> getRides() {
        initData();
        return (Content<Ride>) data.get(Database.REST_RIDE);
    }

    public static ArrayList<Campus> getCampuses() {
        initData();
        return (Content<Campus>) data.get(Database.REST_CAMPUS);
    }

    public static ArrayList<Resource> getResources() {
        initData();
        return (Content<Resource>) data.get(Database.REST_RESOURCE);
    }

    public static Content get(String key) {
        initData();
        return data.get(key);
    }

    private static void initData() {
        if (data == null) {
            Log.i("DBObjectLoader", "data was null");
            data = new ConcurrentHashMap<String, Content>();
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

            Log.i("DBObjectLoader", "Getting objects of type [" + key + "] from database");
            Content<T> content = new SingleRetriever<T>(schema).getAll();

            if (content != null) {
                data.put(key, content);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            loadCount++;
            Log.i("DBObjectLoader", "Finished getting objects of type [" + key + "] from database (loadCount is: " + loadCount + ")");
        }
    }

    private static class GetVideoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Playlist videoPlaylist;
            List<Video> videos = null;
            VideoRetriever retriever = new VideoRetriever();

            Log.i("DBObjectLoader", "Getting videos from youtube");

            data.put(Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS, retriever.getAll());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            loadCount++;
            Log.i("DBObjectLoader", "Finished getting videos from youtube (loadCount is: " + loadCount + ")");
        }
    }
}
