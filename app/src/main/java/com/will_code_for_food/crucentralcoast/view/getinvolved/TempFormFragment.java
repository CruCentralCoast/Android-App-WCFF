package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by MasonJStevenson on 3/2/2016.
 *
 * TODO: replace me with FormFragment
 */
public class TempFormFragment extends CruFragment{

    ScrollView form;
    LinearLayout container;
    Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        initComponents(fragmentView);

        return fragmentView;
    }

    private void initComponents(View fragmentView) {
        form = (ScrollView) fragmentView.findViewById(R.id.scrollview_form);

        container = new LinearLayout(getParent());
        container.setOrientation(LinearLayout.VERTICAL);
        container.setId(R.id.form_container);

        submitButton = new Button(getParent());
        submitButton.setId(R.id.form_submit_button);
        submitButton.setText("Submit");
        submitButton.setGravity(Gravity.CENTER);

        loadQuestions();
        container.addView(submitButton); //not sure why the button is still at the top of the view...

        form.addView(container);
    }

    //normally this would be dynamic
    private void loadQuestions() {
        getFragmentManager().beginTransaction().add(container.getId(), new TestQuestion1Fragment(), "q1").commit();
        getFragmentManager().beginTransaction().add(container.getId(), new TestQuestion1Fragment(), "q2").commit();
        getFragmentManager().beginTransaction().add(container.getId(), new TestQuestion1Fragment(), "q3").commit();
        getFragmentManager().beginTransaction().add(container.getId(), new TestQuestion1Fragment(), "q4").commit();
        getFragmentManager().beginTransaction().add(container.getId(), new TestQuestion1Fragment(), "q5").commit();
    }
}
