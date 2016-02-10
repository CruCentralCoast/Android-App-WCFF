package com.will_code_for_food.crucentralcoast.model.common.form;

import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * Returned when a form is checked for validity.
 * Indicate the form status.
 */
public enum FormValidationResult {
    VALID (Util.getString(R.string.form_valid)),
    ERROR_INCOMPLETE (Util.getString(R.string.form_incomplete)),
    ERROR_INPUT_TYPE (Util.getString(R.string.form_incorrect_type)),
    ERROR_PAST_DATE (Util.getString(R.string.form_past_date)),
    ERROR_FUTURE_DATE (Util.getString(R.string.form_future_date)),
    ERROR_GENERIC (Util.getString(R.string.form_generic_error)),
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
