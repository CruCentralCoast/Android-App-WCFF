package com.will_code_for_food.crucentralcoast.model.common.messaging;

import android.app.Activity;
import android.content.Context;

/**
 * An interface that can be used to send some form
 * of notification to a user.
 */
public abstract class Notifier {
    private String name;
    private String contactInfo;
    private final Context context;

    public Notifier(final Context context, final String contactInfo) {
        this(context, null, contactInfo);
    }

    public Notifier(final Context context, final String name, final String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.context = context;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getName() {
        return name;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Sends the message to the user specified by name and contact info.
     * Returns success or failure, which can be used to check validity
     * of provided user information.
     */
    public abstract boolean notify(final String message);
}
