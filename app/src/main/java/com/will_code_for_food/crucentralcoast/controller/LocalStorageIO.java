package com.will_code_for_food.crucentralcoast.controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Cache;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic tool to read/write local files to the phone,
 * outside of the preferences
 *
 * Created by Gavin on 2/3/2016.
 */
public class LocalStorageIO {
    public final static String HASHMAP_DELIMITER = "|";
    /**
     * Writes a list of strings to internal storage at the given file name. Creates
     * the file if necessary. Each element in the list is given its own line. Clears
     * the file if it already exists.
     */
    public static boolean writeList(final List<String> list, final String fileName) {
        File path = MainActivity.context.getFilesDir();
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(
                    Util.getContext().openFileOutput(fileName, Context.MODE_PRIVATE));
            for (String string : list) {
                writer.write(string + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            Log.e("Write Error", "File write failed: " + e.toString());
            return false;
        }
    }

    /**
     * Reads a file to return a list, containing one element per line. Returns null on error.
     */
    public static List<String> readList(final String fileName) {
        List<String> list = new ArrayList<>();
        try {
            InputStream inputStream = MainActivity.context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inReader);
                String receiveString;
                while ( (receiveString = reader.readLine()) != null ) {
                    list.add(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Read Error", "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("Read Error", "Can not read file: " + e.toString());
            return null;
        }
        return list;
    }

    /**
     * Adds a string to the end of a file
     */
    public static boolean appendToList(final String data, final String fileName) {
        if (!fileExists(fileName))
            return writeSingleLineFile(fileName, data);

        List<String> list = readList(fileName);
        if (list != null) {
            list.add(data);
            return writeList(list, fileName);
        }
        return false;
    }

    /**
     * Adds a list of strings to the end of a file
     */
    public static boolean appendToList(final List<String> data, final String fileName) {
        if (!fileExists(fileName))
            return writeList(data, fileName);

        List<String> list = readList(fileName);
        if (list != null) {
            list.addAll(data);
            return writeList(list, fileName);
        }
        return false;
    }

    /**
     * Removes the first occurrence of a string from a file if it exists
     */
    public static boolean removeFromList(final String toRemove, final String fileName) {
        List<String> list = readList(fileName);
        if (list != null && list.contains(toRemove)) {
            list.remove(toRemove);
            return writeList(list, fileName);
        }
        return false;
    }

    /**
     * Returns whether or not a list file contains a string
     */
    public static boolean listContains(final String contains, final String fileName) {
        List<String> list = readList(fileName);
        return list != null && list.contains(contains);
    }

    /**
     * Writes a hashmap to a file. Deletes the file if it existed previously, returns whether or
     * not the write was successful
     */
    public static boolean writeMap(final Map<String, String> map, final String fileName) {
        List<String> list = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.contains(HASHMAP_DELIMITER) || map.get(key).contains(HASHMAP_DELIMITER)) {
                Log.w("WRITING HASHMAP",
                        "Warning: Hashmap contains the sequence used to separate keys and values." +
                            " This could cause errors.");
            }
            list.add(key + HASHMAP_DELIMITER + map.get(key));
        }
        return writeList(list, fileName);
    }

    /**
     * Reads a HashMap from a file, returns null if unsuccessful
     */
    public static HashMap<String, String> readMap(final String fileName) {
        HashMap<String, String> map = new HashMap<>();
        List<String> list = readList(fileName);
        if (list == null) {
            return null;
        }
        for (String line : list) {
            String[] split = line.split("\\" + HASHMAP_DELIMITER);
            map.put(split[0], split[1]);
        }
        return map;
    }

    /**
     * Adds a key-value pair to an exsiting map file
     */
    public static boolean putInMap(final String newKey, final String newValue,
                                   final String fileName) {
        Map<String, String> map = readMap(fileName);
        if (map != null) {
            map.put(newKey, newValue);
            return writeMap(map, fileName);
        }
        return false;
    }

    /**
     * Removes an element from an existing map
     */
    public static boolean removeFromMap(final String key, final String fileName) {
        Map<String, String> map = readMap(fileName);
        if (map != null && map.keySet().contains(key)) {
            map.remove(key);
            return writeMap(map, fileName);
        }
        return false;
    }

    /**
     * Returns whether or not a key is in an existing hashmap file
     */
    public static boolean mapContainsKey(final String key, final String fileName) {
        Map<String, String> map = readMap(fileName);
        return map != null && map.keySet().contains(key);
    }

    /**
     * Returns whether or not a value is in an existing hashmap file
     */
    public static boolean mapContainsValue(final String value, final String fileName) {
        Map<String, String> map = readMap(fileName);
        if (map != null) {
            for (String key : map.keySet()) {
                if (map.get(key).equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Deletes a file from internal storage
     */
    public static boolean deleteFile(final String fileName) {
        return Util.getContext().deleteFile(fileName);
    }

    /**
     * Outputs a file contents to the screen
     */
    public static void printFile(final String fileName) {
        List<String> list = readList(fileName);
        if (list != null) {
            Log.i("Printing", fileName);
            for (String line : list) {
                Log.i("\t", line);
            }
        } else {
            Log.e("Printing", "Could not find file: " + fileName);
        }
    }

    /**
     * Checks if a file exists
     */
    public static boolean fileExists(final String fileName) {
        return readList(fileName) != null;
    }

    /**
     * Writes a file containing a single line
     */
    public static boolean writeSingleLineFile(final String fileName, final String line) {
        List<String> list = Arrays.asList(line);
        return writeList(list, fileName);
    }

    /**
     * Reads the first line from a file
     */
    public static String readSingleLine(final String fileName) {
        List<String> list = readList(fileName);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }
}
