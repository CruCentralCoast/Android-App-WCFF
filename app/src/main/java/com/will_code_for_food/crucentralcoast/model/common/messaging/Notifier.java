package com.will_code_for_food.crucentralcoast.model.common.messaging;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.will_code_for_food.crucentralcoast.model.common.common.users.User;

/**
 * Created by Gavin on 11/12/2015.
 */
public class Notifier {
    // Current notification
    int curId;

    public Notifier() {
        curId = 0;
    }

    public boolean notifyUser(User user, NotificationMessage message) {
        // TODO handles the push notification API in the superclass
        return false;
    }
    public boolean textUser(User user, NotificationMessage message) {
        // TODO handles the texting API in the superclass
        return false;
    }

    public void createNotification(String title, String text, Context context) {
        // Creates a new notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.stat_notify_more)  // change this to the cru icon
                        .setContentTitle(title)
                        .setContentText(text);

        NotificationManager mNotificationManager =  (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(curId++, mBuilder.build());
    }

    public void sendNotification() {

    }
}
