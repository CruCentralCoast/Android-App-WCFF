package com.will_code_for_food.crucentralcoast.model.common.common;

import android.content.res.Resources;
import android.util.Pair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.local_io.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Brian on 11/16/2015.
 * Updated by Mason on 11/22/2015.
 */
public class RestUtil
{
    private static final String DB_URL = Util.getString(R.string.db_url);

    private static HttpURLConnection createGetConnection(String from) throws Exception
    {
        String dataUrl = DB_URL + from;
        URL url = new URL(dataUrl);;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Util.getInt(R.string.db_timeout);
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod("GET");
        return connection;
    }

    private static HttpURLConnection createPostConnection(String from, Pair<String, String>... fields) throws Exception
    {
        String dataUrl = DB_URL + from;
        URL url = new URL(dataUrl);;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeout = Util.getInt(R.string.db_timeout);
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod("POST");

        for (Pair<String, String> field : fields)
        {
            connection.addRequestProperty(field.first, field.second);
        }
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
     * Returns an array of Json Objects
     */
    public static JsonArray getAll(String tableName) {
        JsonParser parser = new JsonParser();
        try{
            HttpURLConnection conn = createGetConnection(tableName + "/" + "list");
            String toParse = request(conn);

            if (toParse.equals("!error"))
                return null;
            else
                return parser.parse(toParse).getAsJsonArray();
        }
        catch (Exception ex){
            new Logger().logError(ex.getLocalizedMessage());
            return null;
        }
    }

    /**
     *
     * @param tableName Name of the db table to search
     * @param fields A series of paired strings for searching purposes.
     *               The first string should be the key, and the second the value
     * @return
     */
    public static JsonArray getAllWithCondition(String tableName, Pair<String, String>... fields)
    {

        JsonParser parser = new JsonParser();
        try{
            HttpURLConnection conn = createPostConnection(tableName + "/" + "find", fields);
            String toParse = request(conn);

            if (toParse.equals("!error"))
                return null;
            else
                return parser.parse(toParse).getAsJsonArray();
        }
        catch (Exception ex){
            new Logger().logError(ex.getLocalizedMessage());
            return null;
        }
    }
}
