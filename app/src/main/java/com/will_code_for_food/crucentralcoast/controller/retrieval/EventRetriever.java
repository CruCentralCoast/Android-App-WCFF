package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * Created by Gavin on 1/13/2016.
 */
public class EventRetriever extends SingleRetriever {
    @Override
    protected Class getDatabaseObjectClass() {
        return Event.class;
    }

    @Override
    protected String getJSONCollectionString() {
        return Util.getString(R.string.rest_collection_event);
    }

    @Override
    protected String getJSONQueryString() {
        return Util.getString(R.string.rest_query_get_all);
    }
}
