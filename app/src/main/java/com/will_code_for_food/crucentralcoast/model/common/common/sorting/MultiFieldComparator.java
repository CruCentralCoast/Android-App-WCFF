package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.Comparator;
import java.util.List;

/**
 * Compares two database objects by comparing multiple fields (in order) and returning the comparison
 * as soon as a field's values in each object are not equals.
 *
 * Ex: sort by name, then time, then something else...
 */
public class MultiFieldComparator implements Comparator<DatabaseObject> {
    private final List<String> fields;

    public MultiFieldComparator(final List<String> fields) {
        this.fields = fields;
    }

    public int compare(DatabaseObject obj1, DatabaseObject obj2) {
        int comp = 0;
        for (String field : fields) {
            comp =  obj1.getFieldAsString(field).compareTo(obj2.getFieldAsString(field));
            if (comp != 0) {
                return comp;
            }
        }
        return comp;
    }
}
