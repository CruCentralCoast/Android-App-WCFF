package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by Gavin on 2/10/2016.
 */
public class Search {

    /**
     * Returns a list containing a subset of database objects that contain the query string in one
     * or more of their JSON fields. Objects whose name contains the query are filtered to the top.
     */
    public static List<DatabaseObject> searchObjectFields(List<DatabaseObject> list, String query) {
        ArrayList<DatabaseObject> searchList = new ArrayList<>();
        for (DatabaseObject obj : list) {
            if (obj.getName().toLowerCase().contains(query.toLowerCase())) {
                searchList.add(obj);
            }
        }
        for (DatabaseObject obj : list) {
            for (Entry entry : obj.getJsonEntrySet()) {
                Log.e("ENTRY VALUE", entry.getKey() + " : " + entry.getValue());
                if (!searchList.contains(obj) &&
                        entry.getValue().toString().toLowerCase().contains(query.toLowerCase())) {
                    searchList.add(obj);
                }
            }
        }
        return searchList;
    }
}
