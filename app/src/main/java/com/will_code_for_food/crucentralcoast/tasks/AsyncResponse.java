package com.will_code_for_food.crucentralcoast.tasks;

import android.content.Context;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

/**
 * An optional parameter for an async task. If one is provided, the processFinish
 * message will be called on the task's postExecute
 */
public abstract class AsyncResponse {
    private final Context context;

    public AsyncResponse(final Context context) {
        this.context = context;
    }

    public final void processFinish(ContentType type) {
        if (type == ContentType.CACHED) {
            Toast.makeText(context, Util.getString(R.string.cached_data_msg),
                    Toast.LENGTH_SHORT).show();
        }
        otherProcessing();
    }

    protected abstract void otherProcessing();
}
