package com.will_code_for_food.crucentralcoast.model.common.form;

import java.sql.Time;
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
    TIME_SELECT(Time.class),
    DATE_SELECT(GregorianCalendar.class),
    FREE_RESPONSE_SHORT(String.class),    // text field
    FREE_RESPONSE_LONG(String.class),     // text area
    MAP_SELECTION(String.class);

    private final Class answerType;

    QuestionType(Class answerType) {
        this.answerType = answerType;
    }

    public Class getAnswerType() {
        return answerType;
    }
}
