package com.will_code_for_food.crucentralcoast.model.common.form;

import com.will_code_for_food.crucentralcoast.model.common.common.Location;

import java.util.GregorianCalendar;

/**
 * An Enum representing the types of questions that may be asked
 * in a Form. QuestionTypes have a Class variable associated with
 * them that dictates the type of Object allowed as an answer to
 * a question of that type.
 */
public enum QuestionType {
    TRUE_FALSE(Boolean.class),
    MULTI_OPTION_SELECT(Object.class),
    TIME_SELECT(GregorianCalendar.class),
    FREE_RESPONSE_SHORT(String.class),    // text field
    FREE_RESPONSE_LONG(String.class),     // text area
    NUMBER_SELECT(Integer.class),
    MAP_SELECTION(Location.class),
    OTHER(Object.class);

    private final Class answerType;

    QuestionType(Class answerType) {
        this.answerType = answerType;
    }

    public Class getAnswerType() {
        return answerType;
    }
}
