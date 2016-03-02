package com.will_code_for_food.crucentralcoast.model.common.form;

import android.content.Context;

import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * Created by Gavin on 2/24/2016.
 */
public class FormValidationResult {
    public final FormValidationResultType type;
    public final Question question;
    public final int questionIndex;

    public FormValidationResult(final FormValidationResultType type, final Question question,
                                final int index) {
        this.type = type;
        this.question = question;
        this.questionIndex = index;
    }

    public void setNewMessage(final int messageId) {
        if (type == FormValidationResultType.ERROR_CUSTOM) {
            type.setMessageId(messageId);
        }
    }

    public String getMessage(final Context ctxt) {
        String msg = Util.getString(ctxt, type.getMessageId());
        if (question != null) {
            msg = String.format(msg, question.getName());
        }
        return msg;
    }
}
