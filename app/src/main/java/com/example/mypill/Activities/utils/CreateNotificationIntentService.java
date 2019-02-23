package com.example.mypill.Activities.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;

import com.example.mypill.R;

/*
    This class is responsible to create the notification
*/

public class CreateNotificationIntentService extends JobIntentService {
    private static final String CHANNEL_ID = "myNotificationChannel";
    private NotificationManager mNotificationManager;
    private Context context = GlobalApplication.getAppContext();

    public static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, CreateNotificationIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        // Get reference to Notification Manager
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel(mNotificationManager);

        // Create the two intents for our actions
        Intent tookPillIntent = new Intent(context, ActionsIntentService.class);
        tookPillIntent.putExtra("action", "tookPill");
        PendingIntent pendingIntent = PendingIntent.getService(context, 3, tookPillIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(context, ActionsIntentService.class);
        snoozeIntent.putExtra("action", "snooze");
        PendingIntent snoozePendingIntent = PendingIntent.getService(context, 3, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build Notification with NotificationCompat.Builder
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.notificationTitle))   //Set the title of Notification
                .setContentText(getString(R.string.notificationText))    //Set the text for notification
                .setSmallIcon(R.drawable.mainlogo)   //Set the icon
                .setColorized(true)
                .addAction(android.R.drawable.ic_menu_view, getString(R.string.tookPill), pendingIntent)
                .addAction(android.R.drawable.ic_menu_view, getString(R.string.snooze), pendingIntent)
                .setAutoCancel(true)
                .build();
        // TODO create different text and titles and randomize them

        //Send the notification.
        mNotificationManager.notify(1, notification);

        System.out.println("IntentReceiver");
    }

    // This is needed only if API Level >= 26 (OREO)
    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel only if it is not already created
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, "Alarm Sound", NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
        }
    }
}