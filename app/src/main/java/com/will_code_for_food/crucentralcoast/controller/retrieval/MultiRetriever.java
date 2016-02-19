package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.DatabaseObjectSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Allows for the combination of multiple retrievers into a single
 * retriever instance. The order of fetched elements is determined
 * by the order of the provided retrievers. The default constructor
 * creates a MultiRetriever with no retriever, but the list of
 * retrievers can be changed after instantiation.
 */
public final class MultiRetriever implements Retriever {
    private List<Retriever> retrievers;

    public MultiRetriever(List<Retriever> retrievers) {
        this.retrievers = retrievers;
    }

    public MultiRetriever(Retriever... retrievers) {
        this.retrievers = Arrays.asList(retrievers);
    }

    public void setRetrievers(List<Retriever> retrievers) {
        this.retrievers = retrievers;
    }

    public void setRetrievers(Retriever... retrievers) {
        this.retrievers = Arrays.asList(retrievers);
    }

    @Override
    public Content<? extends DatabaseObject> getAll() {
        List<? extends DatabaseObject> objects = new ArrayList();
        boolean containsCached = false;
        boolean containsTest = false;
        Content content;

        for (Retriever ret : retrievers) {
            if (ret != null) {
                content = ret.getAll();
                //Log.i("MultiRetriever", "content size: " + content.size());
                         objects.addAll(content.getObjects());

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
