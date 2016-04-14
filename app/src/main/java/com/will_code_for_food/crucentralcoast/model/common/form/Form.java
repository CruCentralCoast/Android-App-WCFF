package com.will_code_for_food.crucentralcoast.model.common.form;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A form to be filled out by a user (ride-sharing, joining groups, etc.)
 */
public abstract class Form implements Serializable {
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
     * Searches the form for a question whose name matches the given question.
     * Returns the index in the form, or -1 if the form does not contain the question.
     */
    public int getQuestionIndex(final Question question) {
        for (int ndx = 0; ndx < questions.size(); ndx++) {
            if (questions.get(ndx).getName().equals(question.getName())) {
                return ndx;
            }
        }
        return -1;
    }

    /**
     * Answers a question on the form.
     */
    public boolean answerQuestion(final int index, final Object answer) {
        if (index < questions.size()) {
            Logger.i("Answering", index + " : " + questions.get(index).getName() + " = " + answer);
            questions.get(index).answerQuestion(answer);
            return true;
        }
        return false;
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
        return isListValid(isFinishedDetailed());
    }

    /**
     * Gives detailed information on whether or not a
     * form is complete and valid
     */
    public List<FormValidationResult> isFinishedDetailed() {
        List<FormValidationResult> result;
        result = isCompleteDetailed();
        if (isListValid(result)) {
            result = isValidDetailed();
        }
        return result;
    }

    private boolean isListValid(final List<FormValidationResult> list) {
        if (list == null) {
            return false;
        }
        if (list.isEmpty()) {
            return true;
        }
        for (FormValidationResult result : list) {
            if (result.type != FormValidationResultType.VALID) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if every question has been answered
     */
    public boolean isComplete() {
        return isListValid(isCompleteDetailed());
    }

    /**
     * Checks if every question has been answered, returns
     * a form validation result for more information
     */
    public List<FormValidationResult> isCompleteDetailed() {
        List<FormValidationResult> results = new ArrayList<>();
        for (int ndx = 0; ndx < questions.size(); ndx++) {
            if (questions.get(ndx).isRequired() && !questions.get(ndx).isAnswered()) {
                results.add(new FormValidationResult(FormValidationResultType.ERROR_INCOMPLETE, questions.get(ndx), ndx));
            }
        }
        return results;
    }

    /**
     * Returns whether or not all of the questions on the form are valid.
     */
    public boolean isValid() {
        return isListValid(isValidDetailed());
    }

    /**
     * Returns whether or not all of the questions on the form are valid.
     * Returns a form validation result for more information.
     */
    public abstract List<FormValidationResult> isValidDetailed();

    /**
     * Submits the form, returns success/failure
     */
    public abstract boolean submit();

    /**
     * Prints the form to the logger (for debugging)
     */
    public void print() {
        Logger.i("FORM", "Printing form...");
        for (Question question : questions) {
            Logger.i("Quesiton", question.getPrompt() + " -> " + question.getAnswer());
        }
    }

    public void clear() {
        for (Question question : questions) {
            question.clearAnswer();
        }
    }
}
