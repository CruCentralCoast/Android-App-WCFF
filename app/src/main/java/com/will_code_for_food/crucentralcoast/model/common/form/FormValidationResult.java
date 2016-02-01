package com.will_code_for_food.crucentralcoast.model.common.form;

import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;

/**
 * Returned when a form is checked for validity.
 * Indicate the form status.
 */
public enum FormValidationResult {
    VALID (Resources.getSystem().getString(R.string.form_valid)),
    ERROR_INCOMPLETE (Resources.getSystem().getString(R.string.form_incomplete)),
    ERROR_INPUT_TYPE (Resources.getSystem().getString(R.string.form_incorrect_type)),
    ERROR_PAST_DATE (Resources.getSystem().getString(R.string.form_past_date)),
    ERROR_FUTURE_DATE (Resources.getSystem().getString(R.string.form_future_date)),
    ERROR_GENERIC (Resources.getSystem().getString(R.string.form_generic_error)),
    ERROR_CUSTOM ("");

    private String message;

    FormValidationResult(final String message) {
        this.message = message;
    }

    public void setDefaultMessageQuestion(final Question question) {
        this.message = String.format(message, question.getName());
    }

    public void setNewMessage(final String message) {
        if (this == ERROR_CUSTOM) {
            this.message = message;
        }
    }

    public String getMessage() {
        return message;
    }
}
