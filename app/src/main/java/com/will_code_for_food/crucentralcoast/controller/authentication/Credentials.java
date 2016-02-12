package com.will_code_for_food.crucentralcoast.controller.authentication;

/**
 * Stores a user's credentials (usernames are not case sensitive)
 */
public class Credentials {
    public final String username;
    public final String password;

    public Credentials(final String username, final String password) {
        this.username = username.toLowerCase();
        this.password = password;
    }
}
