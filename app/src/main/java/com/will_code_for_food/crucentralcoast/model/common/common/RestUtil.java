package com.will_code_for_food.crucentralcoast.model.common.common;

import android.content.res.Resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Brian on 11/16/2015.
 * Updated by Mason on 11/22/2015.
 */
public class RestUtil
{
    private static final String DB_URL = Util.getString(R.string.db_url);

    /**
     * Gets a JSON object from the server and returns it as a String.
     */
    private static String request(String from) {
        String dataUrl = DB_URL + from;
        URL url;
        HttpURLConnection connection = null;
        String responseStr = "!error";
        int timeout = Util.getInt(R.string.db_timeout);

        System.out.println("Timeout is: " + timeout);

        try {
            // Create connection
            url = new URL(dataUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setRequestMethod("GET");

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            rd.close();
            responseStr = response.toString();

        }

        catch (Exception e) {
            //return "exception in request(): " + e.toString();
            e.printStackTrace();
        }

        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return responseStr;
    }

    /**
     * Returns an array of Json Objects
     */
    public static JsonArray getAll(String from) {
        JsonParser parser = new JsonParser();
        String toParse = request(from);

        if (toParse.equals("!error")) {
            return null;
        }

        else {
            return parser.parse(toParse).getAsJsonArray();
        }
    }
}
