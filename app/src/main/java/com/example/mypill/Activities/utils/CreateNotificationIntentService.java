package com.example.mypill.Activities.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.example.mypill.R;

import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import java.util.Random;

/*
    This class is responsible to create the two types of notification:
    1. Main Notification (Reminder to take your pill with 2 actions)
    2. Secondary Notification (Reminder that you can eat anything)
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

        System.out.println(intent.hasExtra("NOTIFICATION"));
        if (intent.hasExtra("NOTIFICATION")) {
            if (intent.getStringExtra("NOTIFICATION").equals("MAIN")) {
                createMainNotification();
            } else if (intent.getStringExtra("NOTIFICATION").equals("SECONDARY")) {
                createSecondaryNotification();
            }
        } else {
            createMainNotification();
        }
        // There is a bug here. Whenever a new alarm is set, for some reason
        // the intent.getExtra("NOTIFICATION") is not set

        System.out.println("IntentReceiver");
    }

    public void createMainNotification() {
        // Create the two intents for our actions
        Intent tookPillIntent = new Intent(context, ActionsIntentService.class);
        tookPillIntent.putExtra("ACTION", "TAKEPILL");
        PendingIntent tookPillPendingIntent = PendingIntent.getService(context, 3, tookPillIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(context, ActionsIntentService.class);
        snoozeIntent.putExtra("ACTION", "SNOOZE");
        PendingIntent snoozePendingIntent = PendingIntent.getService(context, 4, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent forgetIntent = new Intent(context, ActionsIntentService.class);
        forgetIntent.putExtra("ACTION", "FORGET");
        PendingIntent forgetPendingIntent = PendingIntent.getService(context, 5, forgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build Notification with NotificationCompat.Builder
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.notificationTitle))   //Set the title of Notification
                .setContentText(getString(R.string.notificationText))    //Set the text for notification
                .setSmallIcon(R.drawable.notification_logo)   //Set the icon
                .addAction(R.drawable.check, getString(R.string.tookPill), tookPillPendingIntent)
                .addAction(R.drawable.check, getString(R.string.snooze), snoozePendingIntent)
                .addAction(R.drawable.check, getString(R.string.forgotPill), forgetPendingIntent)
                .setAutoCancel(true)
                .setDeleteIntent(snoozePendingIntent)
                .build();
        // TODO create different text and titles and randomize them

        // To place the notification on top and force to be in extended view
        notification.priority = Notification.PRIORITY_MAX;

        // Show the notification.
        mNotificationManager.notify(1, notification);
    }

    public void createSecondaryNotification() {

        String[] titlesArray = context.getResources().getStringArray(R.array.ingestYourPillTitle);
        String[] textArray = context.getResources().getStringArray(R.array.ingestYourPillText);

        // Build Notification with NotificationCompat.Builder
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(titlesArray[new Random().nextInt(titlesArray.length)])   //Set the title of Notification
                .setContentText(textArray[new Random().nextInt(textArray.length)])    //Set the text for notification
                .setSmallIcon(R.drawable.notification_logo) //Set the icon
                .setColorized(true)
                .setAutoCancel(true)
                .build();

        // Show the notification.
        mNotificationManager.notify(1, notification);
    }

    // This is needed only if API Level >= 26 (OREO)
    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel only if it is not already created
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        CHANNEL_ID, "Alarm Sound", NotificationManager.IMPORTANCE_DEFAULT
                );
                notificationChannel.setLightColor(Color.RED);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}