package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * Created by Gavin on 1/13/2016.
 */
public class CampusRetriever extends SingleRetriever {

    @Override
    protected Class getDatabaseObjectClass() {
        return Campus.class;
    }

    @Override
    protected String getJSONCollectionString() {
        return Util.getString(R.string.rest_collection_campus);
    }

    @Override
    protected String getJSONQueryString() {
        return Util.getString(R.string.rest_query_get_all);
    }
}
