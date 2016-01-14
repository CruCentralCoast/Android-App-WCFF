package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.List;

/**
 * Created by Gavin on 1/13/2016.
 */
public abstract class Retriever {
    public abstract List<DatabaseObject> getAll();
}
