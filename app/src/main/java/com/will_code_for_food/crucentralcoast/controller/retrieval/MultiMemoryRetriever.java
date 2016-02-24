package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.DatabaseObjectSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/21/2016.
 */
public class MultiMemoryRetriever implements Retriever {

    List<String> keys;

    public MultiMemoryRetriever(List<String> keys) {
        this.keys = keys;
    }

    @Override
    public Content getAll() {
        List<? extends DatabaseObject> objects = new ArrayList();
        boolean containsCached = false;
        boolean containsTest = false;
        Content content;

        for (String key : keys) {
            if (key != null) {
                content = DBObjectLoader.get(key);
                //Log.i("MultiRetriever", "content size: " + content.size());
                objects.addAll(content);

                if (content.getType() == ContentType.CACHED) {
                    containsCached = true;
                } else if (content.getType() == ContentType.TEST) {
                    containsTest = true;
                }
            }
        }

        //Broken, currently
        objects = DatabaseObjectSorter.sortByDate(objects);

        if (containsCached) {
            return new Content(objects, ContentType.CACHED); //may contain some live content
        } else if (containsTest){
            return new Content(objects, ContentType.TEST); //may contain some live content
        } else {
            //Log.i("MultiRetriever", "Multi retriever got " + objects.size() + " objects");
            return new Content(objects, ContentType.LIVE); //only returns LIVE if there is no cached or test content
        }
    }
}
