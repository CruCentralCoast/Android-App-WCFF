package com.will_code_for_food.crucentralcoast.model.ridesharing;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;

import junit.framework.Assert;

import org.junit.Test;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Tests rider form
 */
public class RiderFormTest extends WCFFUnitTest {
    // TODO Unit tests aren't mocking the resources file so this can't be run
//    @Test
//    public void testForm() {
//        newTest("Testing the rider form");
//        logStep("New Form");
//        RiderForm form = new RiderForm(Arrays.asList(new Object[]{
//                "PAC Circle", "CRU HQ", "Other"
//        }));
//        Assert.assertTrue(form.isValid());
//        Assert.assertFalse(form.isComplete());
//        Assert.assertEquals(4, form.getQuestions().size());
//
//        logStep("Answering questions (one-way)");
//        form.answerQuestion(0, Calendar.getInstance());
//        form.answerQuestion(1, new Time(0));
//        ((MultiOptionQuestion)form.getQuestion(2)).answerQuestionByIndex(0);
//        form.answerQuestion(3, "PAC Circle");
//        Assert.assertTrue(form.isComplete());
//
//        logStep("Answering questions (two-way)");
//        ((MultiOptionQuestion)form.getQuestion(2)).answerQuestionByIndex(2);
//        Assert.assertFalse(form.isComplete());
//
//        logStep("Answering two-way subquestions");
//        ((MultiOptionQuestion)form.getQuestion(2)).getSubquestions().get(0)
//                .answerQuestion(Calendar.getInstance());
//        ((MultiOptionQuestion)form.getQuestion(2)).getSubquestions().get(1)
//                .answerQuestion(new Time(0));
//        Assert.assertTrue(form.isComplete());
//    }
}
