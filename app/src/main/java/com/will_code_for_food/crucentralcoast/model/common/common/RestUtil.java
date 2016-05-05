package com.will_code_for_food.crucentralcoast.model.common.common;

import android.support.v4.util.Pair;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.community_groups.CommunityGroupForm;
import com.will_code_for_food.crucentralcoast.model.community_groups.CommunityGroupQuestion;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.Youtube;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Brian on 11/16/2015.
 * Updated by Mason on 11/22/2015.
 * <p/>
 * Communicates with the REST api to retrieve DatabaseObjects from the database.
 */
public class RestUtil {
    private static final String DB_URL = Database.DB_URL;

    private static HttpURLConnection createGetConnection(String dbUrl, String from) throws Exception {
        String dataUrl = dbUrl + from;
        URL url = new URL(dataUrl);
        Logger.e("URL", url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Database.DB_TIMEOUT;
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(Database.HTTP_REQUEST_METHOD_GET);
        return connection;
    }

    private static HttpURLConnection createPostConnection(String from, String body, String contentType) throws Exception {
        String dataUrl = DB_URL + from;

        URL url = new URL(dataUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Database.DB_TIMEOUT;
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(Database.HTTP_REQUEST_METHOD_POST);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Accept", "application/json");

        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(body);
        wr.close();
        return connection;
    }

    private static HttpURLConnection createChangeConnection(String from, String body, String contentType, String id, String requestMethod) throws Exception {
        String dataUrl = DB_URL + from;

        URL url = new URL(dataUrl + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Database.DB_TIMEOUT;
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Accept", "application/json");

        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(body);
        wr.close();
        return connection;
    }

    /**
     * Gets a JSON object from the server and returns it as a String.
     */
    private static String request(HttpURLConnection connection) throws Exception {
        String responseStr = "!error";
        // Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = rd.readLine()) != null) {
            response.append(line + '\r');
        }

        rd.close();
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * Returns an array of Json Objects, or null if an error occured.
     */
    public static JsonArray get(String tableName) {
        JsonParser parser = new JsonParser();
        try {
            HttpURLConnection conn = createGetConnection(DB_URL, tableName);
            String toParse = request(conn);

            if (toParse.equals("!error")) {
                Logger.e("Get", "Got '!error'");
                return null;
            } else {
                return parser.parse(toParse).getAsJsonArray();
            }
        } catch (Exception ex) {
            Logger.e("Get", "Exception in get()");
            ex.printStackTrace();
            return null;
        }
    }

    public static CommunityGroupForm getMinistryQuestions(final String ministryId) {
        JsonArray ary = get("ministries/" + ministryId + "/questions");
        Logger.i("Getting questions", "Getting from " + ministryId);
        CommunityGroupForm form = new CommunityGroupForm(ministryId);
        if (ary != null) {
            for (JsonElement obj : ary) {
                Logger.i("Getting questions", "Found question");
                form.add(new CommunityGroupQuestion(obj.getAsJsonObject()));
            }
        } else {
            Logger.e("Getting questions", "Returned json array is null");
        }
        return form;
    }

    /**
     * Gets a playlist given its url query
     */
    public static Playlist getPlaylist(String url) {
        try {
            String numResults = Youtube.QUERY_MAXRESULTS + Youtube.MINRESULTS;
            JsonParser parser = new JsonParser();
            HttpURLConnection conn = createGetConnection(url + numResults, "");
            String toParse = request(conn);

            if (toParse.equals("!error")) {
                return null;
            }
            else {
                JsonElement videoElement = parser.parse(toParse);
                return new Playlist(videoElement.getAsJsonObject(), url);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the uploads playlist from the given user's Youtube account
     */
    public static Playlist getPlaylistFromUser(String username) {
        try {
            String channelQuery = Youtube.QUERY + Youtube.QUERY_CHANNEL;
            String userQuery = Youtube.QUERY_USERNAME + username;
            String uploadsId = getUploadsId(channelQuery + userQuery + Youtube.QUERY_KEY);
            String playlistQuery = Youtube.QUERY + Youtube.QUERY_PLAYLIST;
            String uploadsQuery = Youtube.QUERY_PLAYLIST_ID + uploadsId;

            return getPlaylist(playlistQuery + uploadsQuery + Youtube.QUERY_KEY);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the id of the uploads playlist given a channel query
     */
    private static String getUploadsId(String url) throws Exception {
        JsonParser parser = new JsonParser();
        HttpURLConnection conn = createGetConnection(url, "");
        String toParse = request(conn);

        if (toParse.equals("!error")) {
            return "";
        } else {
            JsonObject channel = parser.parse(toParse).getAsJsonObject();
            JsonArray items = channel.get(Youtube.JSON_LIST).getAsJsonArray();
            JsonObject content = items.get(0).getAsJsonObject();
            JsonObject contentDetails = content.get(Youtube.JSON_CONTENT_DETAILS).getAsJsonObject();
            JsonObject playlists = contentDetails.get(Youtube.JSON_RELATED_PLAYLISTS).getAsJsonObject();
            String uploads = playlists.get(Youtube.JSON_UPLOADS).getAsString();

            return uploads;
        }
    }

    /**
     * @param tableName Name of the db table to search
     * @param fields    A series of paired strings for searching purposes.
     *                  The first string should be the key, and the second the value
     * @return
     */
    public static JsonArray getWithConditions(String tableName, Pair<String, String>... fields) {

        JsonParser parser = new JsonParser();
        try {
            JsonObject bodyJson = new JsonObject();
            for (Pair<String, String> field : fields)
                bodyJson.addProperty(field.first, field.second);

            HttpURLConnection conn = createPostConnection(tableName
                    + Database.REST_QUERY_FIND, bodyJson.toString(), Database.CONTENT_TYPE_JSON);
            String toParse = request(conn);

            if (toParse.equals("!error"))
                return null;
            else
                return parser.parse(toParse).getAsJsonArray();
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            return null;
        }
    }

    /**
     * Helper for the methods create() and update(). Creates a post connection and sends the
     * JsonObject toSend to the database. If create() is used, the object is added a a new object in
     * the target collection. If update() is used, the object replaces an existing object in the target
     * collection. Since the code for these two methods is almost identical, it makes sense to
     * relocate it here.
     */
    private static JsonObject sendJsonObject(JsonObject toSend, String collectionName, boolean update) {

        HttpURLConnection connection = null;

        JsonParser parser = new JsonParser();
        JsonObject dbObj = null;

        try {
            if (update)
                connection = createChangeConnection(collectionName, toSend.toString(), Database.CONTENT_TYPE_JSON,
                        toSend.get(Database.JSON_KEY_COMMON_ID).getAsString(), Database.HTTP_REQUEST_METHOD_PATCH);
            else
                connection = createPostConnection(collectionName, toSend.toString(), Database.CONTENT_TYPE_JSON);

            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK || HttpResult == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();

                //Log.d("RestUtil.java", sb.toString());
                System.out.println(sb.toString());

                dbObj = parser.parse(sb.toString()).getAsJsonObject();

            } else {
                //Log.d("RestUtil.java", connection.getResponseMessage());
                System.out.println(connection.getResponseMessage());
            }

        } catch (Exception ex) {
            //Log.e("RestUtil.java", ex.toString());
            System.out.println(ex.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return dbObj;
    }

    /**
     * Sends a JSON object to the database, returns the resulting JSON object pulled from the database.
     * This new JSON object will have an auto-generated _id field.
     */
    public static JsonObject create(JsonObject toSend, String collectionName) {
        Logger.i("RestUtil.java", "Sending object to " + collectionName + ": " + toSend.toString());
        return sendJsonObject(toSend, collectionName, false);
    }

    /**
     *  Updates an existing object
     */
    public static JsonObject update(JsonObject updatedObject, String collectionName) {
        return sendJsonObject(updatedObject, collectionName, true);
    }

    private static boolean addDropHelper(Boolean remove, String rideId, String passengerId) {
        HttpURLConnection connection = null;
        String content = "passenger_id=" + passengerId;
        JsonParser parser = new JsonParser();
        JsonObject dbObj = new JsonObject();
        dbObj.addProperty("passenger_id", passengerId);
        boolean actionSuccessful = false;
        int HttpResult;

        try {
            if (remove) {
                connection = createChangeConnection(Database.REST_RIDE + "/" + rideId + "/" +
                                Database.REST_PASSENGER, dbObj.toString(), Database.CONTENT_TYPE_JSON, passengerId,
                        Database.HTTP_REQUEST_METHOD_DELETE);
            }
            else {
                connection = createPostConnection(Database.REST_RIDE + "/" + rideId + "/" +
                        Database.REST_PASSENGER, dbObj.toString(), Database.CONTENT_TYPE_JSON);
            }

            HttpResult = connection.getResponseCode();

            if(HttpResult == HttpURLConnection.HTTP_OK || HttpResult == HttpURLConnection.HTTP_CREATED){
                actionSuccessful = true;
                Log.d("RestUtil.java", connection.getResponseMessage());
            } else{
                Log.d("RestUtil.java", connection.getResponseMessage());
            }

        } catch (Exception ex) {
            Log.e("RestUtil.java", ex.toString());
            System.out.println(ex.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return actionSuccessful;
    }

    //adds a passenger to a ride
    public static boolean addPassenger(String rideId, String passengerId) {
        Log.i("RestUtil", "adding passenger " + passengerId + " to ride " + rideId);
        return addDropHelper(false, rideId, passengerId);
    }

    //removes a passenger from a ride
    public static boolean dropPassenger(String rideId, String passengerId) {
        Log.i("RestUtil", "dropping passenger " + passengerId + " from ride " + rideId);
        return addDropHelper(true, rideId, passengerId);
    }

    /**
     * Returns a passenger objects based on a person's phone number,
     * or null if none was found
     */
    public static Passenger getPassenger(String phoneNum) {
        JsonArray passengers = RestUtil.get(Database.REST_PASSENGER);
        Passenger tempPassenger = null;
        Passenger passenger = null;

        //get the latest Passenger object with that phone number
        if (passengers != null) {
            for (JsonElement tempElement : passengers) {
                tempPassenger = new Passenger(tempElement.getAsJsonObject());

                if (tempPassenger.getPhoneNumber().equals(phoneNum)) {
                    passenger = tempPassenger;
                }
            }
        }

        return passenger;
    }
}
