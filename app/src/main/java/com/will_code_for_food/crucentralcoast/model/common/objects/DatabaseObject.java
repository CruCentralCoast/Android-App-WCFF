package com.will_code_for_food.crucentralcoast.model.common.objects;

/**
 * Created by Gavin on 11/12/2015.
 */
public abstract class DatabaseObject {
    public abstract String getId();
    public boolean update() {
        // TODO handles updating the object in the database
        return false;
    }
    public boolean delete() {
        // TODO deletes the object from the database
        return false;
    }
}
