package com.will_code_for_food.crucentralcoast.view.resources;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by Brian on 2/16/2016.
 */
public class ArticleCardFactory implements CardFragmentFactory {
    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return null;
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(MainActivity currentActivity, Content myDBObjects) {
        return null;
    }
}
