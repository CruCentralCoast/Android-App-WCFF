package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;

/**
 * Created by mallika on 5/2/16.
 */
public class QuestionCardAdapter extends CardAdapter {

    public QuestionCardAdapter(Context context, Content content) {
        super(context, android.R.layout.simple_list_item_1, content);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
