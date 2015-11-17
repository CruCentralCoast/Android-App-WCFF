package com.will_code_for_food.crucentralcoast;

import android.app.*;
import android.content.*;
import android.support.v4.app.NotificationCompat;

public class Notifier {
    // Current notification
    int curId;

    public Notifier() {
        curId = 0;
    }

    public void createNotification(String title, String text, Context context) {
        // Creates a new notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.stat_notify_more)  // change this to the cru icon
                        .setContentTitle(title)
                        .setContentText(text);

        /*
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, activityClass);
        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(activityClass);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        */

        NotificationManager mNotificationManager =  (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(curId++, mBuilder.build());
    }

    public void sendNotification() {

    }
}
