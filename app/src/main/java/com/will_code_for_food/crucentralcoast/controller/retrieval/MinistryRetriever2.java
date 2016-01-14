package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gavin on 1/13/2016.
 */
public class MinistryRetriever2 extends SingleRetriever {

    @Override
    protected Class getDatabaseObjectClass() {
        return Ministry.class;
    }

    @Override
    protected String getJSONDatabaseString() {
        return Util.getString(R.string.rest_ministry_all);
    }
}
