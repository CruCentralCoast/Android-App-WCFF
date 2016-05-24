package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.authentication.LogInForm;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.resources.LeaderArticleCardFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

import java.util.List;

/**
 * Created by Gavin Scott on 2/2/2016.
 */
public class LogInFragment extends CruFragment {

    private Form form;
    private EditText usernameTextField, passwordTextField;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        form = new LogInForm(getParent());
        initComponents(fragmentView);
        return fragmentView;
    }

    private void initComponents(final View fragmentView) {
        usernameTextField = (EditText) fragmentView.findViewById(R.id.login_username);
        passwordTextField = (EditText) fragmentView.findViewById(R.id.login_password);
        loginButton = (Button) fragmentView.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogIn();
            }
        });
    }

    public void attemptLogIn() {
        String username = usernameTextField.getText().toString();
        String password = passwordTextField.getText().toString();

        if (username.trim().length() > 0) {
            form.answerQuestion(0, username);
        }
        if (password.trim().length() > 0) {
            form.answerQuestion(1, password);
        }

        if (form.isFinished() && form.submit()) {
            goToLeaderResources();
        } else {
            List<FormValidationResult> results = form.isFinishedDetailed();
            for (FormValidationResult result : results) {
                // TODO show errors in GUI
                Toast.makeText(getParent(), result.getMessage(getParent()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToLeaderResources() {
        ResourceArticleFragment fragment = new ResourceArticleFragment();
        fragment.setFactory(new LeaderArticleCardFactory());

        dismissKeyboard();
        getParent().loadFragmentWithBackstackOption(R.layout.fragment_card_list, "Resources > LeaderArticles", fragment, getParent(), false);
    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getParent().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameTextField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordTextField.getWindowToken(), 0);
    }
}