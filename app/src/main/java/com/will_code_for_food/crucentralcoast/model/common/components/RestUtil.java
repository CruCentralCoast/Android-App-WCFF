package com.will_code_for_food.crucentralcoast.model.common.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Brian on 11/16/2015.
 */
public class RestUtil
{
    private static final String DB_URL = "https://httpbin.org/";

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
    }

}
