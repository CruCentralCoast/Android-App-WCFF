package com.will_code_for_food.crucentralcoast.modelTests.formTests;


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
        newTest();
        final String prompt = "prompt";

        for (QuestionType type : QuestionType.values()) {
            logStep("Testing question type: " + type);
            logInfo("Testing constructor");
            Question question = new Question(prompt, type);
            Assert.assertEquals(prompt, question.getPrompt());
            Assert.assertEquals(null, question.getAnswer());
            Assert.assertEquals(type.getAnswerType(), question.getAnswerType());
            Assert.assertTrue(question.getSubquestions().isEmpty());

            logInfo("Testing invalid answer");
            Assert.assertFalse(question.answerQuestion(new Garbage()));
            Assert.assertEquals(null, question.getAnswer());

            logInfo("Testing valid answer");
            Object validAnswer = null;
            try {
                Constructor ctor = Class.forName(type.getAnswerType().getName()).getConstructor();
                validAnswer = ctor.newInstance();
            } catch (Exception ex) {
                logError("Cannot instantiate an instance of " + type.getAnswerType());
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


