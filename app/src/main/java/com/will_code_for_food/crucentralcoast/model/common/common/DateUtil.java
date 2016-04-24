package com.will_code_for_food.crucentralcoast.model.common.common;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MasonJStevenson on 4/24/2016.
 */
public class DateUtil {

    /**
     * Gets the correct date field depending on which object is being used.
     */
    public static Date getDate(DatabaseObject obj) {

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
    public static Date parseISO(String dateString) {
        Date date;

        // Convert ISODate to Java Date format
        try {
            DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            Logger.i("DatabaseObjectSorter", "parsed a null ISO date: " + dateString);
            return null;
        }

        return date;
    }
}
