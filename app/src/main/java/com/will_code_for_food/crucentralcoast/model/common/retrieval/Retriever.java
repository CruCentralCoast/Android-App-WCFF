package com.will_code_for_food.crucentralcoast.model.common.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.ArrayList;

/**
 * Created by MasonJStevenson on 1/12/2016.
 */
public interface Retriever {
    public ArrayList<DatabaseObject> getAll();
}
