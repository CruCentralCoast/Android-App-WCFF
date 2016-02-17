package com.will_code_for_food.crucentralcoast.controller.authentication;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import java.util.HashMap;

/**
 * Stores a user's credentials (usernames are not case sensitive)
 */
public class Credentials {
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    public final String username;
    public final String password;

    /**
     * Creates credentials from given strings
     */
    public Credentials(final String username, final String password) {
        this.username = username.toLowerCase().trim();
        this.password = password.trim();
    }

    /**
     * Attempts to get user credentials from the local file. Username and password are set to null
     * if the credentials have not been saved or there was an error.
     */
    public Credentials() {
        HashMap<String, String> map = LocalStorageIO.readMap(LocalFiles.CREDENTIALS);
        if (map != null && map.containsKey(USERNAME_KEY)
                && map.containsKey(PASSWORD_KEY)) {
            username = map.get(USERNAME_KEY).toLowerCase();
            password = map.get(PASSWORD_KEY);
        } else {
            username = null;
            password = null;
        }
    }

    public void save() {
        // save log in information to the phone
        HashMap<String, String> map = new HashMap<>();
        map.put(USERNAME_KEY, username);
        map.put(PASSWORD_KEY, password);
        LocalStorageIO.writeMap(map, LocalFiles.CREDENTIALS);
    }
}
