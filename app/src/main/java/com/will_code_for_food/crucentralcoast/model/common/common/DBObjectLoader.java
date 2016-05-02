package com.will_code_for_food.crucentralcoast.model.common.common;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.controller.retrieval.PlaylistRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.TaskFactory;
import com.will_code_for_food.crucentralcoast.controller.retrieval.VideoRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.Youtube;

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
    private static final int OBJECTS_TO_LOAD = 9; //the number of dbobject types to load during the splashscreen
    private static int loadCount = 0; //the current number of dbobject types loaded
    public static boolean testMode = false;

    /**
     * Loads all objects. Doesn't wait for them to finish loading.
     */
    public static void loadAll() {
        loadCount = 0;
        loadEvents();
        loadCampuses();
        loadMinistries();
        loadMinistryTeams();
        loadRides();
        loadResources();
        loadSummerMissions();
        loadVideos();
        loadPassengers();
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
        loadMinistryTeams(waitTime);
        loadRides(waitTime);
        loadResources(waitTime);
        loadSummerMissions(waitTime);
        //TODO: loadVideos(waitTime);
        loadPassengers(waitTime);
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
        Logger.i("DBObjectLoader", "Loading events");
        new GetObjectTaskExecuter<Event>(RetrieverSchema.EVENT).execute();
    }

    /**
     * Loads events. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadEvents(long waitTime) {
        return loadDelayed(RetrieverSchema.EVENT, waitTime);
    }

    /**
     * Loads ministries. Doesn't wait for them to finish loading.
     */
    public static void loadMinistries() {
        Logger.i("DBObjectLoader", "Loading ministries");
        new GetObjectTaskExecuter<Ministry>(RetrieverSchema.MINISTRY).execute();
    }

    /**
     * Loads ministries. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadMinistries(long waitTime) {
        return loadDelayed(RetrieverSchema.MINISTRY, waitTime);
    }

    /**
     * Loads rides. Doesn't wait for them to finish loading.
     */
    public static void loadRides() {
        Logger.i("DBObjectLoader", "Loading rides");
        new GetObjectTaskExecuter<Ride>(RetrieverSchema.RIDE).execute();
    }

    /**
     * Loads rides. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadRides(long waitTime) {
        return loadDelayed(RetrieverSchema.RIDE, waitTime);
    }

    /**
     * Loads campuses. Doesn't wait for them to finish loading.
     */
    public static void loadCampuses() {
        Logger.i("DBObjectLoader", "Loading campuses");
        new GetObjectTaskExecuter<Campus>(RetrieverSchema.CAMPUS).execute();
    }

    /**
     * Loads campuses. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadCampuses(long waitTime) {
        return loadDelayed(RetrieverSchema.CAMPUS, waitTime);
    }

    /**
     * Loads resources. Doesn't wait for them to finish loading.
     */
    public static void loadResources() {
        Logger.i("DBObjectLoader", "Loading resources");
        new GetObjectTaskExecuter<Resource>(RetrieverSchema.RESOURCE).execute();
    }

    /**
     * Loads resources. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadResources(long waitTime) {
        return loadDelayed(RetrieverSchema.RESOURCE, waitTime);
    }

    /**
     * Loads summer missions. Doesn't wait for them to finish loading.
     */
    public static void loadSummerMissions() {
        Logger.i("DBObjectLoader", "Loading summer missions");
        new GetObjectTaskExecuter<Resource>(RetrieverSchema.SUMMER_MISSION).execute();
    }

    /**
     * Loads summer missions. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadSummerMissions(long waitTime) {
        return loadDelayed(RetrieverSchema.SUMMER_MISSION, waitTime);
    }

    /**
     * Loads ministry teams. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadMinistryTeams(long waitTime) {
        return loadDelayed(RetrieverSchema.MINISTRY_TEAM, waitTime);
    }

    /**
     * Loads ministry teams. Doesn't wait for them to finish loading.
     */
    public static void loadMinistryTeams() {
        Logger.i("DBObjectLoader", "Loading ministry teams");
        new GetObjectTaskExecuter<Resource>(RetrieverSchema.MINISTRY_TEAM).execute();
    }

    /**
     * Loads passengers. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadPassengers(long waitTime) {
        return loadDelayed(RetrieverSchema.PASSENGER, waitTime);
    }

    /**
     * Loads ministry teams. Doesn't wait for them to finish loading.
     */
    public static void loadPassengers() {
        Logger.i("DBObjectLoader", "Loading ministry teams");
        new GetObjectTaskExecuter<Resource>(RetrieverSchema.PASSENGER).execute();
    }

    /**
     * Loads youtube videos. Doesn't wait for them to finish loading.
     */
    public static void loadVideos() {
        new GetVideoTask().execute();
    }

    /**
     * Loads youtube videos. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadVideos(long waitTime) {
        try {
            new GetVideoTask().execute().get(waitTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }

        return true;
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

    public static ArrayList<Playlist> getPlaylists() {
        initData();
        return (Content<Playlist>) data.get(Database.PLAYLISTS);
    }

    public static ArrayList<Passenger> getPassengers() {
        initData();
        return (Content<Passenger>) data.get(Database.REST_PASSENGER);
    }

    public static Passenger getPassenger(String phoneNum) {
        ArrayList<Passenger> passengers = getPassengers();
        Passenger passenger = null;


        //get the latest Passenger object with that phone number
        if (passengers != null) {
            for (Passenger tempPassenger : passengers) {
                if (tempPassenger.getPhoneNumber().equals(phoneNum)) {
                    passenger = tempPassenger;
                }
            }
        }

        return passenger;
    }

    public static ArrayList<Video> getVideos() {
        initData();
        return (Content<Video>) data.get(Database.VIDEOS);
    }

    public static Content get(String key) {
        initData();
        return data.get(key);
    }

    public static void initData() {
        if (data == null) {
            Logger.i("DBObjectLoader", "data was null");
            data = new ConcurrentHashMap<String, Content>();
        }
    }

    public static void put(String key, Content content) {
        data.put(key, content);
    }

    public static void objectLoaded() {
        loadCount++;
    }

    public static int getLoadCount() {
        return loadCount;
    }

    private static boolean loadDelayed(RetrieverSchema schema, long waitTime) {
        try {
            new GetObjectTaskExecuter<Event>(schema).execute().get(waitTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static class GetObjectTaskExecuter<T extends DatabaseObject> {

        RetrieverSchema schema;

        public GetObjectTaskExecuter(RetrieverSchema schema) {
            this.schema = schema;
        }

        public AsyncTask execute() {

            if (testMode) {
                return new TestGetObjectTask<T>(schema).execute();
            } else {
                return new GetObjectTask<T>(schema).execute();
            }
        }
    }

    private static class GetObjectTask<T extends DatabaseObject> extends AsyncTask<Void, Void, Void> {

        RetrieverSchema schema;
        String key;

        public GetObjectTask(RetrieverSchema schema) {
            this.schema = schema;
            this.key = schema.getTableName();
        }

        @Override
        protected Void doInBackground(Void... params) {
            initData();

            Logger.i("DBObjectLoader", "Getting objects of type [" + key + "] from database");
            Content<T> content = getContent();

            if (content != null) {
                data.put(key, content);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!finishedLoading()) {
                loadCount++;
            }

            Logger.i("DBObjectLoader", "Finished getting objects of type [" + key + "] from database (loadCount is: " + loadCount + ")");
        }

        public Content<T> getContent() {
            Content<T> content = new SingleRetriever<T>(schema).getAll();
            return content;
        }
    }

    private static class TestGetObjectTask<T extends DatabaseObject> extends GetObjectTask<T> {

        public TestGetObjectTask(RetrieverSchema schema) {
            super(schema);
        }

        @Override
        public Content<T> getContent() {
            return new Content<T>(new ArrayList<T>(), ContentType.TEST);
        }
    }

    private static class GetVideoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Logger.i("DBObjectLoader", "Getting videos from youtube");

            PlaylistRetriever playlistRetriever = new PlaylistRetriever();
            VideoRetriever videoRetriever = new VideoRetriever();

            data.put(Database.PLAYLISTS, playlistRetriever.getAll());
            data.put(Database.VIDEOS, videoRetriever.getAll());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!finishedLoading()) {
                loadCount++;
            }

            Logger.i("DBObjectLoader", "Finished getting videos from youtube (loadCount is: " + loadCount + ")");
        }
    }
}
