package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by MasonJStevenson on 3/2/2016.
 */
public class GetInvolvedFragment extends CruFragment {

    Button testFormGeneration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        initComponents(fragmentView);

        return fragmentView;
    }

    private void initComponents(View fragmentView) {
        testFormGeneration = (Button) fragmentView.findViewById(R.id.button_form_test);

        testFormGeneration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent().loadFragmentById(R.layout.fragment_form_container, "Form Test", new TempFormFragment(), getParent());
            }
        });
    }
}
