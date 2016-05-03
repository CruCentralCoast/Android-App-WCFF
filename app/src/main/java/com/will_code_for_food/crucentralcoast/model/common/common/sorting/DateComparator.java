package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.DateUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by MasonJStevenson on 3/9/2016.
 *
 * Compares dates for DatabaseObjects in the feed
 */
public class DateComparator implements Comparator<DatabaseObject> {

    private SortMethod method;

    public DateComparator(SortMethod method) {
        this.method = method;
    }

    /**
     * Compares two Database Objects. Currently supports DBObjects of type Video, Resource, and Event.
     * A null date is always considered "less than" a non-null date.
     */
    public int compare(DatabaseObject obj1, DatabaseObject obj2) {
        Date date1 = DateUtil.getDate(obj1);
        Date date2 = DateUtil.getDate(obj2);

        if (date1 == null && date2 == null) {
            return 0;
        } else if (date1 == null && date2 != null) {
            return method == SortMethod.DESCENDING ? 1 : -1;
        } else if (date1 != null && date2 == null) {
            return method == SortMethod.DESCENDING ? -1 : 1;
        } else {
            return method == SortMethod.DESCENDING ? (date1.compareTo(date2) * -1) : date1.compareTo(date2);
        }
    }
}