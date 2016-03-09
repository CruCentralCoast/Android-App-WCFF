package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Youtube;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 3/8/2016.
 */
public class TestDB {

    private static final String JSON_ROOT = "src/test/java/com/will_code_for_food/crucentralcoast/JSON/";
    public static final String EVENTS_FILE = JSON_ROOT + "Events";
    public static final String MINISTRIES_FILE = JSON_ROOT + "Ministries";
    public static final String MINISTRY_TEAMS_FILE = JSON_ROOT + "MinistryTeams";
    public static final String PASSENGERS_FILE = JSON_ROOT + "Passengers";
    public static final String RESOURCES_FILE = JSON_ROOT + "Resources";
    public static final String RIDES_FILE = JSON_ROOT + "Rides";
    public static final String PLAYLIST_1_FILE = JSON_ROOT + "Playlist1";


    public static ArrayList<DatabaseObject> getFeed() {
        ArrayList list = new ArrayList<DatabaseObject>();

        list.addAll(getEvents());
        list.addAll(getResources());
        list.addAll(getVideos());

        return list;
    }

    public static ArrayList<Event> getEvents() {
        ArrayList list = new ArrayList<Event>();

        for (JsonElement event : loadTestObjects(EVENTS_FILE)) {
            list.add(new Event(event.getAsJsonObject(), true));
        }

        return list;
    }

    public static ArrayList<Ministry> getMinistries() {
        ArrayList list = new ArrayList<Ministry>();

        for (JsonElement element : loadTestObjects(MINISTRIES_FILE)) {
            list.add(new Ministry(element.getAsJsonObject()));
        }

        return list;
    }

    public static ArrayList<MinistryTeam> getMinistryTeams() {
        ArrayList list = new ArrayList<MinistryTeam>();

        for (JsonElement element : loadTestObjects(MINISTRY_TEAMS_FILE)) {
            list.add(new MinistryTeam(element.getAsJsonObject()));
        }

        return list;
    }

    public static ArrayList<Passenger> getPassengers() {
        ArrayList list = new ArrayList<Passenger>();

        for (JsonElement element : loadTestObjects(PASSENGERS_FILE)) {
            list.add(new Passenger(element.getAsJsonObject()));
        }

        return list;
    }

    public static ArrayList<Resource> getResources() {
        ArrayList list = new ArrayList<Resource>();

        for (JsonElement element : loadTestObjects(RESOURCES_FILE)) {
            list.add(new Resource(element.getAsJsonObject()));
        }

        return list;
    }

    public static ArrayList<Ride> getRides() {
        ArrayList list = new ArrayList<Ride>();

        for (JsonElement element : loadTestObjects(RIDES_FILE)) {
            list.add(new Ride(element.getAsJsonObject()));
        }

        return list;
    }

    public static ArrayList<Video> getVideos () {
        ArrayList<Video> list = new ArrayList<Video>();

        for (JsonElement element : loadTestObjects(PLAYLIST_1_FILE)) {
            list.add(new Video(element.getAsJsonObject()));
        }

        return list;
    }

    private static JsonArray loadTestObjects(String fileName) {

        JsonArray objects = new JsonArray();
        BufferedReader reader;
        String jsonString = "";
        String line;
        JsonParser parser = new JsonParser();

        try {

            reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                jsonString += line;
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!jsonString.equals("")) {
            objects = parser.parse(jsonString).getAsJsonArray();
        }

        return objects;
    }
}
