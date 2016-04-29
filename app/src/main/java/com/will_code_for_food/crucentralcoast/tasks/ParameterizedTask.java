package com.will_code_for_food.crucentralcoast.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A version of AsyncTask that requires a constructor that takes in
 * an arbitrary number of variables.
 */
public abstract class ParameterizedTask extends AsyncTask<Void, Void, Object> {
    private final List<Object> extras;

    public ParameterizedTask() {
        extras = new ArrayList<>();
    }

    public final void putExtra(final Object value) {
        extras.add(value);
    }

    public final Object getExtra(final int index) {
        return extras.get(index);
    }

    @Override
    protected abstract Object doInBackground(Void... params);
}
