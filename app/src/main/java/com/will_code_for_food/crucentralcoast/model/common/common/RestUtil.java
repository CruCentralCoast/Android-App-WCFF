package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    //private static final String DB_URL = "https://httpbin.org/";
    private static final String DB_URL = "http://10.0.2.1:3000/api/";

    //depricated?
    /*
    public static String get(String tableName) throws IOException
    {
        StringBuilder jsonBuilder = new StringBuilder();

        //Get input stream
        URL getUrl = new URL(DB_URL + "get" + tableName);
        HttpsURLConnection connection = (HttpsURLConnection) getUrl.openConnection();
        BufferedReader restReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //Build json from stream
        String line = "";
        while ((line = restReader.readLine()) != null)
            jsonBuilder.append(line);
        return jsonBuilder.toString();
    }*/

    /**
     * Gets a JSON object from the server and returns it as a String.
     */
    private static String request(String from) {
        String dataUrl = DB_URL + from;
        URL url;
        HttpURLConnection connection = null;
        String responseStr = "!error";

        try {
            // Create connection
            url = new URL(dataUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
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
    private static JsonArray getAll(String from) {
        JsonParser parser = new JsonParser();
        String toParse = request(from);

        if (toParse.equals("!error")) {
            return null;
        }

        else {
            return parser.parse(toParse).getAsJsonArray();
        }
    }

    public static ArrayList<Ministry> getMinistries() {
        JsonArray ministriesJson;
        Iterator<JsonElement> iterator;
        ArrayList<Ministry> ministries = new ArrayList<Ministry>();
        JsonObject temp;

        ministriesJson = getAll("ministry/list"); // TODO: 11/22/2015 add this to strings file

        if (ministriesJson != null) {
            iterator = ministriesJson.iterator();

            while (iterator.hasNext()) {
                temp = iterator.next().getAsJsonObject();
                ministries.add(new Ministry(temp));
            }
        }

        return ministries;
    }

}
