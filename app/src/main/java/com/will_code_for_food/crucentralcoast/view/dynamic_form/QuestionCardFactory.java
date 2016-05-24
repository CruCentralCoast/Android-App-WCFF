package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.resources.ResourcesActivity;
import com.will_code_for_food.crucentralcoast.view.resources.VideoCardAdapter;

/**
 * Created by mallika on 5/2/16.
 */
public class QuestionCardFactory implements CardFragmentFactory {

    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new VideoCardAdapter(ResourcesActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(
            final MainActivity currentActivity, final Content myDBObjects) {
        return null;
    }

}