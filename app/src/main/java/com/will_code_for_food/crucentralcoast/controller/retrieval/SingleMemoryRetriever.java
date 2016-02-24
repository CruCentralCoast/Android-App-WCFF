package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;

import java.util.List;

/**
 * Created by MasonJStevenson on 2/22/2016.
 *
 * Retrieves objects from the DBObjectLoader instead of RestUtil
 */
public class SingleMemoryRetriever implements Retriever {

    String key;

    public SingleMemoryRetriever(String key) {
        this.key = key;
    }

    @Override
    public Content getAll() {
        return DBObjectLoader.get(key);
    }
}
