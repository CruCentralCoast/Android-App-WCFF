package com.will_code_for_food.crucentralcoast.model.common;

import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.model.common.form.MultiOptionQuestion;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;

import junit.framework.Assert;

import org.junit.Test;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Tests the Question class
 */
public class QuestionTest extends WCFFUnitTest {
    private final String prompt = "prompt";
    private final String name = "name";

    @Test
    public void testAllQuestionTypes() {
        testSingleQuestionType(QuestionType.TRUE_FALSE, false);
        testSingleQuestionType(QuestionType.TIME_SELECT, new Time(0));
        //testSingleQuestionType(QuestionType.DATE_SELECT, Calendar.getInstance());
        testSingleQuestionType(QuestionType.FREE_RESPONSE_SHORT, "");
        testSingleQuestionType(QuestionType.FREE_RESPONSE_LONG, "");
    }

    private void testSingleQuestionType(QuestionType type, Object valid) {
        Assert.assertFalse( "Valid answer cannot be null (for this test)", valid == null);
        Question question = new Question(name, prompt, type);
        Assert.assertEquals(name, question.getName());
        Assert.assertEquals(prompt, question.getPrompt());
        Assert.assertEquals(null, question.getAnswer());
        Assert.assertEquals(type.getAnswerType(), question.getAnswerType());
        Assert.assertTrue(question.getSubquestions().isEmpty());
        Assert.assertFalse(question.isAnswered());

        Assert.assertFalse(question.answerQuestion(new Garbage()));
        Assert.assertEquals(null, question.getAnswer());

        question.answerQuestion(valid);
        Assert.assertEquals(valid, question.getAnswer());
        Assert.assertEquals(question.getAnswer().getClass(), type.getAnswerType());
        Assert.assertTrue(question.isAnswered());

        question.clearAnswer();
        Assert.assertEquals(null, question.getAnswer());
        Assert.assertFalse(question.isAnswered());

        question.answerQuestion(valid);
        question.answerQuestion(null);
        Assert.assertEquals(null, question.getAnswer());
    }

    @Test
    public void testSubQuestions() {
        Question question = new Question(name, prompt, QuestionType.TRUE_FALSE);
        Assert.assertFalse(question.isAnswered());
        Assert.assertTrue(question.isEnabled());
        Assert.assertTrue(question.getSubquestions().isEmpty());
        question.answerQuestion(false);
        Assert.assertTrue(question.isAnswered());

        Question question2 = new Question(name, prompt, QuestionType.TRUE_FALSE);
        Question question3 = new Question(name, prompt, QuestionType.TRUE_FALSE);
        question.addSubquestion(question2);
        question.addSubquestion(question3);
        Assert.assertTrue(question.isAnswered());
        Assert.assertFalse(question2.isAnswered());
        Assert.assertFalse(question3.isAnswered());

        question.getSubquestions().get(0).answerQuestion(false);
        Assert.assertTrue(question.isAnswered());
        Assert.assertFalse(question2.isAnswered());
        Assert.assertFalse(question3.isAnswered());

        question.getSubquestions().get(0).setEnabled(true);
        question.getSubquestions().get(1).setEnabled(true);
        Assert.assertFalse(question.isAnswered());
        Assert.assertFalse(question.getSubquestions().get(0).isAnswered());
        Assert.assertFalse(question.getSubquestions().get(1).isAnswered());

        question.getSubquestions().get(0).answerQuestion(false);
        Assert.assertFalse(question.isAnswered());
        Assert.assertTrue(question.getSubquestions().get(0).isAnswered());
        Assert.assertFalse(question.getSubquestions().get(1).isAnswered());

        question.getSubquestions().get(1).answerQuestion(true);
        Assert.assertTrue(question.isAnswered());
        Assert.assertTrue(question.getSubquestions().get(0).isAnswered());
        Assert.assertTrue(question.getSubquestions().get(1).isAnswered());

        question.clearAnswer();
        Assert.assertFalse(question.isAnswered());
        Assert.assertTrue(question.getSubquestions().get(0).isAnswered());
        Assert.assertTrue(question.getSubquestions().get(1).isAnswered());
    }

    @Test
    public void testMultiOptionQuestion() {
        MultiOptionQuestion question = new MultiOptionQuestion(name, prompt,
                Arrays.asList(new Object[]{"1", 2, "3"}));
        Assert.assertFalse(question.isAnswered());
        Assert.assertTrue(question.isEnabled());
        Assert.assertEquals(QuestionType.MULTI_OPTION_SELECT, question.getType());
        Assert.assertEquals(Object.class, question.getAnswerType());
        List<Object> options = question.getOptions();
        Assert.assertEquals("1", options.get(0));
        Assert.assertEquals(2, options.get(1));
        Assert.assertEquals("3", options.get(2));

        question.answerQuestion(1);
        Assert.assertFalse(question.isAnswered());

        question.answerQuestion(2);
        Assert.assertTrue(question.isAnswered());
        Assert.assertEquals(2, question.getAnswer());

        question.answerQuestionByIndex(2);
        Assert.assertTrue(question.isAnswered());
        Assert.assertEquals("3", question.getAnswer());

        question.clearAnswer();
        Assert.assertFalse(question.isAnswered());
        Assert.assertEquals(null, question.getAnswer());

        Assert.assertFalse(question.isAnswered());
        Assert.assertEquals(null, question.getAnswer());
    }

    private class Garbage {
    }
}