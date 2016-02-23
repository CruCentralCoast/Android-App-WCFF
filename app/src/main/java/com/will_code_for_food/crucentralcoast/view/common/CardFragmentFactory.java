package com.will_code_for_food.crucentralcoast.view.common;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.List;

/**
 * Created by Brian on 1/24/2016.
 */
public interface CardFragmentFactory <T extends DatabaseObject> {
    boolean include(T object);
    ArrayAdapter createAdapter(Content<T> cardObjects);
    AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final Content<T> myDBObjects);
}
