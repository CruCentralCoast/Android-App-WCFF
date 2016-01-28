package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;

import java.util.Arrays;
import java.util.List;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class RiderForm extends Form {
    public final Event event;
    public final Question leaveDayToEvent;
    public final Question leaveTimeToEvent;
    public final MultiOptionQuestion direction;
    public final Question leaveDayFromEvent;
    public final Question leaveTimeFromEvent;
    // TODO probably make locations a map-selection question (doesn't exist yet)
    public final Question locations;

    /**
     * Creates a form for riders to fill out to find a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     *      TODO maybe replace this dropdown with a location selector (Google maps)
     */
    public RiderForm(final Event event, final List<Object> possibleLeaveLocations) {
        super();
        this.event = event;
        leaveDayToEvent = new Question(Resources.getSystem().getString(
                R.string.ridesharing_choose_day),
                QuestionType.DATE_SELECT);
        leaveTimeToEvent = new Question(Resources.getSystem().getString(
                R.string.ridesharing_choose_time), QuestionType.TIME_SELECT);
        direction = new MultiOptionQuestion("",
                Arrays.asList(new Object[]{
                        Resources.getSystem().getString(R.string.ridesharing_one_way_to_event),
                        Resources.getSystem().getString(R.string.ridesharing_one_way_from_event),
                        Resources.getSystem().getString(R.string.ridesharing_two_way)
                }));
        leaveDayFromEvent = new Question(Resources.getSystem().getString(
                R.string.ridesharing_two_way_date), QuestionType.DATE_SELECT);
        leaveTimeFromEvent = new Question(Resources.getSystem().getString(
                R.string.ridesharing_two_way_time), QuestionType.TIME_SELECT);
        direction.addSubquestion(leaveDayFromEvent);
        direction.addSubquestion(leaveDayFromEvent);

        locations = new MultiOptionQuestion(Resources.getSystem().getString(
                R.string.ridesharing_location), possibleLeaveLocations);

        addQuestion(leaveDayFromEvent);
        addQuestion(leaveTimeFromEvent);
        addQuestion(direction);
        addQuestion(locations);
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
        // TODO actually check user input for validity
        return true;
    }
}
