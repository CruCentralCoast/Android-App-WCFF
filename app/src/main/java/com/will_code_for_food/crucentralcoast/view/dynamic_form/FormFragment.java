package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A form that can be dynamically build from a Form object
 */
public class FormFragment extends CruFragment {
    private List<FormElementFragment> fragments;
    private Form form;

    // TODO may need ui methods (onCResume, etc)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle args = getArguments();
        // TODO the form needs to be passed in as an argument
        this.form = (Form) args.getSerializable("form");
        loadForm(form);
        return view;
    }

    public void loadForm(final Form form) {
        fragments = new ArrayList<>();
        if (form != null) {
            for (Question question : form.getQuestions()) {
                switch (question.getType()) {
                    case FREE_RESPONSE_SHORT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case FREE_RESPONSE_LONG:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case MAP_SELECTION:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case MULTI_OPTION_SELECT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case TIME_SELECT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case TRUE_FALSE:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case NUMBER_SELECT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    default:
                        Log.e("DYNAMIC FORM", "Invalid Question Type");
                }
            }
        } else {
            Log.e("DYNAMIC FORM", "Form should not be null");
        }
    }

    // TODO this needs to be linked to the submit button of the form
    public void pressSubmmitButton() {
        if (form != null) {
            form.clear();
            for (int ndx = 0; ndx < fragments.size(); ndx++) {
                form.answerQuestion(ndx, fragments.get(ndx).getAnswer());
            }
            if (form.isFinished()) {
                // form done, submitting
                form.submit();
            } else {
                List<FormValidationResult> results = form.isFinishedDetailed();
                for (FormValidationResult result : results) {
                    fragments.get(result.questionIndex).showError(result);
                }
            }
        } else {
            Log.e("DYNAMIC FORM", "Form should not be null");
        }
    }
}
