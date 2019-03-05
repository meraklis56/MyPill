package com.example.mypill.Activities.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.util.Log;

/*
    This class is responsible to handle the setting of Alarms.
    There are 2 types of alarms:

    1. Main Alarm(s) which sets of every day at specific time
    2. Secondary Alarm(s) which are repeated every a small interval (30 minutes for snoozing)

    More specifically:

    1. One Main Alarm in the morning for the pill, whenever you wake up
    2. If action == pillIntaken:
        Secondary Alarm in 30 minutes to eat food
       ElseIf action == snooze:
        Secondary Alarm in 30 minutes to take pill
*/

public class AlarmsManager {

    private Context context;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;

    public AlarmsManager() {
        context = GlobalApplication.getAppContext();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context.getApplicationContext(), MyBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public boolean setMainAlarm() {
        try {
            intent = new Intent(context.getApplicationContext(), MyBroadcastReceiver.class);
            intent.putExtra("NOTIFICATION", "MAIN");
            pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Set the alarm to start at approximately 5:00 a.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 5);

            // For testing reasons this is repeated approximately every 10 seconds
            // Due to Android's doze settings, it could be more
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(),24 * 60 * 60 * 1000, pendingIntent);
            Log.i("AlarmsManager", "Alarm was set at 5 am");
            return true;
        } catch (Exception e) {
            Log.e("AlarmsManager", e.getMessage());
            return false;
        }
    }

    // This method has two options:
    // 1. Display a notification after 30 minutes that you can eat
    // 2. Display normal notification
    public boolean setSecondaryAlarm(String action) {
        try {
            if (action.equals("ACTION_PILL_INTAKEN")) {

                Intent intent2 = new Intent(context.getApplicationContext(), MyBroadcastReceiver.class);
                intent2.putExtra("NOTIFICATION", "SECONDARY");
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context.getApplicationContext(), 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() +
                                60 * 1000 * 30, pendingIntent2);
                Log.i("AlarmsManager", "Secondary Alarm, to intake pill");
            } else if (action.equals("SNOOZE_MAIN_NOTIFICATION")) {

                Intent intent2 = new Intent(context.getApplicationContext(), MyBroadcastReceiver.class);
                intent2.putExtra("NOTIFICATION", "MAIN");
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context.getApplicationContext(), 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() +
                        60 * 1000 * 30, pendingIntent2);
                Log.i("AlarmsManager", "Secondary Alarm, to notify to eat");
            }

            return true;
        } catch (Exception e) {
            Log.e("AlarmsManager", e.getMessage());
            return false;
        }
    }

    public boolean cancelAlarm() {
        try {
            alarmManager.cancel(pendingIntent);
            System.out.println("ALARM CANCELLED");
            return true;
        } catch (Exception e) {
            Log.e("AlarmsManager", "AlarmManager update was not canceled. " + e.toString());
            return false;
        }
    }

    public boolean isAlarmSet() {
        intent = new Intent(context.getApplicationContext(), MyBroadcastReceiver.class);
        intent.putExtra("NOTIFICATION", "MAIN");
        pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return (PendingIntent.getBroadcast(context.getApplicationContext(), 1,
                intent, PendingIntent.FLAG_NO_CREATE) != null);
    }
}
