package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import android.util.Log;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/18/2016.
 *
 * BROKEN, currently
 */
public class DatabaseObjectSorter {

    public static List<? extends DatabaseObject> sortByDate(final List<? extends DatabaseObject> list) {
        Collections.sort(list, new DateComparator());
        return list;
    }

    private static class DateComparator implements Comparator<DatabaseObject> {

        public int compare(DatabaseObject obj1, DatabaseObject obj2) {
            Date date1 = getDate(obj1);
            Date date2 = getDate(obj2);

            if (date1 == null && date2 == null) {
                return 0;
            } else if (date1 == null && date2 != null) {
                return 1;
            } else if (date1 != null && date2 == null) {
                return -1;
            } else {
                return date1.compareTo(date2) * -1;
            }
        }

        private Date getDate(DatabaseObject obj) {

            //TODO: sort events by the date the event was added, not date the event occurs
            if (obj instanceof Video) {
                return parseISO(((Video) obj).getPublishDate());
            } else if (obj instanceof Resource) {
                return parseISO(((Resource) obj).getDate());
            } else if (obj instanceof Event) {
                return parseISO(((Event) obj).getFieldAsString(Database.JSON_KEY_EVENT_STARTDATE));
            } else {
                return null;
            }
        }

        private Date parseISO(String dateString) {
            Date date;

            // Convert ISODate to Java Date format
            try {
                DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                Log.i("DatabaseObjectSorter", "parsed a null ISO date: " + dateString);
                return null;
            }

            return date;
        }

        private Date parseYouTube(String dateString) {
            Date date;

            // Convert ISODate to Java Date format
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                Log.i("DatabaseObjectSorter", "parsed a null Youtube date: " + dateString);
                return null;
            }

            return date;
        }
    }
}
