package com.will_code_for_food.crucentralcoast.model.formTests;

import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests the driver form
 */
public class DriverFormTest extends WCFFUnitTest {
    // TODO these tests don't work because they need a context to do the file IO
/*
    @Test
    public void testUsernameNotAutofilled() {
        boolean needToReplace = false;
        String name = null;
        if (LocalStorageIO.fileExists(LocalFiles.USER_NAME)) {
            // delete file (replace later)
            needToReplace = true;
            name = LocalStorageIO.readSingleLine(LocalFiles.USER_NAME);
            LocalStorageIO.deleteFile(LocalFiles.USER_NAME);
        }

        // perform test
        DriverForm form = new DriverForm("id");
        Question nameQuestion = form.getQuestion(0);
        Assert.assertFalse(nameQuestion.isAnswered());

        // replace file (if deleted)
        if (needToReplace) {
            LocalStorageIO.writeSingleLineFile(LocalFiles.USER_NAME, name);
        }
    }

    @Test
    public void testUsernameAutofilled() {
        boolean needToDelete = false;
        String name = "cru_user";
        if (!LocalStorageIO.fileExists(LocalFiles.USER_NAME)) {
            // delete file (replace later)
            needToDelete = true;
            LocalStorageIO.writeSingleLineFile(LocalFiles.USER_NAME, name);
        } else {
            name = LocalStorageIO.readSingleLine(LocalFiles.USER_NAME);
        }

        // perform test
        DriverForm form = new DriverForm("id");
        Question nameQuestion = form.getQuestion(0);
        Assert.assertTrue(nameQuestion.isAnswered());
        Assert.assertEquals(name, nameQuestion.getAnswer());

        // delete file (if created)
        if (needToDelete) {
            LocalStorageIO.deleteFile(LocalFiles.USER_NAME);
        }
    }
*/
    // TODO need to test that submitting works, but can't test database stuff reliably
}
