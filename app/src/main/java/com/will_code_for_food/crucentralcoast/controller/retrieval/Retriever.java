package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.List;

/**
 * Created by MasonJStevenson on 1/12/2016.
 */
public interface Retriever {
    public List<DatabaseObject> getAll();
}
