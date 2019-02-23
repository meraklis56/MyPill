package com.example.mypill.Activities.utils;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;

/*
    This is the class responsible to handle user's clicks on the two main buttons
    of notification
*/

public class ActionsIntentService extends IntentService {

    public ActionsIntentService() {
        super("ActionsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Hide notification
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

        if (intent.hasExtra("action")) {
            if (intent.getStringExtra("action").equals("tookPill")) {
                IntakePill();
            } else if (intent.getStringExtra("action").equals("snooze")) {
                SnoozeAction();
            }
        }
    }

    private void SnoozeAction() {
        System.out.println("Snooze action");
    }

    private void IntakePill() {
        System.out.println("Pill intaken");
    }
}