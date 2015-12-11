package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class RiderForm extends Form {

    /**
     * Creates a form for riders to fill out to find a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     *      TODO maybe replace this dropdown with a location selector (Google maps)
     */
    public RiderForm(final List<Object> possibleLeaveLocations) {
        super();
        addQuestion(new Question(Resources.getSystem().getString(
                R.string.ridesharing_choose_day), QuestionType.DATE_SELECT));
        addQuestion(new Question(Resources.getSystem().getString(
                R.string.ridesharing_choose_time), QuestionType.TIME_SELECT));
        Question twoWay = new MultiOptionQuestion("",
                Arrays.asList(new Object[]{
                        Resources.getSystem().getString(R.string.ridesharing_one_way_to_event),
                        Resources.getSystem().getString(R.string.ridesharing_one_way_from_event),
                        Resources.getSystem().getString(R.string.ridesharing_two_way)
                }));
        twoWay.addSubquestion(new Question(Resources.getSystem().getString(
                R.string.ridesharing_two_way_date), QuestionType.DATE_SELECT));
        twoWay.addSubquestion(new Question(Resources.getSystem().getString(
                R.string.ridesharing_two_way_time), QuestionType.TIME_SELECT));
        addQuestion(twoWay);
        addQuestion(new MultiOptionQuestion(Resources.getSystem().getString(
                R.string.ridesharing_location), possibleLeaveLocations));
    }

    @Override
    public void answerQuestion(final int index, final Object answer) {
        super.answerQuestion(index, answer);
        Question question = getQuestions().get(index);
        // enables subquestions if two-way is selected
        if (question.getPrompt().equals(Resources.getSystem().getString(
                R.string.ridesharing_two_way))) {
            for (Question sub : question.getSubquestions()) {
                sub.setEnabled((boolean) answer == true);
            }
        }
    }

    public boolean isValid() {
        return true;
    }
}
