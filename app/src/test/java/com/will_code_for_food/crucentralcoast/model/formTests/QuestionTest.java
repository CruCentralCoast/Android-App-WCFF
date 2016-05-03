package com.will_code_for_food.crucentralcoast.model.formTests;


import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * Tests the question class
 */
public class QuestionTest extends WCFFUnitTest {

    @Test
    public void testAllQuestionTypes() {
        final String prompt = "prompt";
        final String name = "name";

        for (QuestionType type : QuestionType.values()) {
            Question question = new Question(name, prompt, type);
            Assert.assertEquals(prompt, question.getPrompt());
            Assert.assertEquals(null, question.getAnswer());
            Assert.assertEquals(type.getAnswerType(), question.getAnswerType());
            Assert.assertTrue(question.getSubquestions().isEmpty());

            Assert.assertFalse(question.answerQuestion(new Garbage()));
            Assert.assertEquals(null, question.getAnswer());

            Object validAnswer = null;
            try {
                Constructor ctor = Class.forName(type.getAnswerType().getName()).getConstructor();
                validAnswer = ctor.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            Assert.assertFalse(validAnswer == null);
            question.answerQuestion(validAnswer);
            Assert.assertEquals(validAnswer, question.getAnswer());
            Assert.assertEquals(question.getAnswer().getClass(), type.getAnswerType());
        }
    }

    private class Garbage {
    }
}


