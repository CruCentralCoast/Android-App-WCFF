package com.will_code_for_food.crucentralcoast.controller.authentication;

/**
 * Logs in users and checks whether or not the user is logged in
 */
public class Authenticator {
    private static boolean loggedIn = false;

    /**
     * Checks if the user is currently logged in
     */
    public static boolean isUserLoggedIn() {
        return loggedIn;
    }

    /**
     * Logs out the user
     */
    public static void logOut() {
        loggedIn = false;
    }

    /**
     * Logs in a user using the provided user credentials.
     * Returns true if the log-in was successful, and saves the log-in information to the phone.
     */
    public static boolean logIn(final Credentials credentials) {
        if (credentials.username != null && credentials.password != null){
            final String username = credentials.username;
            final String password = credentials.password;

            // TODO perform check in database (not implemented yet)
            loggedIn = username.equals("user") && password.equals("1234");

            if (loggedIn) {
                credentials.save();
            }
            return loggedIn;
        }
        return false;
    }

    /**
     * Logs in a user using the provided username and password.
     * Returns true if the log-in was successful, and saves the log-in information to the phone.
     */
    public static boolean logIn(final String username, final String password) {
        return logIn(new Credentials(username, password));
    }

    /**
     * Logs in using the credentials stored on the phone. Returns false if the credentials are
     * incorrect or have not been saved to the phone.
     */
    public static boolean logIn() {
        return logIn(new Credentials());
    }
}
