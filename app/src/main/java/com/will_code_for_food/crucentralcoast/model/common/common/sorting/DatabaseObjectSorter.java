package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import android.util.Log;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/18/2016.
 *
 */
public class DatabaseObjectSorter {

    public static Content<DatabaseObject> filterByName(Content<DatabaseObject> list, String phrase) {
        List<DatabaseObject> newList = new ArrayList<DatabaseObject>();

        for (DatabaseObject obj : list) {
            if (obj != null && obj.getName() != null && obj.getName().toLowerCase().contains(phrase.toLowerCase())) {
                newList.add(obj);
            }
        }

        return new Content<DatabaseObject>(newList, list.getType());
    }

    public static void sortByDate(List<? extends DatabaseObject> list, SortMethod newMethod) {
        Collections.sort(list, new DateComparator(newMethod));
    }

    public static void sortFeedObjectsByType(List<DatabaseObject> list, SortMethod method) {
        List<DatabaseObject> articles = new ArrayList<DatabaseObject>();
        List<DatabaseObject> events = new ArrayList<DatabaseObject>();
        List<DatabaseObject> videos = new ArrayList<DatabaseObject>();
        List<DatabaseObject> other = new ArrayList<DatabaseObject>();


        for (DatabaseObject obj : list) {
            if (obj instanceof Resource) {
                articles.add(obj);
            } else if (obj instanceof Event) {
                events.add(obj);
            } else if (obj instanceof Video) {
                videos.add(obj);
            } else {
                other.add(obj);
            }
        }

        sortByDate(articles, method);
        sortByDate(events, method);
        sortByDate(videos, method);
        sortByDate(other, method);

        list.clear();
        list.addAll(articles);
        list.addAll(events);
        list.addAll(videos);
        list.addAll(other);
    }
}
