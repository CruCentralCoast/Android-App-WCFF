package com.will_code_for_food.crucentralcoast;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.Logger;

import org.junit.Before;

/**
 * A way to standardize test outputs
 */
public abstract class WCFFUnitTest {
    @Before
    public void setup() {
        Log.e("TEST MODE", "Entering Test Mode");
        Logger.testMode = true;
    }
}
