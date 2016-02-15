package com.will_code_for_food.crucentralcoast.model.common.common;

import android.support.v4.util.Pair;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Brian on 11/16/2015.
 * Updated by Mason on 11/22/2015.
 * Communicates with the REST api to retrieve DatabaseObjects from the database.
 */
public class RestUtil {
    private static final String DB_URL = Database.DB_URL;

    private static HttpURLConnection createGetConnection(String dbUrl, String from) throws Exception {
        String dataUrl = dbUrl + from;
        URL url = new URL(dataUrl);
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
            HttpURLConnection conn = createGetConnection(DB_URL, tableName
                    + Database.REST_QUERY_GET_ALL);
            String toParse = request(conn);

            if (toParse.equals("!error"))
                return null;
            else
                return parser.parse(toParse).getAsJsonArray();
        } catch (Exception ex) {
            return null;
        }
    }

    public static JsonArray getVideos(String url) {
        try {
            JsonParser parser = new JsonParser();
            HttpURLConnection conn = createGetConnection(url, "");
            String toParse = request(conn);

            if (toParse.equals("!error")) {
                return null;
            }
            else {
                JsonElement videoElement = parser.parse(toParse);
                JsonArray videoArray = videoElement.getAsJsonObject().get(Android.YOUTUBE_JSON_LIST).getAsJsonArray();
                return videoArray;
            }
        } catch (Exception e) {
            return null;
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

    private static JsonObject createUpdateHelper(JsonObject toSend, String collectionName, String command) {

        HttpURLConnection connection = null;

        JsonParser parser = new JsonParser();
        JsonObject dbObj = null;

        try {
            connection = createPostConnection(collectionName + command, toSend.toString(), Database.CONTENT_TYPE_JSON);

            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();

                //Log.d("RestUtil.java", sb.toString());
                System.out.println(sb.toString());

                dbObj = parser.parse(sb.toString()).getAsJsonObject().get("post").getAsJsonObject();

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
        return createUpdateHelper(toSend, collectionName, Database.REST_QUERY_CREATE);
    }

    /**
     *  Updates an existing object
     */
    public static JsonObject update(JsonObject updatedObject, String collectionName) {
        return createUpdateHelper(updatedObject, collectionName, Database.REST_QUERY_UPDATE);
    }

    private static boolean addDropHelper(String command, String rideId, String passengerId) {
        HttpURLConnection connection = null;
        String content = "ride_id=" + rideId + "&passenger_id=" + passengerId;
        JsonParser parser = new JsonParser();
        JsonObject dbObj = null;
        boolean actionSuccessful = false;
        int HttpResult;

        try {
            connection = createPostConnection(Database.REST_RIDE + command, content, Database.CONTENT_TYPE_URL_ENCODED);

            HttpResult = connection.getResponseCode();

            if(HttpResult == HttpURLConnection.HTTP_OK){
                actionSuccessful = true;
            } else{
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

        return actionSuccessful;
    }

    //adds a passenger to a ride
    public static boolean addPassenger(String rideId, String passengerId) {
        return addDropHelper(Database.REST_QUERY_ADD_PASSENGER, rideId, passengerId);
    }

    //removes a passenger from a ride
    public static boolean dropPassenger(String rideId, String passengerId) {
        return addDropHelper(Database.REST_QUERY_DROP_PASSENGER, rideId, passengerId);
    }
}
