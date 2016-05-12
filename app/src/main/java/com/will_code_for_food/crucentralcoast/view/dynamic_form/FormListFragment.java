package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.community_groups.CommunityGroupForm;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mallika on 5/11/16.
 */
public class FormListFragment extends CruFragment {

    HashMap<String, CommunityGroupForm> forms;
    ArrayList<String> keyList;

    public FormListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main = super.onCreateView(inflater, container, savedInstanceState);
        ListView list = (ListView) main.findViewById(R.id.list_cards);

        Bundle args = getArguments();
        keyList = args.getStringArrayList("keyList");
        forms = (HashMap<String, CommunityGroupForm>) args.getSerializable("formsMap");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, keyList);
        Log.e("ADAPTER", adapter.toString());
        list.setAdapter(adapter);
        return main;
    }

}
