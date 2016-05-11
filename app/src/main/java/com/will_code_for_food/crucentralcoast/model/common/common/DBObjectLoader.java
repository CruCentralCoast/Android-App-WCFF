package com.will_code_for_food.crucentralcoast.model.common.common;

import android.os.AsyncTask;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.PlaylistRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.VideoRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    /**
     * Loads all objects. Doesn't wait for them to finish loading.
     */
    public static void loadAll() {
        loadCount = 0;
        for (RetrieverSchema schema : RetrieverSchema.values()){
            loadObjects(schema);
        }
        loadVideos();
    }

    /**
     * Loads all objects. Waits for each one to get loaded.
     * @param waitTime The amount of time to wait for each object in milliseconds;
     */
    public static void loadAll(long waitTime) {
        loadCount = 0;
        for (RetrieverSchema schema : RetrieverSchema.values()){
            loadObjects(schema, waitTime);
        }
        //TODO: loadVideos(waitTime);
    }

    /**
     * Returns true if all database objects have loaded.
     */
    public static boolean finishedLoading() {
        return loadCount == OBJECTS_TO_LOAD;
    }


    public static void loadObjects(RetrieverSchema schema){
        Logger.i("DBObjectLoader", "Loading " + schema.getTableName());
        new GetObjectTask<JsonDatabaseObject>(schema).execute();
    }

    /**
     * Loads events. Waits for them to finish loading.
     *
     * @param waitTime maximum wait time in milliseconds
     */
    public static boolean loadObjects(RetrieverSchema schema, long waitTime) {
        return loadDelayed(schema, waitTime);
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

    private static void initData() {
        if (data == null) {
            Logger.i("DBObjectLoader", "data was null");
            data = new ConcurrentHashMap<String, Content>();
        }
    }


    private static boolean loadDelayed(RetrieverSchema schema, long waitTime) {
        try {
            new GetObjectTask(schema).execute().get(waitTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }

        return true;
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
            Content<T> content = new SingleRetriever<T>(schema).getAll();

            // filtering out cancelled rides
            List<T> pruned = new ArrayList<>();
            for (T obj: content.getObjects()) {
                if (obj instanceof Ride) {
                    Log.i("PRUNING RIDES", "Loaded object is a Ride instance");
                    // don't name yourself 'CANCELLED'
                    if (!((Ride)obj).getDriverName().equals("CANCELLED")) {
                        pruned.add(obj);
                    } else {
                        Log.i("PRUNING RIDES", "Found cancelled ride");
                    }
                } else {
                    pruned.add(obj);
                }
            }
            content = new Content<>(pruned, content.getType());

            data.put(key, content);
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
    }

    private static class GetVideoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Logger.i("DBObjectLoader", "Getting videos from youtube");
            initData();

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
