package com.will_code_for_food.crucentralcoast.model.common.messaging;

import com.will_code_for_food.crucentralcoast.model.common.objects.User;

/**
 * Created by Gavin on 11/12/2015.
 */
public abstract class Notifier {
    public boolean notifyUser(User user, NotificationMessage message) {
        // TODO handles the push notification API in the superclass
        return false;
    }
    public boolean textUser(User user, NotificationMessage message) {
        // TODO handles the texting API in the superclass
        return false;
    }
}
