package com.will_code_for_food.crucentralcoast.model.ridesharing;

import android.content.res.Resources;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import java.util.Arrays;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class RiderForm extends Form {
    protected final String eventId;
    protected final Question nameQuestion;
    protected final Question leaveDayToEvent;
    protected final Question leaveTimeToEvent;
    protected final MultiOptionQuestion direction;
    protected final Question leaveDayFromEvent;
    protected final Question leaveTimeFromEvent;
    protected final Question locations;

    /**
     * Creates a form for riders to fill out to find a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     */
    public RiderForm(final String eventId) {
        super();
        this.eventId = eventId;
        nameQuestion = new Question(
                Util.getString(R.string.ridesharing_username),
                Util.getString(R.string.ridesharing_username_question_name),
                QuestionType.FREE_RESPONSE_SHORT);
        leaveDayToEvent = new Question(
                Util.getString(R.string.ridesharing_choose_day_question_name),
                Util.getString(R.string.ridesharing_choose_day),
                QuestionType.DATE_SELECT);
        leaveTimeToEvent = new Question(
                Util.getString(R.string.ridesharing_choose_time_question_name),
                Util.getString(R.string.ridesharing_choose_time), QuestionType.TIME_SELECT);
        direction = new MultiOptionQuestion(
                Util.getString(R.string.ridesharing_choose_type_question_name),
                Util.getString(R.string.ridesharing_choose_type),
                Arrays.asList(new Object[]{
                        Util.getString(R.string.ridesharing_one_way_to_event),
                        Util.getString(R.string.ridesharing_one_way_from_event),
                        Util.getString(R.string.ridesharing_two_way)
                }));
        leaveDayFromEvent = new Question(
                Util.getString(R.string.ridesharing_two_way_date_question_name),
                Util.getString(R.string.ridesharing_two_way_date),
                QuestionType.DATE_SELECT);
        leaveTimeFromEvent = new Question(
                Util.getString(R.string.ridesharing_two_way_time_question_name),
                Util.getString(R.string.ridesharing_two_way_time),
                QuestionType.TIME_SELECT);
        direction.addSubquestion(leaveDayFromEvent);
        direction.addSubquestion(leaveDayFromEvent);

        locations = new Question(
                Util.getString(R.string.ridesharing_location_question_name),
                Util.getString(R.string.ridesharing_location),
                QuestionType.MAP_SELECTION);

        // auto-fill name if possible
        String userName = LocalStorageIO.readSingleLine(LocalFiles.USER_NAME);
        if (userName != null) {
            nameQuestion.answerQuestion(userName);
            Log.e("GAVIN", "Autofilling username: " + userName);
        } else {
            Log.e("GAVIN", "Could not find username");
        }

        addQuestion(nameQuestion);
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
        if (question.getPrompt().equals(Util.getString(

                R.string.ridesharing_two_way))) {
            for (Question sub : question.getSubquestions()) {
                sub.setEnabled((boolean) answer == true);
                sub.setRequired(true);
            }
        }
    }

    public FormValidationResult isValidDetailed() {
        // TODO actually check user input for validity
        return FormValidationResult.VALID;
    }

}
