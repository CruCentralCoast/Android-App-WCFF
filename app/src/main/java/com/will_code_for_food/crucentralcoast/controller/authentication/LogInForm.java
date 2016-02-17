package com.will_code_for_food.crucentralcoast.controller.authentication;

import android.content.Context;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;

/**
 * A simple log-in form that can be used whenever the user has to log in
 */
public class LogInForm extends Form {
    private final Question username;
    private final Question password;

    public LogInForm(final Context ctxt) {
        super();
        username = new Question(
                Util.getString(ctxt, R.string.login_user_question_name),
                Util.getString(ctxt, R.string.login_user),
                QuestionType.FREE_RESPONSE_SHORT);
        password = new Question(
                Util.getString(ctxt, R.string.login_pass_question_name),
                Util.getString(ctxt, R.string.login_pass),
                QuestionType.FREE_RESPONSE_SHORT);
        addQuestion(username);
        addQuestion(password);
    }

    @Override
    public FormValidationResult isValidDetailed() {
        if (Authenticator.logIn((String) username.getAnswer(), (String) password.getAnswer())) {
            return FormValidationResult.VALID;
        } else {
            FormValidationResult result = FormValidationResult.ERROR_CUSTOM;
            result.setNewMessage(R.string.login_invalid_cred);
            return FormValidationResult.ERROR_CUSTOM;
        }
    }
}
