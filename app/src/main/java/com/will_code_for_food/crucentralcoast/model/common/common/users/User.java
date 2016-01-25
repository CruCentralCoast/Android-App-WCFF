package com.will_code_for_food.crucentralcoast.model.common.common.users;

/**
 * Created by Gavin on 11/12/2015.
 */
public class User {
    private final String name;
    private final String number;

    public User(final String name, final String number) {
        this.name = name;
        this.number= number;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return number;
    }

    public boolean notify(final String msg) {
        // TODO send a text to the user, containing the message
        return true;
    }
}
