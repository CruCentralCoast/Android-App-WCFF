package com.will_code_for_food.crucentralcoast.view.common;

import android.os.Bundle;

import com.will_code_for_food.crucentralcoast.R;

/**
 * Created by MasonJStevenson on 4/13/2016.
 */
public class HomeActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFragmentById(R.layout.fragment_card_list, "Home", new FeedFragment(), this);
    }
}
