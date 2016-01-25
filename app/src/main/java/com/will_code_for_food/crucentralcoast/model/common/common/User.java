package com.will_code_for_food.crucentralcoast.model.common.common;

/**
 * Created by Gavin on 11/12/2015.
 */
public interface User {
    String getName();
    String getPhoneNumber();
    boolean notify(final String msg);
}
