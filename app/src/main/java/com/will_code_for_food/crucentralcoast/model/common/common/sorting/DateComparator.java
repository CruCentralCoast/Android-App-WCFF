package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
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
        Date date1 = getDate(obj1);
        Date date2 = getDate(obj2);

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

    /**
     * Gets the correct date field depending on which object is being used.
     */
    private Date getDate(DatabaseObject obj) {

        //TODO: update DatabaseObject with a GetDate() method, so this class supports all types of DatabaseObjects
        //TODO: sort events by the date the event was added, not date the event occurs
        if (obj instanceof Video) {
            return parseISO(((Video) obj).getPublishDate());
        } else if (obj instanceof Resource) {
            return parseISO(((Resource) obj).getDate());
        } else if (obj instanceof Event) {
            return parseISO(((Event) obj).getFieldAsString(Database.JSON_KEY_EVENT_STARTDATE));
        } else if (obj instanceof SummerMission) {
            return parseISO(((SummerMission) obj).getFieldAsString(Database.JSON_KEY_EVENT_STARTDATE));
        } else {
            return null;
        }
    }

    /**
     * Parses the date from ISO string (Example: "2015-10-13T05:00:00.000Z") format to Date format.
     */
    private Date parseISO(String dateString) {
        Date date;

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            Log.i("DatabaseObjectSorter", "parsed a null ISO date: " + dateString);
            return null;
        }

        return date;
    }
}