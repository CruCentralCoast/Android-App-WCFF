package com.will_code_for_food.crucentralcoast.view.other;

import android.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.List;

/**
 * Created by Brian on 1/24/2016.
 */
public interface CardFragmentFactory <T extends DatabaseObject> {
    public boolean include(DatabaseObject object);
    public ArrayAdapter createAdapter(List<T> cardObjects);
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final List<? extends DatabaseObject> myDBObjects);
}
