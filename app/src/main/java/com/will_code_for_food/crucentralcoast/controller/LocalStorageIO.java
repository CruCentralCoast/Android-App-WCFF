package com.will_code_for_food.crucentralcoast.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.view.common.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    public static boolean writeList(final List<String> list, final String fileName,
                                    final boolean readable) {
        if (!Logger.testMode) {
            File path = Util.getContext().getFilesDir();
            File file = new File(path, fileName);
            try {
                file.createNewFile();
                if (readable) {
                    if (!file.setReadable(true, false)) {
                        Log.e("File Write", "Global read permissions error");
                    }
                }
                OutputStreamWriter writer = new OutputStreamWriter(Util.getContext().openFileOutput(fileName, Context.MODE_PRIVATE));
                for (String string : list) {
                    writer.write(string + "\n");
                }
                writer.close();
                return true;
            } catch (IOException e) {
                Log.e("Write Error", "File write failed: " + e.toString());
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean copyToExternal(String filename){
        if (!Logger.testMode) {
            Context context = Util.getContext();
            if (context == null) {
                Log.e("File copy", "Null context");
                return false;
            }
            try {
                File file = new File(context.getExternalFilesDir(null), filename); //Get file location from external source
                InputStream is = new FileInputStream(context.getFilesDir() + File.separator + filename); //get file location from internal
                OutputStream os = new FileOutputStream(file); //Open your OutputStream and pass in the file you want to write to
                byte[] toWrite = new byte[is.available()]; //Init a byte array for handing data transfer
                Log.i("Available", is.available() + "");
                int result = is.read(toWrite); //Read the data from the byte array
                Log.i("Result", result + "");
                os.write(toWrite); //Write it to the output stream
                is.close(); //Close it
                os.close(); //Close it
                Log.i("Copying to", "" + context.getExternalFilesDir(null) + File.separator + filename);
                Log.i("Copying from", context.getFilesDir() + File.separator + filename + "");
                return true;
            } catch (Exception e) {
                Log.e("File copy", "Failed");
                Toast.makeText(context, "File copy failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean writeList(final List<String> list, final String fileName) {
        return writeList(list, fileName, false);
    }

    /**
     * Reads a file to return a list, containing one element per line. Returns null on error.
     */
    public static List<String> readList(final String fileName) {
        List<String> list = new ArrayList<>();
        if (!Logger.testMode) {
            try {
                InputStream inputStream = MyApplication.getContext().openFileInput(fileName);

                if (inputStream != null) {
                    InputStreamReader inReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inReader);
                    String receiveString;
                    while ((receiveString = reader.readLine()) != null) {
                        list.add(receiveString);
                    }
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                Log.e("Read Error", "File not found: " + e.toString());
                return null;
            } catch (IOException e) {
                Log.e("Read Error", "Can not read file: " + e.toString());
                return null;
            }
            return list;
        } else {
            return list;
        }
    }

    /**
     * Adds a string to the end of a file
     */
    public static boolean appendToList(final String data, final String fileName) {
        if (!Logger.testMode) {
            if (!fileExists(fileName)) {
                return writeSingleLineFile(fileName, data);
            }
            List<String> list = readList(fileName);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(data);
            return writeList(list, fileName);
        } else {
            return false;
        }
    }

    /**
     * Adds a list of strings to the end of a file
     */
    public static boolean appendToList(final List<String> data, final String fileName) {
        if (!Logger.testMode) {
            if (!fileExists(fileName))
                return writeList(data, fileName);

            List<String> list = readList(fileName);
            if (list != null) {
                list.addAll(data);
                return writeList(list, fileName);
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Removes the first occurrence of a string from a file if it exists
     */
    public static boolean removeFromList(final String toRemove, final String fileName) {
        if (!Logger.testMode) {
            List<String> list = readList(fileName);
            if (list != null && list.contains(toRemove)) {
                list.remove(toRemove);
                return writeList(list, fileName);
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Returns whether or not a list file contains a string
     */
    public static boolean listContains(final String contains, final String fileName) {
        if (!Logger.testMode) {
            List<String> list = readList(fileName);
            return list != null && list.contains(contains);
        } else {
            return false;
        }
    }

    /**
     * Writes a hashmap to a file. Deletes the file if it existed previously, returns whether or
     * not the write was successful
     */
    public static boolean writeMap(final Map<String, String> map, final String fileName) {
        if (!Logger.testMode) {
            List<String> list = new ArrayList<>();
            for (String key : map.keySet()) {
                if (key.contains(HASHMAP_DELIMITER) || map.get(key).contains(HASHMAP_DELIMITER)) {
                    Log.w("WRITING HASHMAP",
                            "Hashmap contains the sequence used to separate keys and values."
                                    + " This could cause errors.");
                }
                list.add(key + HASHMAP_DELIMITER + map.get(key));
            }
            return writeList(list, fileName);
        } else {
            return false;
        }
    }

    /**
     * Reads a HashMap from a file, returns null if unsuccessful
     */
    public static HashMap<String, String> readMap(final String fileName) {
        HashMap<String, String> map = new HashMap<>();
        if (!Logger.testMode) {
            List<String> list = readList(fileName);
            if (list == null) {
                return null;
            }
            for (String line : list) {
                String[] split = line.split("\\" + HASHMAP_DELIMITER);
                map.put(split[0], split[1]);
            }
        }
        return map;
    }

    /**
     * Adds a key-value pair to an exsiting map file
     */
    public static boolean putInMap(final String newKey, final String newValue,
                                   final String fileName) {
        if (!Logger.testMode) {
            Map<String, String> map = readMap(fileName);
            if (map != null) {
                map.put(newKey, newValue);
                return writeMap(map, fileName);
            }
        }
        return false;
    }

    /**
     * Removes an element from an existing map
     */
    public static boolean removeFromMap(final String key, final String fileName) {
        if (!Logger.testMode) {
            Map<String, String> map = readMap(fileName);
            if (map != null && map.keySet().contains(key)) {
                map.remove(key);
                return writeMap(map, fileName);
            }
        }
        return false;
    }

    /**
     * Returns whether or not a key is in an existing hashmap file
     */
    public static boolean mapContainsKey(final String key, final String fileName) {
        if (!Logger.testMode) {
            Map<String, String> map = readMap(fileName);
            return map != null && map.keySet().contains(key);
        } else {
            return false;
        }
    }

    /**
     * Returns whether or not a value is in an existing hashmap file
     */
    public static boolean mapContainsValue(final String value, final String fileName) {
        if (!Logger.testMode) {
            Map<String, String> map = readMap(fileName);
            if (map != null) {
                for (String key : map.keySet()) {
                    if (map.get(key).equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Deletes a file from internal storage
     */
    public static boolean deleteFile(final String fileName) {
        if (!Logger.testMode) {
            return Util.getContext().deleteFile(fileName);
        } else {
            return false;
        }
    }

    /**
     * Outputs a file contents to the screen
     */
    public static void printFile(final String fileName) {
        if (!Logger.testMode) {
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
        if (!Logger.testMode) {
            List<String> list = readList(fileName);
            if (list != null) {
                return list.get(0);
            }
        }
        return null;
    }
}
