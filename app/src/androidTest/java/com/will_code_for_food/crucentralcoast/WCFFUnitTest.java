package com.will_code_for_food.crucentralcoast;

/**
 * A way to standardize test outputs
 */
public abstract class WCFFUnitTest {
    private int step = 0;

    public void newTest() {
        step = 0;
    }

    public void logStep(final Object message) {
        System.out.println(++step + ": " + message);
    }

    public void logInfo(final Object message) {
        System.out.println("\t" + message);
    }

    public void logError(final Object message) {
        System.out.println("\tERROR: " + message);
    }

    public void logWarning(final Object message) {
        System.out.println("\tWARN: " + message);
    }
}
