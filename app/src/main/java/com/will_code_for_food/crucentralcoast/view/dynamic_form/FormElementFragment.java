package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * A specific form element (one question)
 */
public abstract class FormElementFragment extends CruFragment {
    public abstract Object getAnswer();
    public abstract void showError(final FormValidationResult result);
}
