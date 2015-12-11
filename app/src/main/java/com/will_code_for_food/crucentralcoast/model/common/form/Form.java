package com.will_code_for_food.crucentralcoast.model.common.form;

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
        questions = new ArrayList<Question>();
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
     * Checks if every question has been answered
     */
    public boolean isComplete() {
        for (Question question : questions) {
            if (!question.isAnswered()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not all of the questions on the form are valid.
     */
    public abstract boolean isValid();
}
