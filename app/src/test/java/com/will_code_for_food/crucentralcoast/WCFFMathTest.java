package com.will_code_for_food.crucentralcoast;

import junit.framework.TestCase;

/**
 * Created by MasonStevenson on 11/5/2015.
 */
public class WCFFMathTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    // every method that starts with "test" will get called
    public void testNumberAdder() {
        int result = WCFFMath.addNumbers(2, 3);
        assertEquals(5, result);

        //uncomment if you want to see a test fail
        //assertEquals(0, 1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
