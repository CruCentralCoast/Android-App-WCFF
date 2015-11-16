package com.will_code_for_food.crucentralcoast.model.common.components;

import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;

import java.util.Collection;

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

    /**
     * Gets ministry teams from the DB API and constructs their corresponding objects.
     * @return
     */
    public Collection<MinistryTeam> getMinistryTeams() {
        return null;
    }
}
