package com.example.mypill.Activities.utils;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.mypill.R;

/*
    This is the class responsible to handle user's clicks on the two main buttons
    of notification
*/

public class ActionsIntentService extends IntentService {

    private AlarmsManager alarmsManager;
    private Handler mHandler;

    public ActionsIntentService() {
        super("ActionsIntentService");
        alarmsManager = new AlarmsManager();
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Hide notification
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

        if (intent.hasExtra("ACTION")) {
            if (intent.getStringExtra("ACTION").equals("TAKEPILL")) {
                IntakePill();
            } else if (intent.getStringExtra("ACTION").equals("SNOOZE")) {
                SnoozeAction();
            }
        }
    }

    private void SnoozeAction() {
        System.out.println("Snooze action");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), (String)getString(R.string.snoozeToast), Toast.LENGTH_LONG).show();
                alarmsManager.setSecondaryAlarm("SNOOZE_MAIN_NOTIFICATION");
            }
        });
    }

    private void IntakePill() {
        System.out.println("Pill intaken");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), (String)getString(R.string.pillTaken), Toast.LENGTH_LONG).show();
                // TODO notify DB
                alarmsManager.setSecondaryAlarm("ACTION_PILL_INTAKEN");
            }
        });
    }
}