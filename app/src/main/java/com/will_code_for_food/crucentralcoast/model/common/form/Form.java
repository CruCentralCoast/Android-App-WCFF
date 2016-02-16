package com.will_code_for_food.crucentralcoast.model.common.form;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A form to be filled out by a user (ride-sharing, joining groups, etc.)
 */
public abstract class Form {
    private List<Question> questions;

    /**
     * Creates a form.
     */
    public Form() {
        questions = new ArrayList<>();
    }

    public void addQuestion(final Question question) {
        questions.add(question);
    }

    /**
     * Answers a question on the form.
     */
    public void answerQuestion(final int index, final Object answer) {
        questions.get(index).answerQuestion(answer);
    }

    public Question getQuestion(final int index) {
        return questions.get(index);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Checks if a form is complete and valid
     */
    public boolean isFinished() {
        return isFinishedDetailed() == FormValidationResult.VALID;
    }

    /**
     * Gives detailed information on whether or not a
     * form is complete and valid
     */
    public FormValidationResult isFinishedDetailed() {
        FormValidationResult result;
        result = isCompleteDetailed();
        if (result == FormValidationResult.VALID) {
            result = isValidDetailed();
        }
        return result;
    }

    /**
     * Checks if every question has been answered
     */
    public boolean isComplete() {
        return isCompleteDetailed() == FormValidationResult.VALID;
    }

    /**
     * Checks if every question has been answered, returns
     * a form validation result for more information
     */
    public FormValidationResult isCompleteDetailed() {
        FormValidationResult result = FormValidationResult.VALID;
        for (Question question : questions) {
            if (question.isRequired() && !question.isAnswered()) {
                result = FormValidationResult.ERROR_INCOMPLETE;
                result.setDefaultMessageQuestion(question);
                return result;
            }
        }
        return result;
    }

    /**
     * Returns whether or not all of the questions on the form are valid.
     */
    public boolean isValid() {
        return isValidDetailed() == FormValidationResult.VALID;
    }

    /**
     * Returns whether or not all of the questions on the form are valid.
     * Returns a form validation result for more information.
     */
    public abstract FormValidationResult isValidDetailed();
}
