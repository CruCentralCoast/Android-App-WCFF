package com.will_code_for_food.crucentralcoast.view.common;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class FeedCardFactory implements CardFragmentFactory<DatabaseObject> {

    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content<DatabaseObject> cardObjects) {
        return new FeedCardAdapter(MainActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(MainActivity currentActivity, Content<DatabaseObject> myDBObjects) {
        return null;
    }
}
