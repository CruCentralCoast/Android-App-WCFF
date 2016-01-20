package com.will_code_for_food.crucentralcoast;

/**
 * Created by mallika on 12/2/15.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CruFragment extends Fragment {
    private int id;
    public String name;

    public CruFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        //these arguments should have always been passed by using loadFragmentById
        //if not you need to modify stuff
        id = args.getInt("id", R.layout.fragment_main);
        name = args.getString("name", "");
        return inflater.inflate(id, container, false);
    }

}
