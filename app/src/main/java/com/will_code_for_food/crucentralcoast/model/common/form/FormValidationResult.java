package com.will_code_for_food.crucentralcoast.model.common.form;

import android.content.Context;
import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * Returned when a form is checked for validity.
 * Indicate the form status.
 */
public enum FormValidationResult {
    VALID (R.string.form_valid),
    ERROR_INCOMPLETE (R.string.form_incomplete),
    ERROR_INPUT_TYPE (R.string.form_incorrect_type),
    ERROR_PAST_DATE (R.string.form_past_date),
    ERROR_FUTURE_DATE (R.string.form_future_date),
    ERROR_GENERIC (R.string.form_generic_error),
    ERROR_CUSTOM (-1);

    private int messageId;
    private Question question = null;

    FormValidationResult(final int messageId) {
        this.messageId = messageId;
    }

    public void setDefaultMessageQuestion(final Question question) {
        this.question = question;
    }

    public void setNewMessage(final int messageId) {
        if (this == ERROR_CUSTOM) {
            this.messageId = messageId;
        }
    }

    public String getMessage(final Context ctxt) {
        String msg = Util.getString(ctxt, messageId);
        if (question != null) {
            msg = String.format(msg, question.getName());
        }
        return msg;
    }
}
