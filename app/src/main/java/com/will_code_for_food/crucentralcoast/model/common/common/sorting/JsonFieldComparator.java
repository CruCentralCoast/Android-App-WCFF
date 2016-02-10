package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.Comparator;

/**
 * Compares database objects by whichever field is passed in to the comparator
 */
public class JsonFieldComparator implements Comparator<DatabaseObject> {
    private final String field;

    public JsonFieldComparator(final String field) {
        this.field = field;
    }

    public int compare(DatabaseObject obj1, DatabaseObject obj2) {
        return obj1.getFieldAsString(field).compareTo(obj2.getFieldAsString(field));
    }

}
