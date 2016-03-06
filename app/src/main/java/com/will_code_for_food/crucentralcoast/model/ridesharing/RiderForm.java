package com.will_code_for_food.crucentralcoast.model.ridesharing;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.PhoneNumberAccessor;
import com.will_code_for_food.crucentralcoast.model.common.common.Location;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Gender;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The form that user's fill out when looking for a ride to an event.
 */
public class RiderForm extends Form {
    protected final String eventId;
    protected final Question nameQuestion;
    protected final Question numberQuestion;
    protected final MultiOptionQuestion genderQuestion;
    protected final Question leaveTimeToEvent;
    protected final MultiOptionQuestion direction;
    protected final Question leaveTimeFromEvent;
    protected final Question location;

    protected Ride createdRide;

    /**
     * Creates a form for riders to fill out to find a ride.
     * Must provide a list of possible locations to leave from
     * for the dropdown.
     */
    public RiderForm(final String eventId) {
        super();
        createdRide = null;
        this.eventId = eventId;
        nameQuestion = new Question(
                Util.getString(R.string.ridesharing_username_question_name),
                Util.getString(R.string.ridesharing_username),
                QuestionType.FREE_RESPONSE_SHORT);
        numberQuestion = new Question(
                Util.getString(R.string.ridesharing_number_question_name),
                Util.getString(R.string.ridesharing_number),
                QuestionType.FREE_RESPONSE_SHORT);
        genderQuestion = new MultiOptionQuestion(
                Util.getString(R.string.ridesharing_choose_gender_question_name),
                Util.getString(R.string.ridesharing_choose_gender),
                Arrays.asList((Object[]) Gender.values()));
        genderQuestion.answerQuestion(Gender.getUsersGender());

        leaveTimeToEvent = new Question(
                Util.getString(R.string.ridesharing_choose_time_question_name),
                Util.getString(R.string.ridesharing_choose_time), QuestionType.TIME_SELECT);
        direction = new MultiOptionQuestion(
                Util.getString(R.string.ridesharing_choose_type_question_name),
                Util.getString(R.string.ridesharing_choose_type),
                Arrays.asList((Object[]) RideDirection.values()));
        leaveTimeFromEvent = new Question(
                Util.getString(R.string.ridesharing_two_way_time_question_name),
                Util.getString(R.string.ridesharing_two_way_time),
                QuestionType.TIME_SELECT);
        direction.addSubquestion(leaveTimeFromEvent);

        location = new Question(
                Util.getString(R.string.ridesharing_location_question_name),
                Util.getString(R.string.ridesharing_location),
                QuestionType.MAP_SELECTION);

        // auto-fill name if possible
        String userName = LocalStorageIO.readSingleLine(LocalFiles.USER_NAME);
        if (userName != null) {
            nameQuestion.answerQuestion(userName);
        }

        addQuestion(nameQuestion);
        addQuestion(numberQuestion);
        addQuestion(genderQuestion);
        addQuestion(leaveTimeToEvent);
        addQuestion(direction);
        addQuestion(location);
    }

    @Override
    public boolean answerQuestion(final int index, final Object answer) {
        boolean ret = super.answerQuestion(index, answer);
        if (!ret) {
            return false;
        }
        Question question = getQuestions().get(index);
        // enables subquestions if two-way is selected
        if (question.getPrompt().equals(Util.getString(
                R.string.ridesharing_two_way))) {
            for (Question sub : question.getSubquestions()) {
                sub.setEnabled((boolean) answer);
                sub.setRequired(true);
            }
        }
        return true;
    }

    public List<FormValidationResult> isValidDetailed() {
        // TODO actually check user input for validity
        return new ArrayList<>();
    }

    public Ride getCreatedRide() {
        return createdRide;
    }

    public boolean submit() {
        if (isFinished()) {
            saveUserInfo();
            return true;
        }
        return false;
    }

    public void saveUserInfo() {
        if (isComplete() && isValid()) {
            // TODO create user using user's actual info (from phone)
            String driverName =  (String) nameQuestion.getAnswer();
            String driverNumber = (String) numberQuestion.getAnswer();
            // save user info
            LocalStorageIO.writeSingleLineFile(LocalFiles.USER_NAME, (String) nameQuestion.getAnswer());
            PhoneNumberAccessor.savePhoneNumberToFile(driverNumber);
            LocalStorageIO.writeSingleLineFile(LocalFiles.USER_GENDER,
                    Util.getString(((Gender) genderQuestion.getAnswer()).getNameId()));
        }
    }
}
