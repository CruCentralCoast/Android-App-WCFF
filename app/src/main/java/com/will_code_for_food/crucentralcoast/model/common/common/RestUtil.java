package com.will_code_for_food.crucentralcoast.model.common.common;

import android.content.res.Resources;
import android.support.v4.util.Pair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.controller.local_io.log.Logger;
import com.will_code_for_food.crucentralcoast.values.Database;

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

    private static HttpURLConnection createGetConnection(String from) throws Exception {
        String dataUrl = DB_URL + from;
        URL url = new URL(dataUrl);
        ;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Database.DB_TIMEOUT;
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(Database.HTTP_REQUEST_METHOD_GET);
        return connection;
    }

    private static HttpURLConnection createPostConnection(String from, String body) throws Exception {
        String dataUrl = DB_URL + from;
        URL url = new URL(dataUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Database.DB_TIMEOUT;
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(Database.HTTP_REQUEST_METHOD_POST);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(body);
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
            HttpURLConnection conn = createGetConnection(tableName
                    + Database.REST_QUERY_GET_ALL);
            String toParse = request(conn);

            if (toParse.equals("!error"))
                return null;
            else
                return parser.parse(toParse).getAsJsonArray();
        } catch (Exception ex) {
            new Logger().logError(ex.getLocalizedMessage());
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
                    + Database.REST_QUERY_FIND, bodyJson.toString());
            String toParse = request(conn);

            if (toParse.equals("!error"))
                return null;
            else
                return parser.parse(toParse).getAsJsonArray();
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            new Logger().logError(ex.getLocalizedMessage());
            return null;
        }
    }
}
