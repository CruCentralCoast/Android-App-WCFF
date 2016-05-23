package com.will_code_for_food.crucentralcoast.model.common.form;

import java.util.ArrayList;
import java.util.List;

/**
 * A question that may be inserted into a form.
 */
public class Question {
    private final String prompt;            // prompt displayed to user
    private final String name;              // name of the question
    private final QuestionType type;

    private Object answer;
    private boolean enabled;
    private boolean required;
    private List<Question> subquestions;

    private int index;

    public Question(final String name, final String prompt, final QuestionType type) {
        this.name = name;
        this.prompt = prompt;
        this.type = type;
        this.answer = null;
        this.enabled = true;
        this.subquestions = new ArrayList<>();
        this.required = true;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getName() {
        return name;
    }

    public QuestionType getType() {
        return type;
    }

    public Class getAnswerType() {
        return type.getAnswerType();
    }

    public void clearAnswer() {
        answerQuestion(null);
    }

    /**
     * Checks if the answer is not null, and if all enabled
     * subquestions have also been answered.
     */
    public boolean isAnswered() {
        if (getAnswer() == null) {
            return false;
        }
        for (Question sub : subquestions) {
            if (sub.isEnabled() && !sub.isAnswered()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the answer to the question. Returns true if the provided answer
     * was either null (for no answer) or the correct type for that type of question.
     */
    public boolean answerQuestion(final Object answer) {
        if (isEnabled() && (answer == null || type.getAnswerType().isInstance(answer))) {
            this.answer = answer;
            return true;
        }
        return false;
    }

    public Object getAnswer() {
        return answer;
    }

    /**
     * Adds a subquestion to this question. These are disabled
     * automatically.
     */
    public void addSubquestion(final Question subquestion) {
        subquestion.setEnabled(false);
        subquestion.setRequired(false);
        subquestions.add(subquestion);
    }

    public List<Question> getSubquestions() {
        return subquestions;
    }
}
