package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

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
public final class MultiRetriever extends Retriever2 {
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

    public List<DatabaseObject> getAll() {
        ArrayList<DatabaseObject> objects = new ArrayList<DatabaseObject>();
        for (Retriever ret : retrievers) {
            if (ret != null) {
                objects.addAll(ret.getAll());
            }
        }
        return objects;
    }
}
