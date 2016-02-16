package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.authentication.LogInForm;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
/**
 * Asks the user for log in information.
 */
public class LogInActivity extends MainActivity {
    Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        form = new LogInForm(context);
        loadFragmentById(R.layout.fragment_login_form,
                Util.getString(context, R.string.login_title),
                new LogInFragment(), this);
    }

    public void attemptLogIn(View view) {
        String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
        if (username.trim().length() > 0) {
            form.answerQuestion(0, username);
        }
        if (password.trim().length() > 0) {
            form.answerQuestion(1, password);
        }

        FormValidationResult result = form.isFinishedDetailed();

        if (result == FormValidationResult.VALID) {
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            Toast.makeText(view.getContext(), result.getMessage(view.getContext()), Toast.LENGTH_SHORT).show();
        }
    }
}
