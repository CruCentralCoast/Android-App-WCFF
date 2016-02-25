package com.will_code_for_food.crucentralcoast.model.common.form;

import java.util.List;

/**
 * A subclass of Question representing a question where the user
 * selects exactly one option from a provided list.
 * Examples: dropdown, group of checkboxes, etc.
 */
public class MultiOptionQuestion extends Question {
    private List<Object> options;
    private Object selection;

    public MultiOptionQuestion(final String name, final String prompt, final List<Object> options) {
        super(name, prompt, QuestionType.MULTI_OPTION_SELECT);
        this.options = options;
    }

    @Override
    public boolean answerQuestion(final Object answer) {
        if (options.contains(answer) || answer == null) {
            selection = answer;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getAnswer() {
        return selection;
    }

    public void answerQuestionByIndex(final int index) {
        answerQuestion(options.get(index));
    }

    public List<Object> getOptions() {
        return options;
    }
}
