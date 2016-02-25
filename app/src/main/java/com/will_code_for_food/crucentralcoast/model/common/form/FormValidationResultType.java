package com.will_code_for_food.crucentralcoast.model.common.form;

import com.will_code_for_food.crucentralcoast.R;

/**
 * Returned when a form is checked for validity.
 * Indicate the form status.
 */
public enum FormValidationResultType {
    VALID (R.string.form_valid),
    ERROR_INCOMPLETE (R.string.form_incomplete),
    ERROR_INPUT_TYPE (R.string.form_incorrect_type),
    ERROR_PAST_DATE (R.string.form_past_date),
    ERROR_FUTURE_DATE (R.string.form_future_date),
    ERROR_GENERIC (R.string.form_generic_error),
    ERROR_CUSTOM (-1);

    private int messageId;

    FormValidationResultType(final int messageId) {
        this.messageId = messageId;
    }

    public void setMessageId(final int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
